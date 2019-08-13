package com.gpmall.schedule.monitor;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class MonitorJobListener implements JobListener {
 
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MonitorJobListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
		log.error("!!!!!!!!!!!!!!!!!!!!!!!!! TObe");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
		log.error("!!!!!!!!!!!!!!!!!!!!!!!!! ExecutionVetoed");

	}

	@Override
	public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
		log.error("!!!!!!!!!!!!!!!!!!!!!!!!! WasExecuted");

	}


}
