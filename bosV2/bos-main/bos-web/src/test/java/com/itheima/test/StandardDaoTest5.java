package com.itheima.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StandardDao;
import com.itheima.bos.domain.base.Standard;

/**
 * 演示JpaRepository接口用法
 * @author lenovo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardDaoTest5 {
	
	//注入StandardDao
	@Resource
	private StandardDao standardDao;
	
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test1(){
		List<Standard> list = standardDao.findAll();
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
}
