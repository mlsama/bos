package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.FixedAreaService;
import com.itheima.bos.utils.Constant;
import com.itheima.crm.domain.Customer;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/fixedArea")
public class FixedAreaAction extends BaseAction<FixedArea> {

	private FixedAreaService fixedAreaService;
	
	@Resource
	public void setFixedAreaService(FixedAreaService fixedAreaService) {
		this.fixedAreaService = fixedAreaService;
		super.setBaseService(fixedAreaService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<FixedArea> buildSpecification() {
		final FixedArea model = this.getModel();
		// 添加一个Specification条件
		Specification<FixedArea> spec = new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();


				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}
	
	
	/**
	 * 查询未关联的客户
	 * @throws Exception 
	 */
	@Action("findNoAssociateCustomers")
	public String findNoAssociateCustomers() throws Exception{
		
		List<Customer> list = (List<Customer>)WebClient
			.create(Constant.CRM_URL+"/services/customerService/findNoAssociateCustomers")
			.accept(MediaType.APPLICATION_JSON)
			.getCollection(Customer.class);
		
		//返回json格式给页面
		return writeJson(list);
	}
	
	/**
	 * 查询已经关联的客户
	 * @throws Exception 
	 */
	@Action("findHasAssoicateCustomers")
	public String findHasAssoicateCustomers() throws Exception{
		
		List<Customer> list = (List<Customer>)WebClient
			.create(Constant.CRM_URL+"/services/customerService/findHasAssoicateCustomers?fixedAreaId="+getUuid())
			.accept(MediaType.APPLICATION_JSON)
			.getCollection(Customer.class);
		
		//返回json格式给页面
		return writeJson(list);
	}
	
	//客户ID
	private String customerId;
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * 绑定客户到定区
	 * @throws Exception 
	 */
	@Action("associateCustomerToFixedArea")
	public String associateCustomerToFixedArea() throws Exception{
		
		try {
			WebClient
				.create(Constant.CRM_URL+"/services/customerService/associateCustomerToFixedArea?fixedAreaId="+getUuid()+"&customerId="+customerId)
				.put(null);
			
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
	
}
