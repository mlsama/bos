package com.itheima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itheima.crm.domain.Customer;

public interface CustomerService {
	/**
	 * 查询未关联的客户
	 */
	@GET
	@Path("/findNoAssociateCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> findNoAssociateCustomers();

	/**
	 * 查询已经关联的客户
	 * fixedAreaId: 需要关联的定区ID
	 */
	@GET
	@Path("/findHasAssoicateCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> findHasAssoicateCustomers(@QueryParam("fixedAreaId")Long fixedAreaId);
	
	/**
	 * 绑定客户到定区
	 */
	@PUT
	@Path("associateCustomerToFixedArea")
	public void associateCustomerToFixedArea(@QueryParam("fixedAreaId")Long fixedAraeId,@QueryParam("customerId")String customerId);
	
	/**
	 * 保存客户
	 */
	@Path("/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void save(Customer customer);
	
	/**
	 * 根据手机号码查询客户
	 */
	@Path("/findByTelephone")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer findByTelephone(@QueryParam("telephone")String telephone);
	
	/**
	 * 激活客户
	 */
	@Path("/activeCustomer")
	@PUT
	public void activeCustomer(@QueryParam("telephone")String telephone);
	
	/**
	 * 登录客户
	 */
	@Path("login")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer login(@QueryParam("telephone")String telephone,@QueryParam("password")String password);
	
	
	/**
	 * 根据地址查询客户
	 */
	@GET
	@Path("/findByAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer findByAddress(@QueryParam("address")String address);
	
	/**
	 * 根据客户ID查询客户
	 */
	@GET
	@Path("/findById")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer findById(@QueryParam("id")Long id);
}
