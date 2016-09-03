package com.actionsoft.apps.poc.api.local.process.listener.pub;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ProcessPubicListener;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;

public class PublicListenerTest extends LocalProcessAPITestCase {
	public static final String NOTIFY_SYSTEM = "API测试";

	public String getTestCaseName() {
		return "Process API/Listener监听器/测试全局监听器";
	}

	public String getProcessDefId() {
		return "obj_bb905654bd624af2bcedb6be8697650c";
	}

	public boolean execute(Map<String, Object> params) {
		info("TestUserTaskNotification.java");
		return true;
	}

}

class MyTaskNotification extends ProcessPubicListener {
	public static final String NOTIFY_SYSTEM = "POC.API监听测试";

	@Override
	public void call(String eventName, TaskInstance taskInst, ProcessExecutionContext ctx) {
		System.out.println("poc.api监听到[" + eventName + "]" + taskInst);
	}

}