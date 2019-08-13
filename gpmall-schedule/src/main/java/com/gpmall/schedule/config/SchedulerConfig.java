package com.gpmall.schedule.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.gpmall.schedule.monitor.MonitorJobListener;
import com.gpmall.schedule.monitor.MonitorTriggerListener;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class SchedulerConfig implements SchedulerFactoryBeanCustomizer {
	
	@Override
	public void customize(SchedulerFactoryBean schedulerFactoryBean) {
		 	schedulerFactoryBean.setStartupDelay(2);
	        schedulerFactoryBean.setAutoStartup(true);
	        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //定时监控添加
        schedulerFactoryBean.setGlobalTriggerListeners( new MonitorTriggerListener());
        schedulerFactoryBean.setGlobalJobListeners(new MonitorJobListener());
    }

	@QuartzDataSource
	@Bean
	public DataSource scheduleDataSource() {
		return new DruidDataSource();
	}

}
