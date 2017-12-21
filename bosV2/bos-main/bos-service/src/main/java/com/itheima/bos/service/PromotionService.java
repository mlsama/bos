package com.itheima.bos.service;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itheima.bos.domain.take_delivery.PageBean;
import com.itheima.bos.domain.take_delivery.Promotion;
public interface PromotionService extends BaseService<Promotion>{
	
	
	/**
	 * 分页查询宣传任务
	 */
	@GET
	@Path("/queryByPage")
	@Produces(MediaType.APPLICATION_JSON)
	public PageBean<Promotion> queryByPage(@QueryParam("page")Integer page,@QueryParam("pageSize")Integer pageSize);

	/**
	 * //更新宣传任务的status为0，条件是：当前系统时间>endDate 且 status为1
	 * @param date
	 */
	public void updateStatus(Date date);
	
}
