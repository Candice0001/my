package com.qq.cheng.redispool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.Jedis;

public class RedisPoolImpl implements RedisPool {
	//最大连接数量
	private int max;
	//超时时间
	private long timeOut;
	//空闲连接
	private LinkedBlockingQueue<Jedis> id;
	//繁忙连接
	private LinkedBlockingQueue<Jedis> busy;
	//用于计数，保证线程安全
	AtomicInteger atomicInteger=new AtomicInteger(0);
	//用于打印创建连接的次数
	AtomicInteger count=new AtomicInteger(0);
	AtomicInteger count1=new AtomicInteger(0);

	@Override
	public void init(int max, long timeout) {
		this.max=max;
		this.timeOut=timeout;
		id=new LinkedBlockingQueue<Jedis>();
		busy=new LinkedBlockingQueue<Jedis>();

	}

	@Override
	public Jedis browResource() throws Exception {
		long nowTime=System.currentTimeMillis();//记录时间，便于判断超时
		Jedis jedis=null;
		
		while(null==jedis) {
			//在空闲队列中获取连接，如果非空，放到繁忙队列，并返回该链接
			jedis=id.poll();
			if (null!=jedis) {
				busy.offer(jedis);
				System.out.println("获取空闲队列的元素1。。。。");
				return jedis;
			}
			//为空.判断是否已达到最大连接数
			if (atomicInteger.get()<max) {
				//未满，创建
				if (atomicInteger.incrementAndGet()<=max) {
					jedis=new Jedis("127.0.0.1",6379);
					//jedis.auth("admin");
					System.out.println("jedis的连接数量："+count.incrementAndGet()); //打印创建的连接数量
					System.out.println("atomicInteger:"+atomicInteger.get()+" count1:"+count1.incrementAndGet());
					busy.offer(jedis);
				}else {
					//已达到最大连接数，atomicInteger需减一,因为前面加了一
					atomicInteger.decrementAndGet();
				}
			}
			//已达到最大连接数，等待空闲线程有值
			try {
				//在超时时间内返回连接
				//获取并移除此队列的头部，在指定的等待时间前等待可用的元素（如果有必要）
				jedis=id.poll(timeOut-(System.currentTimeMillis()-nowTime),TimeUnit.MILLISECONDS);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			if (null!=jedis) {//未超时
				busy.offer(jedis);
				System.out.println("获取空闲队列的连接2...");
				return jedis;
			}else {//已经超时,抛出异常
				if (System.currentTimeMillis()-nowTime > timeOut) {
					throw new Exception("超时了。。。。");
				}else {
					continue;
				}
			}
		}
		return null;
	}

	@Override
	public void release(Jedis jedis) {
		System.out.println("release0...");
		//释放连接
		//从繁忙中移除，并加入到空闲队列中，如果移除失败，说明连接不可用，计数器要减一
		if (busy.remove(jedis)) {
			System.out.println("连接释放了");
			id.offer(jedis);
		}else {
			System.out.println("release else...");
			atomicInteger.decrementAndGet();
			count1.decrementAndGet();
		}
	}

}
