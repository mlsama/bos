package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.system.Resources;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.exceptions.LoginFailException;

public interface UserService extends BaseService<User>{

	public void bindRoleToUser(Long userId, String roleIds);

	public User login(User user) throws LoginFailException ;

	public List<Resources> findMyMenus(Long id);
	
}
