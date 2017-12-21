package com.itheima.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * 演示jedis操作redis数据库
 * 
 * @author lenovo
 *
 */
public class JedisDemo {

	// 保存
	@Test
	public void test1() {
		// 创建jedis，用于操作redis
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.set("email", "eric@qq.com");
	}

	// 取出
	@Test
	public void test2() {
		// 创建jedis，用于操作redis
		Jedis jedis = new Jedis("localhost", 6379);
		String email = jedis.get("email");
		System.out.println(email);
	}
}
