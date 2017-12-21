package com.itheima.bos.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm{

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行了授权方法");
		
		//基于资源授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("standard_add");
		info.addStringPermission("standard_update");
		info.addStringPermission("standard:*");
		
		//基于角色授权
		info.addRole("admin");
		
		return info;
	}

	//认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行了认证方法");
		
		//模拟数据库保存的用户名和密码
		String dbUser = "eric";
		String dbPassword = "123456";
		
		//获取用户输入的用户名和密码
		UsernamePasswordToken userToken = (UsernamePasswordToken)token; 
		
		//判断用户名是否存在
		if(!userToken.getUsername().equals(dbUser)){
			//不存在
			//通知shiro返回null即可
			return null;
		}
		
		//判断密码是否正确(密码不用判断)
		/**
		 * 参数一：用户会话数据  principal
		 * 参数二：安全密码
		 * 参数三：起realm别名
		 */
		return new SimpleAuthenticationInfo(dbUser,dbPassword,dbUser);
	}

}
