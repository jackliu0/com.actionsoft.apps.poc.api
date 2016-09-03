package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.exception.AWSAPIException;

public class InclusiveGatewayProcessTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/包容网关流程";
	}

	public String getProcessDefId() {
		return "obj_a369eb75964a4adc8c212ae57a52f26b";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			Map<String, Object> processVariables = new HashMap<String, Object>();
			if (params != null && params.containsKey("str1")) {
				processVariables.put("str1", params.get("str1"));
			} else {
				processVariables.put("str1", "a");
			}
			if (params != null && params.containsKey("str2")) {
				processVariables.put("str2", params.get("str2"));
			} else {
				processVariables.put("str2", "b");
			}
			// 创建流程实例
			ProcessInstance processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), processVariables);
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
