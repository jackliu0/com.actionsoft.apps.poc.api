package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.exception.AWSAPIException;

public class EventBasedGateway3ProcessTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/事件网关流程(消息)";
	}

	public String getProcessDefId() {
		return "obj_332b2644d6da4bf9be76958d6cb1730c";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			// 创建流程实例
			ProcessInstance processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), null);
			// 启动流程
			processAPI.start(processInst, null);
			return true;
		} catch (AWSAPIException e) {
			fail(e.toString());
			return false;
		}
	}
}
