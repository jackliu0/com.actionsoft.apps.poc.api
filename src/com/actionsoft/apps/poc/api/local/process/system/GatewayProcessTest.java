package com.actionsoft.apps.poc.api.local.process.system;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.exception.AWSAPIException;

public class GatewayProcessTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/系统短流程/只有网关的流程";
	}

	public String getProcessDefId() {
		return "obj_a75cf81770464ccba920909f7102dec3";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			// 创建流程实例
			ProcessInstance processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), null);
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
