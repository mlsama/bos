package com.itheima.bos.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.take_delivery.Order;

public interface OrderService extends BaseService<Order>{
	
	@Path("/saveOrder")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveOrder(Order order);

	public Order findByOrderNum(String orderNum);
	//人工分单
	public void bindOrderToCourier(Long id, Long uuid);
	
	public void cancelOrder(String status, String orderNum);
}
