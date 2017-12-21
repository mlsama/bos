package com.itheima.bos.web.jobs;

import java.util.Date;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.itheima.bos.service.PromotionService;

/**
 * 宣传任务状态自动更新定时任务
 * @author lenovo
 *
 */
public class PromotionJob  implements Job{

	//注入promotion的业务
	@Resource
	private PromotionService promotionService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("宣传任务状态自动更新定时任务触发啦...");
		//更新宣传任务的status为0，条件是：当前系统时间>endDate 且 status为1
		System.out.println(promotionService);
		promotionService.updateStatus(new Date());
	}

	
}
