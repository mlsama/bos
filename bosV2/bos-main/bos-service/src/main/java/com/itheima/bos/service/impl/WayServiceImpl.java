package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.WayDao;
import com.itheima.bos.domain.base.Way;
import com.itheima.bos.service.WayService;

@Service
@Transactional // 业务层执行事务操作
public class WayServiceImpl extends BaseServiceImpl<Way> implements WayService {
	
	private WayDao busDao;
	
	@Resource
	public void setWayDao(WayDao busDao) {
		this.busDao = busDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(busDao);
	}
	
}
