package com.itheima.test;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.itheima.bos.web.jobs.MyJob;

/**
 * 演示日历触发器(常用)
 * @author lenovo
 *
 */
public class CronTriggerDemo {

	public static void main(String[] args) throws Exception {
		
		//1.创建任务对象
		JobDetail job = JobBuilder.newJob(MyJob.class).build();
		
		//2.创建触发器对象
		//startNow():立即启动
		Trigger trigger = TriggerBuilder
						.newTrigger()
						.startNow()
						.withSchedule(CronScheduleBuilder.cronSchedule("0,5 * * 4#2 * ? *"))
						.build();
		
		//3.创建任务调度对象（关联任务和触发器）
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		
		//关联任务和触发器
		scheduler.scheduleJob(job, trigger);
		
		//启动任务调度
		scheduler.start();
		
		
	}
}
