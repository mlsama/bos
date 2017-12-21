package com.itheima.bos.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.WayBillService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/wayBill")
public class WayBillAction extends BaseAction<WayBill> {

	private WayBillService wayBillService;
	
	@Resource
	public void setWayBillService(WayBillService wayBillService) {
		this.wayBillService = wayBillService;
		super.setBaseService(wayBillService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<WayBill> buildSpecification() {
		final WayBill model = this.getModel();
		// 添加一个Specification条件
		Specification<WayBill> spec = new Specification<WayBill>() {

			@Override
			public Predicate toPredicate(Root<WayBill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();
				if(StringUtils.isNoneBlank(model.getWayBillNum())){
					preList.add(cb.like(root.get("wayBillNum").as(String.class), "%"+model.getWayBillNum()+"%"));
				}
				if(StringUtils.isNoneBlank(model.getSendAddress())){
					preList.add(cb.like(root.get("sendAddress").as(String.class), "%"+model.getSendAddress()+"%"));
				}
				if(StringUtils.isNoneBlank(model.getRecAddress())){
					preList.add(cb.like(root.get("recAddress").as(String.class), "%"+model.getRecAddress()+"%"));
				}
				if(StringUtils.isNoneBlank(model.getSendProNum())){
					preList.add(cb.like(root.get("sendProNum").as(String.class), "%"+model.getSendProNum()+"%"));
				}
				if(StringUtils.isNoneBlank(model.getSignStatus())){
					preList.add(cb.like(root.get("signStatus").as(String.class), "%"+model.getSignStatus()+"%"));
				}
				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}
	
	/**
	 * 根据运单号查询运单
	 * @throws Exception 
	 */
	@Action("findByWayBillNum")
	public String findByWayBillNum() throws Exception{
		WayBill wayBill = this.getModel();
		WayBill persistWayBill = wayBillService.findByWayBillNum(wayBill.getWayBillNum());
		return writeJson(persistWayBill);
	}

	/**
	 * 录入运单的方法
	 * @throws Exception 
	 */
	@Action("saveWayBill")
	public String saveWayBill() throws Exception{
		WayBill wayBill = this.getModel();
		
		//当运单没有订单ID编号的时候，取消运单和订单的关联关系
		if(wayBill.getOrder()==null || wayBill.getOrder().getId()==null){
			wayBill.setOrder(null); //取消关联关系
		}
		
		if( wayBill.getWayBillNum()!=null && !wayBill.getWayBillNum().equals("")){
			//判断运单号是否不为空，且存在数据库中
			//根据运单号查询运单
			WayBill persistWayBill = wayBillService.findByWayBillNum(wayBill.getWayBillNum());
			
			if(persistWayBill!=null){
					//如果调用save方法，需要执行更新操作，那么保存的对象必须有OID
					//给运单赋值ID
					wayBill.setId(persistWayBill.getId());
					//存在，则执行更新运单操作
			}else{
				//生成运单号
				wayBill.setWayBillNum(UUID.randomUUID().toString());
			}
		}else{
			//生成运单号
			wayBill.setWayBillNum(UUID.randomUUID().toString());
		}
		
		try {
			
			//不存在，则执行新增运单操作
			wayBillService.save(wayBill);
			
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
	//加入wayBillIds字段
	private String wayBillIds;

	public void setWayBillIds(String wayBillIds) {
		this.wayBillIds = wayBillIds;
	}
	
	//查询可作废的运单
	@Action("findCanCancelWayWill")
	public String findCanCancelWayWill() throws Exception{
		List<WayBill> list = wayBillService.findCanCancelWayWill(wayBillIds);
		result.put("total", list.size());
		result.put("rows", list);
		return writeJson(result);
	}
	
	//作废运单
	@Action("cancelWayBill")
	public String cancelWayBill() throws Exception{
		try {
			wayBillService.cancelWayBill(wayBillIds);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
	
	//还原运单
	@Action("restoreWayBill")
	public String restoreWayBill() throws Exception{
		try {
			wayBillService.restoreWayBill(wayBillIds);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
}
