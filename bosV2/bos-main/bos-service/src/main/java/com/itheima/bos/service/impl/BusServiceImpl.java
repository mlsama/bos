package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.BusDao;
import com.itheima.bos.domain.base.Bus;
import com.itheima.bos.service.BusService;

@Service
@Transactional // 业务层执行事务操作
public class BusServiceImpl extends BaseServiceImpl<Bus> implements BusService {
	
	private BusDao busDao;
	
	@Resource
	public void setBusDao(BusDao busDao) {
		this.busDao = busDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(busDao);
	}
	
}
