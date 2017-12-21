package com.itheima.bos.service;

import com.itheima.bos.domain.base.Standard;

public interface StandardService extends BaseService<Standard>{

	public Standard findByName(String standardName);
	
}
