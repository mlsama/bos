package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.SubAreaDao;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.SubAreaService;

@Service
@Transactional // 业务层执行事务操作
public class SubAreaServiceImpl extends BaseServiceImpl<SubArea> implements SubAreaService {
	
	private SubAreaDao subAreaDao;
	
	@Resource
	public void setSubAreaDao(SubAreaDao subAreaDao) {
		this.subAreaDao = subAreaDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(subAreaDao);
	}
	
}
