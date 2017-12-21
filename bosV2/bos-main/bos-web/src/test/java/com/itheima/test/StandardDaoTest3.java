package com.itheima.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StandardDao;
import com.itheima.bos.domain.base.Standard;

/**
 * 演示CrudRepository接口用法
 * @author lenovo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardDaoTest3 {
	
	//注入StandardDao
	@Resource
	private StandardDao standardDao;
	
	//一个添加
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test1(){
		Standard s = new Standard();
		s.setName("15-20公斤");
		standardDao.save(s);
	}
	
	//批量保存
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test2(){
		Standard s1 = new Standard();
		s1.setName("20-25公斤");
		
		Standard s2 = new Standard();
		s2.setName("25-30公斤");
		
		List<Standard> list = new ArrayList<Standard>();
		list.add(s1);
		list.add(s2);
		
		standardDao.save(list);
	}
	
	//修改
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test3(){
		Standard s = new Standard();
		s.setId(3L);
		s.setName("15-20公斤");
		s.setMaxWeight(10L);
		standardDao.save(s);
	}
	
	//查询所有
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test4(){
		List<Standard> list = (List<Standard>)standardDao.findAll();
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	//查询一个
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test5(){
		Standard standard = standardDao.findOne(3L);
		System.out.println(standard);
	}
	
	//删除
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test6(){
		standardDao.delete(3L);
	}
	
}
