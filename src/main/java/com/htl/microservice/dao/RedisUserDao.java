package com.htl.microservice.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.htl.microservice.lock.RedisLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

@Component
public class RedisUserDao {

	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private RedisLock redisLock;

	public String getUser(String id) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis != null) {
				return jedis.get(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	public String changeMoney(String id) {

		Jedis jedis = null;
		Integer money = null;
		try {
			jedis = jedisPool.getResource();

			for (int i = 1; i <= 100000; i++) {
				money = Integer.parseInt(jedis.get(id));
				money -= 1;
				jedis.set(id, money.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(new RedisUserDao().getUser("1"));
	}

	public boolean buy() {
		String customerName = UUID.randomUUID().toString();
		String key = "surface";

		Jedis jedis = jedisPool.getResource();

		boolean success = false;
		String data;
		int currentNum;
		while (!success) {// 可重复抢购直到成功
			// 通过watch实现redis的incr(原子递增操作)
			jedis.watch(key);
			data = jedis.get(key);
			currentNum = Integer.parseInt(data);
			if (currentNum > 0) {
				// 开启事务
				Transaction transaction = jedis.multi();
				// 设置新值,如果key的值被其它连接的客户端修改，那么当前连接的exec命令将执行失败
				currentNum--;
				transaction.set(key, String.valueOf(currentNum));
				List res = transaction.exec();
				if (res.size() == 0) {
					System.out.println(customerName + " 抢购失败");
					success = false;
				} else {
					success = true;
					System.out.println(customerName + " 抢购成功,[" + key + "]剩余：" + currentNum);
					jedis.close();
					return true;
				}
			} else {
				System.out.println("商品售空,活动结束!");
				jedis.close();
				return false;
			}
		}
		return false;
	}

	public Boolean errorBuy() {
		Jedis jedis = jedisPool.getResource();
		String key = "surface";
		String customerName = UUID.randomUUID().toString();
		try {
			Integer count = Integer.valueOf(jedis.get(key));
			if (count >= 0) {
				jedis.set(key, String.valueOf(--count));
				System.out.println(customerName + " 抢购成功,[" + key + "]剩余：" + count);
				return true;
			} else {
				System.out.println("商品售空,活动结束!");
				return false;
			}
		} finally {
			jedis.close();
		}
	}

	public Object lockBuy() {
		Jedis jedis = jedisPool.getResource();
		String key = "surface";
		String customerName = UUID.randomUUID().toString();
		String lockname = "surface-lock";
		redisLock.lock(lockname, customerName, 10L);
		try {
			Integer count = Integer.valueOf(jedis.get(key));
			if (count >= 0) {
				jedis.set(key, String.valueOf(--count));
				System.out.println(customerName + " 抢购成功,[" + key + "]剩余：" + count);
				return true;
			} else {
				System.out.println("商品售空,活动结束!");
				return false;
			}
		} finally {
			jedis.close();
			redisLock.releaseLock(lockname, customerName);
		}

	}
}
