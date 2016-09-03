package com.actionsoft.apps.poc.api.local.process.listener.servicetask;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;

public class ServiceTaskListenerTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Listener监听器/测试服务任务事件";
	}

	public String getProcessDefId() {
		return "obj_1ff5b79b69ae4f9aa1f8d71b43630a45";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			ProcessInstance processInst = processAPI.createShortProcessInstance(getProcessDefId());
			processAPI.start(processInst);
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
