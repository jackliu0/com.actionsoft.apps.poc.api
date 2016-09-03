package com.actionsoft.apps.poc.api;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.actionsoft.bpms.schedule.IJob;

public class Test implements IJob {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("aaa");
	}

}
