package com.itheima.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.system.Role;

public interface RoleService extends BaseService<Role>{

	public void bindResToRole(Long roleId, String resIds);

	public Page<Role> findAll(Specification<Role> spec, Pageable pageable, Long userId);
	
}
