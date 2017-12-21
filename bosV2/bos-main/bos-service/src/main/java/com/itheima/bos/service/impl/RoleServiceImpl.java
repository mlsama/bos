package com.itheima.bos.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ResourcesDao;
import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.system.Resources;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.RoleService;

@Service
@Transactional // 业务层执行事务操作
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	private RoleDao roleDao;

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
		// 给BaseServiceImpl的baseDao赋值
		super.setBaseDao(roleDao);
	}

	// 注入resourcesDao
	@Resource
	private ResourcesDao resourcesDao;
	
	//注入userDao
	@Resource
	private UserDao userDao;

	/**
	 * 角色绑定资源
	 */
	@Override
	public void bindResToRole(Long roleId, String resIds) {
		// 1.清除当前角色绑定过的资源
		// 1.1 获取当前角色对象（持久态对象）
		Role persistRole = roleDao.findOne(roleId);
		// 1.2 清除当前角色的资源
		persistRole.setResourceses(null);

		// 2.把新的资源绑定到当前角色
		Set<Resources> resSet = new HashSet<Resources>();
		if (resIds != null && !resIds.equals("")) {
			String[] resIdArray = resIds.split(",");
			for (String resId : resIdArray) {
				// 查询资源，并且放入集合中
				resSet.add(resourcesDao.findOne(Long.parseLong(resId)));
			}
		}

		persistRole.setResourceses(resSet);
	}

	@Override
	public Page<Role> findAll(Specification<Role> spec, Pageable pageable, Long userId) {
		Page<Role> pageData = roleDao.findAll(spec, pageable);

		// ***修改Role对象的checked属性值***
		// 1）当前用户有绑定该角色对象，checked属性为true
		// 2)当前用户没有绑定该角色对象，checked属性为false

		// 取出当前用户的所有绑定的角色
		User pesistUser = userDao.findOne(userId);
		Set<Role> roleSet = pesistUser.getRoles();

		// 把所有的资源ID封装成集合
		Set<Long> roleIdSet = new HashSet<Long>();
		for (Role role : roleSet) {
			roleIdSet.add(role.getId());
		}

		List<Role> content = pageData.getContent();
		for (Role role : content) {
			// roleIdSet.contains(role.getId()： 判断当前遍历的角色ID是否 存在于 绑定过的角色ID里面
			role.setChecked(roleIdSet.contains(role.getId()));
		}

		return pageData;
	}

	/**
	 * 重写save
	 */
	@Override
	public void save(Role model) {
		
		//判断是角色的添加还是角色修改的操作
		if(model!=null && model.getId()!=null){
			//修改
			//查询当前角色绑定过的资源
			Role persisRole = roleDao.findOne(model.getId()); //persisRole:是持久态对象，对persisRole对象的更新操作都会反映到数据库的
			Set<Resources> resSet = persisRole.getResourceses();
			
			/*
			//把原有的资源的集合设置回当前角色对象中
			model.setResourceses(resSet);
			super.save(model);
			*/
			
			//拷贝对象的属性
			BeanUtils.copyProperties(model, persisRole);
			persisRole.setResourceses(resSet);
		}else{
			//添加
			super.save(model);
		}
	}
}
