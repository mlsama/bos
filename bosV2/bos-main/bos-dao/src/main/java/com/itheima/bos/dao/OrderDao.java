package com.itheima.bos.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.take_delivery.Order;

/**
 * 订单的dao
 * @author lenovo
 *
 */
public interface OrderDao extends BaseDao<Order>{

	public Order findByOrderNum(String orderNum);
	
	@Query("update Order set status = 7 where orderNum = ?")
	@Modifying
	public void cancelOrder(String orderNum);

}
