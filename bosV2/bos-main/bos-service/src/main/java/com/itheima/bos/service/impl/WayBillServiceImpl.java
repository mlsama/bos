package com.itheima.bos.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.WayBillDao;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.WayBillService;

@Service
@Transactional // 业务层执行事务操作
public class WayBillServiceImpl extends BaseServiceImpl<WayBill> implements WayBillService {
	
	private WayBillDao wayBillDao;
	
	@Resource
	public void setWayBillDao(WayBillDao wayBillDao) {
		this.wayBillDao = wayBillDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(wayBillDao);
	}

	@Override
	public WayBill findByWayBillNum(String wayBillNum) {
		return wayBillDao.findByWayBillNum(wayBillNum);
	}

	//分页可作废的运单
	@Override
	public List<WayBill> findCanCancelWayWill(String wayBillIds) {
		List<WayBill> list = new ArrayList<WayBill>();
		if(StringUtils.isNotBlank(wayBillIds)){
			String[] wayBillIdArray = wayBillIds.split(",");
			for (String wayBillId : wayBillIdArray) {
				WayBill wayBill = wayBillDao.findOne(Long.parseLong(wayBillId));
				if(!"0".equals(wayBill.getIsDelete())){
					list.add(wayBill);
				}
			}
		}
		return list;
	}
	
	//批量作废运单
	@Override
	public void cancelWayBill(String wayBillIds) {
		if(StringUtils.isNotBlank(wayBillIds)){
			String[] wayBillIdArray = wayBillIds.split(",");
			for (String wayBillId : wayBillIdArray) {
				wayBillDao.cancelWayBill(Long.parseLong(wayBillId));
			}
		}
	}
	
	//批量还原运单
	@Override
	public void restoreWayBill(String wayBillIds) {
		if(StringUtils.isNotBlank(wayBillIds)){
			String[] wayBillIdArray = wayBillIds.split(",");
			for (String wayBillId : wayBillIdArray) {
				wayBillDao.restoreWayBill(Long.parseLong(wayBillId));
			}
		}
	}
}
