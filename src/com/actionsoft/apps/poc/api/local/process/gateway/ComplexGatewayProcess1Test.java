package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.exception.AWSAPIException;

public class ComplexGatewayProcess1Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/复杂网关流程(自定义branchOutgoing)";
	}

	public String getProcessDefId() {
		return "obj_c44b177be0f34e9a867b65d06d686763";
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
