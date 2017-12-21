package com.itheima.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.take_delivery.WorkBill;

public interface WorkBillService extends BaseService<WorkBill>{
	
	/**
	 * 下拉框改变运单状态
	 */
	public void changeStatus(WorkBill workBill);
	
}
