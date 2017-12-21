package com.itheima.bos.web.action;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.itheima.bos.utils.Constant;
import com.itheima.bos.utils.MailUtils;
import com.itheima.bos.utils.SmsUtils;
import com.itheima.crm.domain.Customer;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/customer")
public class CustomerAction extends BaseAction<Customer>{

	@Override
	protected Specification<Customer> buildSpecification() {
		return null;
	}

	
	@Action("sendSms")
	public String sendSms() throws Exception{
		Customer customer = this.getModel();
		
		String telephone = customer.getTelephone();
		
		//系统生成4位的数字的随机验证码字符串
		String code = RandomStringUtils.randomNumeric(4);
		
		System.out.println("验证码："+code);
		
		//把手机验证码放入会话域
		ActionContext.getContext().getSession().put("code", code);
		
		//发送短信
		String result = SmsUtils.sendSms(
					"物流系统", 
					"{\"name\":\""+telephone+"\",\"product\":\"bos物流系统\",\"code\":\""+code+"\"}", telephone, "SMS_53470018");
		
		//直接写出result字符串给浏览器
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(result);
		
		return NONE;
	}
	
	//接收手机验证码
	private String validCode;
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
	//注入redisTemplate对象
	@Resource
	private RedisTemplate<String,String> redisTemplate;
	
	/**
	 * 注册
	 * @throws Exception 
	 */
	@Action("regist")
	public String regist() throws Exception{
		Customer customer = this.getModel();
		
		//判断手机验证码是否正确
		String code = (String)ActionContext.getContext().getSession().get("code");
		
		if(!validCode.equals(code)){
			//验证码输入有误
			result.put("success", false);
			result.put("msg", "验证码输入有误");
		}else{
			try {
				//判断该手机用户是否注册过
				Customer cust = WebClient
					.create(Constant.CRM_URL+"/services/customerService/findByTelephone?telephone="+customer.getTelephone())
					.accept(MediaType.APPLICATION_JSON)
					.get(Customer.class);
				
				if(cust!=null){
					//该手机已注册
					result.put("success", false);
					result.put("msg", "该手机已注册");
				}else{
					//远程调用CMR系统进行保存用户数据
					WebClient
						.create(Constant.CRM_URL+"/services/customerService/save")
						.type(MediaType.APPLICATION_JSON)
						.post(customer);
					
					//系统生成一个随机激活码
					String activeCode = UUID.randomUUID().toString();
					
					//使用spring data redis实现把随机激活码保存到redis
					redisTemplate.opsForValue().set(customer.getTelephone(), activeCode,48,TimeUnit.HOURS);
					
					//发送一封激活邮件
					String content = "尊敬的"+customer.getTelephone()+":<br/>请在48小时内使用以下链接进行邮件激活：<a href='"+Constant.BOS_FORE_URL+"/customer/activeCustomer.action?telephone="+customer.getTelephone()+"&activeCode="+activeCode+"'>激活链接</a>";
					MailUtils.sendMail(
							customer.getEmail(), "BOS2.0物流系统激活邮件", content);
					
					
					result.put("success", true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}
		return writeJson(result);
	}
	
	//接收激活码
	private String activeCode;
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	/**
	 * 激活客户方法
	 * @throws Exception 
	 */
	@Action("activeCustomer")
	public String activeCustomer() throws Exception{
		Customer customer = this.getModel();
		
		//判断激活码是否合法（   1）redis存在激活码  2）redis保存的激活码和用户传递的激活码一致的    ）
		//取出redis保存的激活码
		String redisActiveCode = redisTemplate.opsForValue().get(customer.getTelephone());
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		
		if(redisActiveCode==null || !activeCode.equals(redisActiveCode)){
			//激活码不合法
			response.getWriter().write("激活码不合法或已过时");
			return NONE;
		}else{
			
			try {
				//调用CRM系统激活客户
				WebClient
					.create(Constant.CRM_URL+"/services/customerService/activeCustomer?telephone="+customer.getTelephone())
					.put(null);
				
				//删除redis的激活码
				redisTemplate.delete(customer.getTelephone());
				
				response.getWriter().write("激活客户成功");
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("激活客户失败");
			}
			return NONE;
		}
	}
	
	
	/**
	 * 登录
	 * @throws Exception 
	 */
	@Action("login")
	public String login() throws Exception{
		Customer customer = this.getModel();
		
		//调用CRM系统进行登录
		Customer loginCustomer = WebClient
			.create(Constant.CRM_URL+"/services/customerService/login?telephone="+customer.getTelephone()+"&password="+customer.getPassword()+"")
			.accept(MediaType.APPLICATION_JSON)
			.get(Customer.class);
		
		if(loginCustomer==null){
			//登录失败
			result.put("success", false);
			result.put("msg", "用户名或密码有误");
		}else{
			//判断是否已经激活
			if(loginCustomer.getType()==null || !loginCustomer.getType().equals("1")){
				//未激活用户
				result.put("success", false);
				result.put("msg", "请先激活用户");
			}else{
				//登录成功
				
				//把登录用户信息放入到会话中
				ActionContext.getContext().getSession().put("loginCustomer", loginCustomer);
				
				result.put("success", true);
			}
		}
		return writeJson(result);
	}
	
}
