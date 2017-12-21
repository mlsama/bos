package com.itheima.test;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 演示SpringData整合Redis的代码
 * 
 * @author lenovo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDataRdisDemo {

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	// 保存
	@Test
	public void test1() {
		redisTemplate.opsForValue().set("password", "123456");
	}

	// 取出
	@Test
	public void test2() {
		String result = redisTemplate.opsForValue().get("password");
		System.out.println(result);
	}

	// 保存到redis，设置有效时间
	/**
	 * 参数三：有效的时间值 参数四：计量单位，纳秒，微秒，毫秒，秒，小时，天
	 */
	@Test
	public void test3() {
		redisTemplate.opsForValue().set("name", "itheima", 20, TimeUnit.SECONDS);
	}

	// 删除
	@Test
	public void test4() {
		redisTemplate.delete("email");
	}

}
