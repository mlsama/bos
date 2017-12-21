package com.itheima.bos.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.take_delivery.WayBill;

/**
 * 运单的dao
 * @author lenovo
 *
 */
public interface WayBillDao extends BaseDao<WayBill>{

	public WayBill findByWayBillNum(String wayBillNum);
	
	@Query("update WayBill set isDelete = '0' where id = ?")
	@Modifying
	public void cancelWayBill(long parseLong);
	
	@Query("update WayBill set isDelete = '1' where id = ?")
	@Modifying
	public void restoreWayBill(long parseLong);
}
