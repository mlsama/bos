package com.itheima.bos.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.CourierDao;
import com.itheima.bos.dao.WorkBillDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.WorkBillService;
import com.itheima.bos.utils.MySmsUtils;

@Service
@Transactional // 业务层执行事务操作
public class WorkBillServiceImpl extends BaseServiceImpl<WorkBill> implements WorkBillService {
	
	private WorkBillDao workBillDao;
	
	@Resource
	public void setWorkBillDao(WorkBillDao workBillDao) {
		this.workBillDao = workBillDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(workBillDao);
	}
	
	/**
	 * 下拉框改变运单状态
	 */
	@Override
	public void changeStatus(WorkBill workBill) {
		workBillDao.changeStatus(workBill.getPickstate(),workBill.getId());
	}
	
	//注入CourierDao
	@Resource
	private CourierDao courierDao;
	/**
	 * 重写save方法
	 */
	@Override
	public void save(WorkBill model) {
		//判断一下是新增还是修改
		if(model != null && model.getId() !=null){
			//查询一下持久态对象
			WorkBill persistWorkBill = workBillDao.findOne(model.getId());
			persistWorkBill.setCourierId(model.getCourierId());
			persistWorkBill.setRemark(model.getRemark());
			Courier persistCourier = courierDao.findOne(model.getCourierId().getId());
			//发短信通知修改的快递员
			//发送取件短信给快递员,使用自己的工具类
			MySmsUtils.sendSms("物流系统", 
					"{\"name\":\""+persistCourier.getName()+"\",\"address\":\""+persistWorkBill.getOrder().getSendAddress()+"\",\"phone\":\""+persistWorkBill.getOrder().getSendMobile()+"\",\"message\":\""+persistWorkBill.getOrder().getSendMobileMsg()+"\"}", 
					persistCourier.getTelephone(), 
					"SMS_85565036");
		}else{
			super.save(model);
		}
	}
}
