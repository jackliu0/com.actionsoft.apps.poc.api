package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;

public class EventBasedGateway2ProcessTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/事件网关流程(时间)";
	}

	public String getProcessDefId() {
		return "obj_a58be863dcb34cc7920a5deb62a09e66";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			// 创建流程实例
			ProcessInstance processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), null);
			// 启动流程
			processAPI.start(processInst, null);
			// 等待60秒后，应该被Timer（1分钟）的事件选择，并取消了其他分支，流程向后推进结束
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
