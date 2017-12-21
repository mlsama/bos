package com.itheima.bos.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Bus;
import com.itheima.bos.service.BusService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/bus")
public class BusAction extends BaseAction<Bus> {

	private BusService busService;
	
	@Resource
	public void setBusService(BusService busService) {
		this.busService = busService;
		super.setBaseService(busService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Bus> buildSpecification() {
		final Bus model = this.getModel();
		// 添加一个Specification条件
		Specification<Bus> spec = new Specification<Bus>() {

			@Override
			public Predicate toPredicate(Root<Bus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();
				//根据线路类型查询
				if(model.getWay() !=null && StringUtils.isNoneBlank(model.getWay().getWayType()) ){
					preList.add(cb.like(root.get("way").get("wayType").as(String.class), "%"+model.getWay().getWayType()+"%"));
				}
				//根据车牌号查询
				if(StringUtils.isNoneBlank(model.getBusNum())){
					preList.add(cb.like(root.get("busNum").as(String.class), "%"+model.getBusNum()+"%"));
				}
				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}

}
