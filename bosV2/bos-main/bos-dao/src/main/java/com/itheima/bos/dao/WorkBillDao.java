package com.itheima.bos.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.take_delivery.WorkBill;

/**
 * 工单的dao
 * @author lenovo
 *
 */
public interface WorkBillDao extends BaseDao<WorkBill>{
	/**
	 * 下拉框改变运单状态
	 */
	@Query("update WorkBill set pickstate = ? where id = ?")
	@Modifying
	public void changeStatus(String pickstate, Long id);
}
