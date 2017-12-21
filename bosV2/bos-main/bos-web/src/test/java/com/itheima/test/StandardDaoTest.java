package com.itheima.test;

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
 * 演示Repository接口的用法
 * @author lenovo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardDaoTest {
	
	//注入StandardDao
	@Resource
	private StandardDao standardDao;
	
	@Test
	public void test1(){
		List<Standard> list = standardDao.findByNameIs("5-10公斤");
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	@Test
	public void test2(){
		List<Standard> list = standardDao.findByNameLike("%10%");
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	@Test
	public void test3(){
		List<Standard> list = standardDao.findByNameLikeAndMinWeight("%10%", 5L);
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	@Test
	public void test4(){
		List<Standard> list = standardDao.findByMinWeightBetween(5L, 10L);
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	
	@Test
	public void test5(){
		List<Standard> list = standardDao.queryName("5-10公斤");
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	@Test
	public void test6(){
		List<Standard> list = standardDao.queryName2("%10%",5L);
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	@Test
	public void test7(){
		List<Standard> list = standardDao.queryName3("%10%",5L);
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void test8(){
		standardDao.updateMaxWeight(30L, 3L);
	}
	
}
