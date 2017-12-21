package com.itheima.bos.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.PromotionDao;
import com.itheima.bos.domain.take_delivery.PageBean;
import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.PromotionService;

@Service
@Transactional // 业务层执行事务操作
public class PromotionServiceImpl extends BaseServiceImpl<Promotion> implements PromotionService {
	
	private PromotionDao promotionDao;
	
	@Resource
	public void setPromotionDao(PromotionDao promotionDao) {
		this.promotionDao = promotionDao;
		//给BaseServiceImpl的baseDao赋值
		super.setBaseDao(promotionDao);
	}

	@Override
	public PageBean<Promotion> queryByPage(Integer page, Integer pageSize) {
		//1.创建Pageable
		Pageable pageable = new PageRequest(page,pageSize);
		
		//只查询status为1的宣传任务
		Specification<Promotion> spec = new Specification<Promotion>() {

			@Override
			public Predicate toPredicate(Root<Promotion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pre = cb.equal(root.get("status").as(String.class), "1");
				return pre;
			}
			
		};
		
		Page<Promotion> pageData = promotionDao.findAll(spec, pageable);
		
		//创建PageBean
		PageBean<Promotion> pageBean = new PageBean<Promotion> ();
		pageBean.setContent(pageData.getContent());
		pageBean.setTotalCount(pageData.getTotalElements());
		
		return pageBean;
	}

	@Override
	public void updateStatus(Date date) {
		promotionDao.updateStatus(date);
	}
	
}
