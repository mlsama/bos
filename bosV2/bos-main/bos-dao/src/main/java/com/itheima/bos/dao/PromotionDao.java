package com.itheima.bos.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.take_delivery.Promotion;

/**
 * 宣传任务的dao
 * @author lenovo
 *
 */
public interface PromotionDao extends BaseDao<Promotion>{

	/**
	 * 更新宣传任务的status为0，条件是：当前系统时间>endDate 且 status为1
	 * @param date
	 */
	@Query("update Promotion set status='0' where endDate<? and status='1'")
	@Modifying
	public void updateStatus(Date date);

}
