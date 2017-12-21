package com.itheima.bos.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/standard")
public class StandardAction extends BaseAction<Standard> {

	private StandardService standardService;
	
	@Resource
	public void setStandardService(StandardService standardService) {
		this.standardService = standardService;
		super.setBaseService(standardService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Standard> buildSpecification() {
		final Standard model = this.getModel();
		// 添加一个Specification条件
		Specification<Standard> spec = new Specification<Standard>() {

			@Override
			public Predicate toPredicate(Root<Standard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();

				// 获取到页面的条件
				// 名称
				if (model.getName() != null && !model.getName().trim().equals("")) {
					preList.add(cb.like(root.get("name").as(String.class), "%" + model.getName() + "%"));
				}

				// 最小重量
				if (model.getMinWeight() != null) {
					preList.add(cb.equal(root.get("minWeight").as(Long.class), model.getMinWeight()));
				}

				// 最大重量
				if (model.getMaxWeight() != null) {
					preList.add(cb.equal(root.get("maxWeight").as(Long.class), model.getMaxWeight()));
				}

				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}

}
