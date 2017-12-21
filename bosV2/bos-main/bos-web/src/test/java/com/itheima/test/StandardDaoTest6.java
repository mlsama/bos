package com.itheima.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StandardDao;
import com.itheima.bos.domain.base.Standard;

/**
 * 演示JpaSpecificationExecutorRepository接口用法
 * 
 * @author lenovo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardDaoTest6 {

	// 注入StandardDao
	@Resource
	private StandardDao standardDao;

	// 条件查询（一个条件）
	@Test
	@Transactional
	@Rollback(false) // 取消事务的自动回滚
	public void test1() {
		// Specification: spring data jpa提供用于封装条件的对象
		// 注意：需要自行提供Specification接口的实现类对象（匿名内部类）
		Specification<Standard> spec = new Specification<Standard>() {

			// 一个条件: from Standard where name = ?
			// Predicate: 封装一个的查询条件，如果有多个条件，需要List<Predicate> （ where name = ?）
			// **Root<Standard>: 查询的根对象（当前实体对象），作用：使用根对象查询属性
			// CriteriaQuery<?>: 执行简单条件查询（不用）
			// **CriteriaBuilder: 查询构建对象，作用：用于构建不同类型的条件查询 例如 = like > < >= 等 （（
			// name = ?））
			@Override
			public Predicate toPredicate(Root<Standard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				/**
				 * 参数一：查询的属性名 参数二：查询的属性值
				 */
				Predicate pre = cb.equal(root.get("name").as(String.class), "5-10公斤");

				return pre;
			}

		};

		List<Standard> list = standardDao.findAll(spec);
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}

	// 条件查询（多个组合条件）
	@Test
	@Transactional
	@Rollback(false) // 取消事务的自动回滚
	public void test2() {

		// 模拟页面的条件
		final String name = "";
		final Long minWeight = 5L;
		final Long maxWeight = 10L;

		// Specification: spring data jpa提供用于封装条件的对象
		// 注意：需要自行提供Specification接口的实现类对象（匿名内部类）
		Specification<Standard> spec = new Specification<Standard>() {

			// Predicate:如果封装多个条件使用List<Predicate>
			@Override
			public Predicate toPredicate(Root<Standard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> preList = new ArrayList<Predicate>();

				// 添加查询条件
				// 名称
				if (name != null && !name.equals("")) {
					preList.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
				}

				// 最小重量
				if (minWeight != null) {
					preList.add(cb.equal(root.get("minWeight").as(Long.class), minWeight));
				}

				// 最大重量
				if (maxWeight != null) {
					preList.add(cb.equal(root.get("maxWeight").as(Long.class), maxWeight));
				}

				// 声明Predicate数组
				Predicate[] preArray = new Predicate[preList.size()];
				// 结果：name like ? and minWeight = ? and maxWeight = ?
				// toArray(): 把集合转换为数组，但是转换后的数组里面的元素类型都是Object
				// toArray(T[]): 把集合转换为数组，但是转换后数组里面的元素为指定类型
				return cb.and(preList.toArray(preArray));
			}
		};

		List<Standard> list = standardDao.findAll(spec);
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}

	// 条件查询（多个组合条件）+ 分页
	@Test
	@Transactional
	@Rollback(false) // 取消事务的自动回滚
	public void test3() {

		// 模拟页面的条件
		final String name = "";
		final Long minWeight = 5L;
		final Long maxWeight = 10L;

		// Specification: spring data jpa提供用于封装条件的对象
		// 注意：需要自行提供Specification接口的实现类对象（匿名内部类）
		Specification<Standard> spec = new Specification<Standard>() {

			// Predicate:如果封装多个条件使用List<Predicate>
			@Override
			public Predicate toPredicate(Root<Standard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> preList = new ArrayList<Predicate>();

				// 添加查询条件
				// 名称
				if (name != null && !name.equals("")) {
					preList.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
				}

				// 最小重量
				if (minWeight != null) {
					preList.add(cb.equal(root.get("minWeight").as(Long.class), minWeight));
				}

				// 最大重量
				if (maxWeight != null) {
					preList.add(cb.equal(root.get("maxWeight").as(Long.class), maxWeight));
				}

				// 声明Predicate数组
				Predicate[] preArray = new Predicate[preList.size()];
				// 结果：name like ? and minWeight = ? and maxWeight = ?
				// toArray(): 把集合转换为数组，但是转换后的数组里面的元素类型都是Object
				// toArray(T[]): 把集合转换为数组，但是转换后数组里面的元素为指定类型
				return cb.and(preList.toArray(preArray));
			}
		};
		
		//分页对象
		Pageable pageable = new PageRequest(0,2);

		Page<Standard> pageBean = standardDao.findAll(spec, pageable);
		
		List<Standard> list = pageBean.getContent();
		
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}

}
