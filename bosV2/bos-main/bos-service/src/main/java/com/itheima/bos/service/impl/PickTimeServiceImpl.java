package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.PickTimeDao;
import com.itheima.bos.domain.base.PickTime;
import com.itheima.bos.service.PickTimeService;

@Service
@Transactional // 业务层执行事务操作
public class PickTimeServiceImpl extends BaseServiceImpl<PickTime> implements PickTimeService {
	
	private PickTimeDao pickTimeDao;
	
	@Resource
	public void setPickTimeDao(PickTimeDao pickTimeDao) {
		this.pickTimeDao = pickTimeDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(pickTimeDao);
	}
	
}
