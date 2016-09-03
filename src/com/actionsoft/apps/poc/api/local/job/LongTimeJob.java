package com.actionsoft.apps.poc.api.local.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.actionsoft.bpms.schedule.IJob;
import com.actionsoft.bpms.util.UtilDate;

public class LongTimeJob implements IJob {

	/**
	 * 演示互斥操作。该Job模拟运行30秒，但在调度配置上每隔10秒调度一次。运行期望结果是，该Job不会被并发执行
	 */
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		long times = System.currentTimeMillis();
		System.out.println("Long Time Job Demo (" + times + ")! Begin " + UtilDate.datetimeFormat(new Date()));
		// wait 30s
		try {
			Thread.sleep(1000 * 30);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Long Time Job Demo (" + times + ")! End " + UtilDate.datetimeFormat(new Date()));
	}
}
