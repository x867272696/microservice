package com.htl.microservice.util;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;

public class SecKillTest {

    private static String key = "macbook";

    private static String num = "1000000";

    private static ExecutorService executorService = Executors.newFixedThreadPool(8);
    
    
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");
        String script = "redis.call('del',KEYS[1]);return redis.call('set',KEYS[1],ARGV[1])";
        jedis.eval(script, Collections.singletonList(key), Collections.singletonList(num));
        jedis.close();
    	
        try{
            for (int i = 1; i <= 1000000; i++) {
                executorService.submit(new SecKillDemo("顾客"+i,key));
            }
            Thread.sleep(10000);
        } catch (Exception e) {
			e.printStackTrace();
		}finally {
            executorService.shutdown();
        }
    }
}
