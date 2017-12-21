package com.itheima.bos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.AreaDao;
import com.itheima.bos.dao.CourierDao;
import com.itheima.bos.dao.OrderDao;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.CourierService;

@Service
@Transactional // 业务层执行事务操作
public class CourierServiceImpl extends BaseServiceImpl<Courier> implements CourierService {
	
	private CourierDao courierDao;
	
	@Resource
	public void setCourierDao(CourierDao courierDao) {
		this.courierDao = courierDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(courierDao);
	}
	
	//注入
	@Resource
	private OrderDao orderDao;
	@Resource
	private AreaDao areaDao;
	/**
	 * 通过订单所在区域查询下属所有的快递员
	 * @return
	 */
	@Override
	public List<Courier> findCourierByOrder(Long uuid) {
		//查询订单持久态对象
		Order persistOrder = orderDao.findOne(uuid);
		//根据订单所在省市区查询区域
		Area area = persistOrder.getSendArea();
		//把区域转为持久态
		Area persistArea = areaDao.findByProvinceAndCityAndDistrict(area.getProvince(), area.getCity(), area.getDistrict());
		//获取区域中的所有分区
		Set<SubArea> subAreas = persistArea.getSubAreas();
		List<Courier> list = new ArrayList<Courier>();
		for (SubArea subArea : subAreas) {
			list.add(subArea.getFixedArea().getCourier());
		}
		return list;
	}
	
}
