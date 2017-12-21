package com.itheima.bos.realm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.system.Resources;
import com.itheima.bos.domain.system.User;

/**
 * bos系统的realm
 */
public class BosRealm extends AuthorizingRealm{

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权");
	/*	
		//授权代码
		SimpleAuthorizationInfo  info = new SimpleAuthorizationInfo();
		info.addStringPermission("standard:*");
		*/
		
		//查询当前登录用户的授权码
		//1.获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		User loginUser = (User)subject.getPrincipal();
		
		SimpleAuthorizationInfo  info = new SimpleAuthorizationInfo();
		
		//2.查询当前登录用户的授权码
		List<Resources> resList = userDao.findMyPerms(loginUser.getId());
		
		for (Resources resources : resList) {
			info.addStringPermission(resources.getGrantKey());
		}
		
		return info;
	}
	
	//注入userDao
	@Resource
	private UserDao userDao;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行认证");
		
		//获取到用户输入的信息
		UsernamePasswordToken userToken = (UsernamePasswordToken)token;
		
		//1.判断用户名是否存在
		//连接数据库判断用户名
		User loginUser = userDao.findByUsername(userToken.getUsername());
		if(loginUser==null){
			//用户不存在
			return null;
		}
		
		//2.返回数据库的密码通知shiro（shiro会判断密码）
		//参数一：shiro的principal数据
		return new SimpleAuthenticationInfo(loginUser,loginUser.getPassword(),loginUser.getUsername());
	}

}
