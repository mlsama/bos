package com.itheima.bos.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.PromotionService;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/promotion")
public class PromotionAction extends BaseAction<Promotion> {

	private PromotionService promotionService;
	
	@Resource
	public void setPromotionService(PromotionService promotionService) {
		this.promotionService = promotionService;
		super.setBaseService(promotionService);
	}

	/**
	 * 此方法用于创建条件查询所需的条件对象
	 */
	@Override
	protected Specification<Promotion> buildSpecification() {
		final Promotion model = this.getModel();
		// 添加一个Specification条件
		Specification<Promotion> spec = new Specification<Promotion>() {

			@Override
			public Predicate toPredicate(Root<Promotion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 根据页面传递的参数进行组合条件查询
				List<Predicate> preList = new ArrayList<Predicate>();

				

				Predicate[] preArray = new Predicate[preList.size()];
				return cb.and(preList.toArray(preArray));
			}

		};
		return spec;
	}
	
	//接收宣传图片文件信息
	private File titleImgFile;
	//接收宣传图片文件名称
	private String titleImgFileFileName;
	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}
	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}

	//重写save方法
	@Override
	@Action(value="save",results={
			@Result(name="success",location="/pages/take_delivery/promotion.jsp",type="redirect"),
			@Result(name="error",location="/error.jsp",type="redirect")
			}
	)
	public String save() throws Exception {
		try {
			Promotion promotion = this.getModel();
			
			//获取upload目录的绝对路径
			String uploadPath = ServletActionContext.getServletContext().getRealPath("/upload");
			
			//生成随机文件名称
			String uuid = UUID.randomUUID().toString();
			//文件后缀名
			String extName = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
			//文件名称
			String fileName = uuid+extName;
			
			//处理宣传图片
			FileUtils.copyFile(titleImgFile, new File(uploadPath+"/"+fileName));
			
			//设置图片路径
			promotion.setTitleImg("/upload/"+fileName);
			
			//设置宣传任务状态
			promotion.setStatus("1"); //1代表有效
			
			promotionService.save(promotion);
			
			//跳转页面
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

}
