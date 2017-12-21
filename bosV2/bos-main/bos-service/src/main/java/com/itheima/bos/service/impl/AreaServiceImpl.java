package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.AreaDao;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaService;

@Service
@Transactional // 业务层执行事务操作
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService {
	
	private AreaDao areaDao;
	
	@Resource
	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(areaDao);
	}
	
}
