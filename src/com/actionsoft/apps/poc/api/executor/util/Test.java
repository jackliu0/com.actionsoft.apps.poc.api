package com.actionsoft.apps.poc.api.executor.util;

import java.util.List;

import com.actionsoft.apps.poc.api.local.process.system.GatewayProcessTest;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.sdk.local.SDK;

/**
 * 测试可删除
 * 
 * @author jack
 * 
 */
public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SDK.getPlatformAPI().getAWSEngine().startup(null);
		// 监控当前线程产生的日志
		EngineDebug.openMonitor();
		new GatewayProcessTest().execute(null);

		System.out.println("==========monitor test==============");
		// 获得监控的历史和最新产生的日志
		List<String> monitorLogs = EngineDebug.getMonitorLogs();
		if (monitorLogs != null && !monitorLogs.isEmpty()) {
			for (String msg : monitorLogs) {
				System.out.println(msg);
			}
		}
		// 释放当前线程产生的日志
		EngineDebug.closeMonitor();
	}

}
