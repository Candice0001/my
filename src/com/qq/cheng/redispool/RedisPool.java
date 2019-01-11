package com.qq.cheng.redispool;

import redis.clients.jedis.Jedis;

public interface RedisPool {
	
	//1.初始化线程池的大小，以及规定连接的超时时间
	
	/**
	 * 
	 * Title: init
	 * Description:  
	 * @time 2018年6月7日 下午6:59:05
	 * @param max 最大数量
	 * @param timeout 超时时间
	 */
	public void init(int max,long timeout);
	
	//2.获取连接
	
	public Jedis browResource() throws Exception;
	
	//3.释放连接
	public void release(Jedis jedis);

}
