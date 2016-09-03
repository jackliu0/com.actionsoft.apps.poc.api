package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;

public class EventBasedGateway1ProcessTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/事件网关流程(信号)";
	}

	public String getProcessDefId() {
		return "obj_0d3d7c7342ce4e16b28d851516ad112a";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			// 创建流程实例
			ProcessInstance processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), null);
			// 启动流程
			processAPI.start(processInst, null);
			// 抛出B信号，结束A和C的分支
			info("抛出SelectB的信号将要发出");
			processAPI.start(processAPI.createShortProcessInstance("obj_c212a7daab08446383b75bdbf2ef4e2a", null, "SDK API-" + System.currentTimeMillis(), null), null);
			assertTrue(processAPI.isEndById(processInst.getId()));
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
