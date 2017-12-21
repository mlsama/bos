package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StandardDao;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;

@Service
@Transactional // 业务层执行事务操作
public class StandardServiceImpl extends BaseServiceImpl<Standard> implements StandardService {
	
	private StandardDao standardDao;
	
	@Resource
	public void setStandardDao(StandardDao standardDao) {
		this.standardDao = standardDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(standardDao);
	}

	@Override
	public Standard findByName(String standardName) {
		
		return standardDao.findByName(standardName);
	}
	
}
