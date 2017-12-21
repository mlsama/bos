package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.FixedAreaDao;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.FixedAreaService;

@Service
@Transactional // 业务层执行事务操作
public class FixedAreaServiceImpl extends BaseServiceImpl<FixedArea> implements FixedAreaService {
	
	private FixedAreaDao fixedAreaDao;
	
	@Resource
	public void setFixedAreaDao(FixedAreaDao fixedAreaDao) {
		this.fixedAreaDao = fixedAreaDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(fixedAreaDao);
	}
	
}
