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
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.RoleService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/role")
public class RoleAction extends BaseAction<Role> {

	private RoleService roleService;
	
	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
		super.setBaseService(roleService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Role> buildSpecification() {
		final Role model = this.getModel();
		// 添加一个Specification条件
		Specification<Role> spec = new Specification<Role>() {

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();

				

				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}
	
	//接收角色和资源id
	private Long roleId;
	private String resIds;
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public void setResIds(String resIds) {
		this.resIds = resIds;
	}
	/**
	 * 绑定资源到角色
	 * @throws Exception 
	 */
	@Action("bindResToRole")
	public String bindResToRole() throws Exception{
		try {
			roleService.bindResToRole(roleId,resIds);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return writeJson(result);
	}
	
	//接收用户id
	private Long userId;
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 查询绑定过的角色列表
	 * @throws Exception 
	 */
	@Action("listByPageAndUser")
	public String listByPageAndUser() throws Exception{
		Specification<Role> spec = buildSpecification();

		// 创建分页对象
		Pageable pageable = new PageRequest(getPage() - 1, getRows());

		// 查询数据
		Page<Role> pageBean = roleService.findAll(spec, pageable,userId);

		// 取出总记录数
		long totalCount = pageBean.getTotalElements();
		// 取出当前页的数据列表
		List<Role> list = pageBean.getContent();

		// 使用Map集合转换key-value形式
		//Map<String, Object> result = new HashMap<String, Object>();

		result.put("total", totalCount);
		result.put("rows", list);

		return writeJson(result);
	}

}
