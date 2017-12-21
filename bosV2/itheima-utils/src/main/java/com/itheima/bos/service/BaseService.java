package com.itheima.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * 泛型service接口
 * @author lenovo
 *
 * @param <T>
 */
public interface BaseService<T> {
	/**
	 * 查询所有数据
	 */
	public List<T> findAll();

	/**
	 * 分页查询
	 */
	public Page<T> findAll(Pageable pageable);
	
	/**
	 * 条件查询
	 */
	public List<T> findAll(Specification<T> spec);

	/**
	 * 分页+条件查询
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public Page<T> findAll(Specification<T> spec, Pageable pageable);

	/**
	 * 保存方法
	 * 
	 * @param model
	 */
	public void save(T model);
	
	
	/**
	 * 批量保存方法
	 * 
	 * @param model
	 */
	public void save(List<T> list);

	/**
	 * 查询一个
	 * 
	 * @param uuid
	 * @return
	 */
	public T findOne(Long uuid);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);
}
