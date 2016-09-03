package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.exception.AWSAPIException;

public class ExclusiveGatewayProcessTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/排他网关流程";
	}

	public String getProcessDefId() {
		return "obj_395968b391cd4d4a954582074e8d1621";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			// 创建流程实例
			ProcessInstance processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), params);
			// 启动流程，直接结束
			if (processAPI.start(processInst, null).isProcessEnd()) {
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
