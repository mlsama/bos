package com.itheima.bos.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.system.Resources;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.exceptions.LoginFailException;
import com.itheima.bos.service.UserService;

@Service
@Transactional // 业务层执行事务操作
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	private UserDao userDao;

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		// 给BaseServiceImpl的baseDao赋值
		super.setBaseDao(userDao);
	}
	
	//注入roleDao
	@Resource
	private RoleDao roleDao;

	@Override
	public void bindRoleToUser(Long userId, String roleIds) {
		// 1.清除当前用户绑定过的角色
		// 1.1 获取当前用户对象（持久态对象）
		User persistUser = userDao.findOne(userId);
		// 1.2 清除当前用户的角色
		persistUser.setRoles(null);

		// 2.把新的角色绑定到当前用户
		Set<Role> rolesSet = new HashSet<Role>();
		if (roleIds != null && !roleIds.equals("")) {
			String[] roleIdArray = roleIds.split(",");
			for (String roleId : roleIdArray) {
				// 查询角色，并且放入集合中
				rolesSet.add(roleDao.findOne(Long.parseLong(roleId)));
			}
		}

		persistUser.setRoles(rolesSet);
	}

	@Override
	public User login(User user) throws LoginFailException {
		//判断用户名是否存在
		User loginUser = userDao.findByUsername(user.getUsername());
		
		if(loginUser==null){
			//抛出自定义异常
			throw new LoginFailException("用户名不存在");
		}
		//判断密码是否正确
		if(!loginUser.getPassword().equals(user.getPassword())){
			throw new LoginFailException("密码输入有误");
		}
		
		//判断用户状态是否有效
		if(!loginUser.getStation().equals("1")){
			throw new LoginFailException("该用户已锁定，请联系管理员");
		}
		
		return loginUser;
	}

	@Override
	public List<Resources> findMyMenus(Long id) {
		return userDao.findMyMenus(id);
	}
	
	/**
	 * 重写save
	 */
	@Override
	public void save(User model) {
		// 判断是角色的添加还是角色修改的操作
		if (model != null && model.getId() != null) {
			// 修改
			// 查询当前角色绑定过的资源
			User persisUser = userDao.findOne(model.getId());
			// persisUser:是持久态对象，对persisUser对象的更新操作都会反映到数据库的
			Set<Role> roleSet = persisUser.getRoles();
			// 把原有的资源的集合设置回当前角色对象中
			model.setRoles(roleSet);
		}
		// 添加
		super.save(model);
	}
}
