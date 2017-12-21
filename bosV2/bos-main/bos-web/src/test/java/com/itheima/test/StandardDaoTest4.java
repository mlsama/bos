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
 * 演示PagingAndSortingRepository接口用法
 * @author lenovo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardDaoTest4 {
	
	//注入StandardDao
	@Resource
	private StandardDao standardDao;
	
	//排序查询
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test1(){
		//Sort: spring data jpa提供的排序对象
		
		//一个条件排序
		//需求: 按照id值倒序  order by id desc
		// Direction.DESC : 倒序   54321
		// Direction.ASC :  正序  12345
		//Sort sort = new Sort(Direction.DESC,"id");
		
		//多个条件排序
		//需求：按照id值倒序，按照minWeight正序
		Sort sort = new Sort(new Order(Direction.DESC,"id"),new Order(Direction.ASC,"minWeight"));
		
		List<Standard> list = (List<Standard>)standardDao.findAll(sort);
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	//分页查询
	@Test
	@Transactional
	@Rollback(false)// 取消事务的自动回滚
	public void test2(){
		//Pageable: spring data jpa提供的封装分页参数的对象
		
		//当前页码（从0开始）
		int page = 1;
		//页面大小
		int size = 2;
		Pageable pageable = new PageRequest(page,size);
		
		//spring data jpa已经提供了一个Page对象，这个对象已经封装好了分页数据，例如总页数，总记录数，列表数据等
		Page<Standard> pageBean = standardDao.findAll(pageable);
		
		//1.获取每页列表数据
		List<Standard> list = pageBean.getContent();
		for (Standard standard : list) {
			System.out.println(standard);
		}
		//2.获取总记录数
		System.out.println(pageBean.getTotalElements());
		//3.总页数
		System.out.println(pageBean.getTotalPages());
	}

}
