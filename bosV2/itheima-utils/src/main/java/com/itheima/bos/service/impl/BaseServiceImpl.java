package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.dao.BaseDao;
import com.itheima.bos.service.BaseService;
/**
 * 泛型Service实现类
 * @author lenovo
 *
 * @param <T>
 */
public class BaseServiceImpl<T> implements BaseService<T> {

	// 注入dao对象
	private BaseDao<T> baseDao;
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return baseDao.findAll(pageable);
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return baseDao.findAll(spec, pageable);
	}

	@Override
	public void save(T model) {
		baseDao.save(model);
	}

	@Override
	public T findOne(Long uuid) {
		return baseDao.findOne(uuid);
	}

	@Override
	public void delete(String ids) {
		if (ids != null && !ids.equals("")) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				baseDao.delete(Long.parseLong(id));
			}
		}
	}

	@Override
	public void save(List<T> list) {
		baseDao.save(list);
	}

	@Override
	public List<T> findAll(Specification<T> spec) {
		return baseDao.findAll(spec);
	}
}
