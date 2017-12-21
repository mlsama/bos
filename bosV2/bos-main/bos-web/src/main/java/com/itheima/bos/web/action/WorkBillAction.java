package com.itheima.bos.web.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.WorkBillService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/workBill")
public class WorkBillAction extends BaseAction<WorkBill> {

	private WorkBillService workBillService;
	
	@Resource
	public void setWorkBillService(WorkBillService workBillService) {
		this.workBillService = workBillService;
		super.setBaseService(workBillService);
	}
	
	//接收页面出传过来的创建时间范围
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
	protected Specification<WorkBill> buildSpecification() {
		final WorkBill model = this.getModel();		
		// 添加一个Specification条件
		Specification<WorkBill> spec = new Specification<WorkBill>() {

			@Override
			public Predicate toPredicate(Root<WorkBill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();
				
				//根据订单编号查询
				if(model.getOrder() != null && StringUtils.isNotBlank(model.getOrder().getOrderNum())) {
					preList.add(cb.like(root.get("order").get("orderNum").as(String.class),"%"+ model.getOrder().getOrderNum() + "%"));
				}
				//根据创建时间查询
				if(startTime != null && endTime != null) {
					preList.add(cb.between(root.<Date>get("buildtime"), startTime,endTime));
				}
				//当没有结束时间的时候，把系统时间设置成结束时间
				if(startTime != null && endTime == null) {
					preList.add(cb.between(root.<Date>get("buildtime"), startTime,new Date()));
				}
				
				//根据快递员查询
				if(model.getCourierId() != null && StringUtils.isNotBlank(model.getCourierId().getName())) {
					preList.add(cb.like(root.get("courierId").get("name").as(String.class), "%" + model.getCourierId().getName() + "%"));
				}

				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}
	
	/**
	 * 下拉框改变运单状态
	 * @throws Exception 
	 */
	@Action("changeStatus")
	public String changeStatus() throws Exception{
		try {
			WorkBill workBill = this.getModel();
			workBillService.changeStatus(workBill);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
}
