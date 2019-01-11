package com.qq.cheng.redispool;

import java.util.concurrent.CountDownLatch;

import redis.clients.jedis.Jedis;

public class TestRedisPool {
	private static final CountDownLatch countDownLatch= new CountDownLatch(50);
	
	public static void main(String[] args) {
		final RedisPool redisPool = new RedisPoolImpl();
		//初始化连接
		redisPool.init(10, 10000);
		for (int i = 0; i < 50; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					countDownLatch.countDown();
					try {
						countDownLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (int j = 0; j < 10; j++) {
						Jedis jedis=null;
						try {
							jedis=redisPool.browResource();
							jedis.incr("name");
						} catch (Exception e) {
							e.printStackTrace();
						}finally {
							System.out.println("release....");
							redisPool.release(jedis);
						}
						
					}
					
				}
			}).start();
		}
	}

}
