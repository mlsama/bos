package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.take_delivery.WayBill;

public interface WayBillService extends BaseService<WayBill>{

	public WayBill findByWayBillNum(String wayBillNum);
	
	//查询可作废运单
	public List<WayBill> findCanCancelWayWill(String wayBillIds);
	
	//作废运单
	public void cancelWayBill(String wayBillIds);
	
	//还原
	public void restoreWayBill(String wayBillIds);
}
