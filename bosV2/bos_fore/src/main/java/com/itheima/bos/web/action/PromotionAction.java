package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.PageBean;
import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.utils.Constant;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/promotion")
public class PromotionAction extends BaseAction<Promotion>{

	@Override
	protected Specification<Promotion> buildSpecification() {
		return null;
	}

	//接收当前页码
	//接收页面大小
	private Integer pageIndex;
	private Integer pageSize;
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 分页展示宣传任务
	 * @throws Exception 
	 */
	@Action("queryByPage")
	public String queryByPae() throws Exception{
		
		//调用BOS后端系统
		PageBean<Promotion> pageBean = WebClient
			.create(Constant.BOS_BACK_URL+"/services/promotionService/queryByPage?page="+pageIndex+"&pageSize="+pageSize+"")
			.accept(MediaType.APPLICATION_JSON)
			.get(PageBean.class);
		
		
		List<Promotion> content = pageBean.getContent();
		Long totalCount = pageBean.getTotalCount();
		
		//修改Promotion对象的titleImg
		for (Promotion promotion : content) {
			promotion.setTitleImg(Constant.BOS_BACK_URL+promotion.getTitleImg());
		}
		
		//返回当前页列表数据  和 总记录（json格式）
		result.put("list", content);
		result.put("total", totalCount);
		return writeJson(result);
	}
	
}
