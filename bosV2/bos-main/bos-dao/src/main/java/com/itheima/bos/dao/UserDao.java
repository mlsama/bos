package com.itheima.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Resources;
import com.itheima.bos.domain.system.User;

/**
 * 用户的dao
 * @author lenovo
 *
 */
public interface UserDao extends BaseDao<User>{

	public User findByUsername(String username);

	/**
	 * 查询某个用户的菜单资源列表
	 * @param id
	 * @return
	 */
	@Query("select res from User u inner join u.roles role inner join role.resourceses res where u.id=? and res.resourceType='0'")
	public List<Resources> findMyMenus(Long id);

	/**
	 * 查询某个用户的所有资源列表
	 * @param id
	 * @return
	 */
	@Query("select res from User u inner join u.roles role inner join role.resourceses res where u.id=?")
	public List<Resources> findMyPerms(Long id);

}
