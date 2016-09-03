package com.actionsoft.apps.poc.api.local.process.system;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.exception.AWSAPIException;

public class NoneProcessTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/系统短流程/无任何活动节点的流程";
	}

	public String getProcessDefId() {
		return "obj_176284a8cb8347438f365b6f4c348ae4";
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
