package com.itheima.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StandardDao;
import com.itheima.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardDaoTest2 {
	
	//注入StandardDao
	@Resource
	private StandardDao standardDao;
	
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test1(){
		Standard s = new Standard();
		s.setName("10-20公斤");
		standardDao.save(s);
		
		System.out.println(standardDao);

	}
	
	@Test
	public void test2(){
		System.out.println(standardDao.getClass());

	}
}
