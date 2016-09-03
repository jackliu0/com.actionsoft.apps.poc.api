package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.exception.AWSAPIException;

public class ParallelGatewayProcessTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/并行网关流程";
	}

	public String getProcessDefId() {
		return "obj_06263847e18343a7b9f09102a73eb9e6";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			// 创建流程实例
			ProcessInstance processInstModel = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), null);
			// 启动流程，直接结束
			processAPI.start(processInstModel, null);
			if (processInstModel.isEnd()) {
				return true;
			} else {
				return false;
			}
		} catch (AWSAPIException e) {
			fail(e.toString());
			return false;
		}
	}
}
