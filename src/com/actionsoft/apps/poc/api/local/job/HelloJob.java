package com.actionsoft.apps.poc.api.local.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.actionsoft.bpms.schedule.IJob;
import com.actionsoft.sdk.local.SDK;

public class HelloJob implements IJob {

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// 读管理员配置的扩展参数串，支持简单的@公式
		String param = SDK.getJobAPI().getJobParameter(jobExecutionContext);
		System.out.println("Hello AWS PaaS Job Demo! Param = " + param);
	}
}
