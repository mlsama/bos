package com.itheima.bos.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.utils.Constant;
import com.itheima.crm.domain.Customer;
import com.opensymphony.xwork2.ActionContext;

/**
 * 订单的Action
 * @author lenovo
 *
 */
public class OrderAction extends BaseAction<Order>{

	@Override
	protected Specification<Order> buildSpecification() {
		return null;
	}
	
	//接收寄件人区域字符串
	private String sendAreaInfo;
	//接收收件人区域字符串
	private String recAreaInfo;
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}

	@Action("saveOrder")
	public String saveOrder() throws Exception{
		Order order = this.getModel();
		
		//获取当前登录的客户
		Customer loginCustomer = (Customer)ActionContext.getContext().getSession().get("loginCustomer");
		if(loginCustomer==null){
			result.put("success", false);
			result.put("msg", "下单前请先登录");
		}else{
			try {
				//给订单的客户编号赋值
				order.setCustomerId(loginCustomer.getId());
				
				//封装寄件人区域对象
				if(sendAreaInfo!=null){
					String[] sendAreaInfoArray = sendAreaInfo.split("/");
					Area sendArea = new Area();
					sendArea.setProvince(sendAreaInfoArray[0]);
					sendArea.setCity(sendAreaInfoArray[1]);
					sendArea.setDistrict(sendAreaInfoArray[2]);
					order.setSendArea(sendArea);
				}
				
				//封装收件人区域对象
				if(recAreaInfo!=null){
					String[] recAreaInfoArray = recAreaInfo.split("/");
					Area recArea = new Area();
					recArea.setProvince(recAreaInfoArray[0]);
					recArea.setCity(recAreaInfoArray[1]);
					recArea.setDistrict(recAreaInfoArray[2]);
					order.setRecArea(recArea);
				}
				
				//调用bos后端系统，保存订单
				WebClient
					.create(Constant.BOS_BACK_URL+"/services/orderService/saveOrder")
					.type(MediaType.APPLICATION_JSON)
					.post(order);
				
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}
		return writeJson(result);
	}

}
