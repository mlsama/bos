package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Resources;
import com.itheima.bos.service.ResourcesService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/resources")
public class ResourcesAction extends BaseAction<Resources> {

	private ResourcesService resourcesService;
	
	@Resource
	public void setResourcesService(ResourcesService resourcesService) {
		this.resourcesService = resourcesService;
		super.setBaseService(resourcesService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Resources> buildSpecification() {
		final Resources model = this.getModel();
		// 添加一个Specification条件
		Specification<Resources> spec = new Specification<Resources>() {

			@Override
			public Predicate toPredicate(Root<Resources> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();

				

				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}

	//接收角色id
	private Long roleId;
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 查询已经绑定过的资源分页列表
	 * @throws Exception 
	 */
	@Action("listByPageAndRole")
	public String listByPageAndRole() throws Exception{
		
		Specification<Resources> spec = buildSpecification();

		// 创建分页对象
		Pageable pageable = new PageRequest(getPage() - 1, getRows());

		// 查询数据
		Page<Resources> pageBean = resourcesService.findAll(spec, pageable,roleId);

		// 取出总记录数
		long totalCount = pageBean.getTotalElements();
		// 取出当前页的数据列表
		List<Resources> list = pageBean.getContent();

		// 使用Map集合转换key-value形式
		//Map<String, Object> result = new HashMap<String, Object>();

		result.put("total", totalCount);
		result.put("rows", list);

		return writeJson(result);
	}
}
