package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.OrderService;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.utils.MailUtils;
import com.itheima.crm.domain.Customer;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/order")
public class OrderAction extends BaseAction<Order> {

	private OrderService orderService;
	
	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
		super.setBaseService(orderService);
	}
	private Date startTime;
	private Date endTime;

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Order> buildSpecification() {
		final Order model = this.getModel();
		
		// 添加一个Specification条件
		Specification<Order> spec = new Specification<Order>() {
		
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();
				if(startTime!=null){
					preList.add(cb.greaterThanOrEqualTo(root.get("orderTime").as(Date.class),startTime));
				}
				if(endTime!=null){
					preList.add(cb.lessThan(root.get("orderTime").as(Date.class),new Date(endTime.getTime()+86400000L)));
				}
				
				if(model.getOrderNum()!=null&& !model.getOrderNum().equals("")){
					preList.add(cb.like(root.get("orderNum").as(String.class), "%"+model.getOrderNum()+"%"));
				}
				
				if(model.getSendProNum()!=null && !model.getSendProNum().equals("")){
					if(model.getSendProNum().equals("任意快递类型")){
						
					}else{
						
						preList.add(cb.equal(root.get("sendProNum").as(String.class), model.getSendProNum()));
					}
				}
				if(model.getStatus()!=null && !model.getStatus().equals("")){
					if(model.getStatus().equals("0")){
						
					}else{
						
						preList.add(cb.equal(root.get("status").as(String.class), model.getStatus()));
					}
				}
				if(model.getSendName()!=null && !model.getSendName().equals("")){
					preList.add(cb.like(root.get("sendName").as(String.class), "%"+model.getSendName()+"%"));
				}
				if(model.getRecName()!=null && !model.getRecName().equals("")){
					preList.add(cb.like(root.get("recName").as(String.class), "%"+model.getRecName()+"%"));
				}
				if(model.getRecMobile()!=null && !model.getRecMobile().equals("")){
					preList.add(cb.like(root.get("recMobile").as(String.class),"%"+model.getRecMobile()+"%"));
				}
				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}
	
	/**
	 * 根据订单号查询订单信息
	 * @throws Exception 
	 */
	@Action("findByOrderNum")
	public String findByOrderNum() throws Exception{
		Order order = this.getModel();
		Order persistOrder = orderService.findByOrderNum(order.getOrderNum());
		
		return writeJson(persistOrder);
	}
	
	
	/**
	 * 展示所有“已下单”（未分单成功）订单
	 * @throws Exception
	 */
	@Action("findUndistributedOrder")
	public String findUndistributedOrder() throws Exception{
		// 创建分页对象
		Pageable pageable = new PageRequest(getPage() - 1, getRows());
		// 自定义查询条件
		// 已下单  status = 1
		// 人工分单 orderType = 2
		Specification<Order> spec = new Specification<Order>() {
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> preList = new ArrayList<Predicate>();
				preList.add(cb.equal(root.get("status").as(String.class), "1"));
				preList.add(cb.equal(root.get("orderType").as(String.class), "2"));
				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}
		};
		
		// 查询数据
		Page<Order> pageBean = orderService.findAll(spec, pageable);
		// 取出总记录数
		long totalCount = pageBean.getTotalElements();
		// 取出当前页的数据列表
		List<Order> list = pageBean.getContent();
		result.put("total", totalCount);
		result.put("rows", list);
		return writeJson(result);
	}

	@Action("cancelOrder")
	public String cancelOrder() throws Exception{
		try {
			Order order = this.getModel();
			
			//取消订单,修改状态码
			orderService.cancelOrder(order.getStatus(),order.getOrderNum());
			
			Customer customer = WebClient.create(Constant.CRM_URL+"/services/customerService/findById?id="+order.getCustomerId())
			.accept(MediaType.APPLICATION_JSON)
			.get(Customer.class);
			
			//发送一封激活邮件
			String content = "尊敬的"+customer.getTelephone()+",您的订单号为"+order.getOrderNum()+"的订单已取消";
			MailUtils.sendMail(
					customer.getEmail(), "关于取消订单", content);
			result.put("success", "true");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg",e.getMessage());
		}
		return writeJson(result);
	}
	
	/**
	 * 人工分单
	 * @throws Exception 
	 */
	@Action("bindOrderToCourier")
	public String bindOrderToCourier() throws Exception{
		try {
			Order order = this.getModel();
			orderService.bindOrderToCourier(order.getId(),this.getUuid());
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
}
