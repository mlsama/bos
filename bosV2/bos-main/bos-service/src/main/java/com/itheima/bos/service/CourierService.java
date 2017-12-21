package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.base.Courier;

public interface CourierService extends BaseService<Courier>{
	/**
	 * 通过订单所在区域查询下属所有的快递员
	 * @return
	 */
	List<Courier> findCourierByOrder(Long uuid);
	
}
