package com.itheima.bos.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ResourcesDao;
import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.system.Resources;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.ResourcesService;

@Service
@Transactional // 业务层执行事务操作
public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements ResourcesService {
	
	private ResourcesDao resourcesDao;
	
	@Resource
	public void setResourcesDao(ResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(resourcesDao);
	}
	
	//注入roleDao
	@Resource
	private RoleDao roleDao;

	@Override
	public Page<Resources> findAll(Specification<Resources> spec, Pageable pageable, Long roleId) {
		Page<Resources> pageData = resourcesDao.findAll(spec, pageable);
		
		//***修改Resources对象的checked属性值***
		//1）当前角色有绑定该资源对象，checked属性为true
		//2)当前角色没有绑定该资源对象，checked属性为false

		//取出当前角色的所有绑定的资源
		Role pesistRole = roleDao.findOne(roleId);
		Set<Resources> resSet = pesistRole.getResourceses();
		
		//把所有的资源ID封装成集合
		Set<Long> resIdSet = new HashSet<Long>();
		for (Resources res : resSet) {
			resIdSet.add(res.getId());
		}
		
		List<Resources> content = pageData.getContent();
		for (Resources resources : content) {
			//resIdSet.contains(resources.getId()： 判断当前遍历的资源ID是否  存在于 绑定过的资源ID里面
			resources.setChecked(resIdSet.contains(resources.getId()));
		}
		
		return pageData;
	}
	
}
