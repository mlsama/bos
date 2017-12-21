package com.itheima.bos.web.jobs;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Service;
/**
 * 重写了JobFactory，让Spring在创建完quazrt的Job对象，继续把对象放入Spirng的环境中管理
 * @author lenovo
 *
 */
@Service("jobFactory")
public class JobFactory extends AdaptableJobFactory {

	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		// 调用父类的方法
		Object jobInstance = super.createJobInstance(bundle);
		// 把Job对象放入Spring环境中管理
		capableBeanFactory.autowireBean(jobInstance);
		return jobInstance;
	}

}
