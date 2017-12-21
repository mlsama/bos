package com.itheima.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 演示shiro的认证和授权的案例
 * @author lenovo
 *
 */
public class ShiroDemo {

	@Test
	public void test(){
		//1.创建安全管理器工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro.ini");
		
		//2.创建安全管理器
		SecurityManager securityManager = factory.getInstance();
		
		//3.初始化SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		
		//4.获取Subject对象
		Subject subject = SecurityUtils.getSubject();
		
		//5.（认证）登录操作
		//模拟用户输入用户名和密码
		String user = "eric";
		String password = "123456";
		
		//封装用户输入的账户信息
		UsernamePasswordToken token = new UsernamePasswordToken(user, password);
		
		try {
			subject.login(token);
			
			//isAuthenticated():判断当前用户是否认证成功  返回true代表成功；返回false：失败
			System.out.println(subject.isAuthenticated());
			
			System.out.println("登录成功");
			
			//shiro授权
			
			//基于资源的授权判断方法
			//需求：判断当前认证用户 有木有 收派标准添加 (授权码：standard_add)
			System.out.println(subject.isPermitted("standard_add"));
			System.out.println(subject.isPermitted("standard_update"));
			//通配符授权 ：standard:*
			System.out.println(subject.isPermitted("standard:add"));
			System.out.println(subject.isPermitted("standard:update"));
			
			//基于角色
			System.out.println(subject.hasRole("admin"));
		} catch (UnknownAccountException e) {
			System.out.println("登录失败:用户名不存在");
		}catch (IncorrectCredentialsException e) {
			System.out.println("登录失败:密码输入有误");
		}catch (Exception e) {
			System.out.println("登录失败:"+e.getMessage());
		}
	}
}
