package com.itheima.bos.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Resources;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.UserService;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/user")
public class UserAction extends BaseAction<User> {

	private UserService userService;
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
		super.setBaseService(userService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<User> buildSpecification() {
		final User model = this.getModel();
		// 添加一个Specification条件
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();
				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}
	
	//接收用户和角色id
	private Long userId;
	private String roleIds;
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * 绑定角色到用户
	 * @throws Exception 
	 */
	@Action("bindRoleToUser")
	public String bindRoleToUser() throws Exception{
		try {
			userService.bindRoleToUser(userId,roleIds);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return writeJson(result);
	}
	
	
	//接收验证码
	private String validCode;
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	/**
	 * 登录
	 * @throws Exception 
	 */
	@Action("login")
	public String login() throws Exception{
		User user = this.getModel();
		
		//判断验证是否输入正确
		//获取系统生成的验证码吗，从session的key
		String key = (String)ActionContext.getContext().getSession().get("key");
		if(!key.equals(validCode)){
			result.put("success", false);
			result.put("msg", "验证码输入有误");
		}else{
			//没有shiro的认证过程
			/*User loginUser = null;
			try {
				loginUser = userService.login(user);
				
				//登录成功
				//把loginUser保存到session
				ActionContext.getContext().getSession().put("loginUser", loginUser);
				
				result.put("success", true);
			} catch (LoginFailException e) {
				//登录异常
				result.put("success", false);
				result.put("msg", e.getMessage());
			} catch (Exception e) {
				//系统异常
				result.put("success", false);
				result.put("msg", e.getMessage());
			}*/
			
			
			//有shiro的认证过程
			//1.获取subject
			Subject subject = SecurityUtils.getSubject();
			
			//使用shiro进行md5加盐
			Md5Hash hash = new Md5Hash(user.getPassword(),user.getUsername(),3);
			
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), hash.toString());
			
			try {
				//2.登录认证
				subject.login(token);
				
				//3.获取到shiro保存的principle数据
				User loginUser = (User)subject.getPrincipal();
				
				//4.我们也要把principle数据放入session中
				ActionContext.getContext().getSession().put("loginUser", loginUser);
				
				result.put("success", true);
			} catch (UnknownAccountException e) {
				result.put("success", false);
				result.put("msg", "用户名不存在");
			} catch (IncorrectCredentialsException e) {
				result.put("success", false);
				result.put("msg", "密码输入有误");
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "登录失败:"+e.getMessage());
			} 
			
		}	
		return writeJson(result);
	}

	/**
	 * 根据登录用户查询菜单资源
	 * @throws Exception 
	 */
	@Action("findMyMenus")
	public String findMyMenus() throws Exception{
		
		//从session获取登录用户
		User loginUser = (User)ActionContext.getContext().getSession().get("loginUser");
		
		if(loginUser!=null){
			//根据登录用户查询菜单资源
			List<Resources> menuResList = userService.findMyMenus(loginUser.getId());
			return writeJson(menuResList);
		}
		return NONE;
		
		
	}
	
	/*
	 * 用户注销
	 */
	@Action(value="logout",results={@Result(name="success",location="/login.jsp",type="redirect")})
	public String logout(){
		//清空shiro认证成功后往session存放的数据
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return SUCCESS;
	}
	
	/**
	 * 重写save
	 */
	@Override
	@Action("save")
	public String save() throws Exception {
		//使用shiro对密码进行加盐
		User user = this.getModel();
		String password = user.getPassword();
		
		/**
		 * 参数一:原密码
		 * 参数二：加盐(保证每个用户都使用 不同的盐)
		 * 参数三：散列次数(使用盐运算几次)
		 */
		Md5Hash hash = new Md5Hash(password,user.getUsername(),3);
		user.setPassword(hash.toString());
		return super.save();
	}
}
