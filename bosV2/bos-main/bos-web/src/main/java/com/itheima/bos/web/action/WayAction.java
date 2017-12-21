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

import com.itheima.bos.domain.base.Way;
import com.itheima.bos.service.WayService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/way")
public class WayAction extends BaseAction<Way> {

	private WayService wayService;
	
	@Resource
	public void setWayService(WayService wayService) {
		this.wayService = wayService;
		super.setBaseService(wayService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Way> buildSpecification() {
		final Way model = this.getModel();
		// 添加一个Specification条件
		Specification<Way> spec = new Specification<Way>() {

			@Override
			public Predicate toPredicate(Root<Way> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();
				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}

}
