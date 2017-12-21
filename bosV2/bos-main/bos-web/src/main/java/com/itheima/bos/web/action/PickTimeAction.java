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

import com.itheima.bos.domain.base.PickTime;
import com.itheima.bos.service.PickTimeService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/pickTime")
public class PickTimeAction extends BaseAction<PickTime> {

	private PickTimeService pickTimeService;
	
	@Resource
	public void setPickTimeService(PickTimeService pickTimeService) {
		this.pickTimeService = pickTimeService;
		super.setBaseService(pickTimeService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<PickTime> buildSpecification() {
		final PickTime model = this.getModel();
		// 添加一个Specification条件
		Specification<PickTime> spec = new Specification<PickTime>() {

			@Override
			public Predicate toPredicate(Root<PickTime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();
				// 名称
				if (model.getName() != null && !model.getName().trim().equals("")) {
					preList.add(cb.like(root.get("name").as(String.class), "%" + model.getName() + "%"));
				}

				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}

}
