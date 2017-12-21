package com.itheima.bos.web.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Quartz的任务
 * @author lenovo
 *
 */
public class MyJob implements Job{

	//执行逻辑
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("任务被触发，当前时间为："+sdf.format(new Date()));
	}

}
