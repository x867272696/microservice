package com.htl.microservice.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisLock {

	@Autowired
	private JedisPool jedisPool;

	public boolean tryLock(String key, String val, Long ttl) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		try {
			Boolean ok = "OK".equals(jedis.set(key, val, "NX", "EX", ttl));
			return ok;
		} finally {
			jedis.close();
		}
	}

	public void lock(String key, String val, Long ttl) {
		while (!tryLock(key, val, ttl)) {
			System.out.println("redis自旋锁继续循环以拿到同步锁");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean releaseLock(String key, String val) {
		Jedis jedis = jedisPool.getResource();
		try {
			String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then\n" + "return redis.call('del', KEYS[1])\n"
					+ "else\n" + "return 0\n" + "end";
			Object obj = jedis.eval(lua, Lists.newArrayList(key), Lists.newArrayList(val));
			return !((Long) obj).equals(0L);
		} finally {
			jedis.close();
		}
	}
	
	public static void main(String[] args) {
		new RedisLock().lock("key", "ssdf", 10L);
	}
}
