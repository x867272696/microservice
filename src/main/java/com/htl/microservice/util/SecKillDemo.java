package com.htl.microservice.util;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class SecKillDemo implements Runnable {

    private Jedis jedis = new Jedis("127.0.0.1", 6379);

    private String customerName;

    private String key;

    public SecKillDemo(String customerName, String key) {
        this.customerName = customerName;
        this.key = key;
    }

    @Override
    public void run() {
        boolean success = false;
        String data;
        int currentNum;
        while (!success) {//可重复抢购直到成功
           //通过watch实现redis的incr(原子递增操作)
            jedis.watch(key);
            data = jedis.get(key);
            currentNum = Integer.parseInt(data);
            if (currentNum > 0) {
                //开启事务
                Transaction transaction = jedis.multi();
                //设置新值,如果key的值被其它连接的客户端修改，那么当前连接的exec命令将执行失败
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
                }
            } else {
                System.out.println("商品售空,活动结束!");
                System.exit(0);
            }
        }
    }
}
