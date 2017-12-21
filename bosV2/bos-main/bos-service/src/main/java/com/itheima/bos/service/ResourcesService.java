package com.itheima.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.system.Resources;

public interface ResourcesService extends BaseService<Resources>{

	public Page<Resources> findAll(Specification<Resources> spec, Pageable pageable, Long roleId);
	
}
