package com.actionsoft.apps.poc.api.local.process.listener.usertask;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListener;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListenerInterface;

public class Test_ACTIVITY_ADHOC_BRANCH extends ValueListener implements ValueListenerInterface {

	public String execute(ProcessExecutionContext ctx) throws Exception {
		info("程序指定后继路线和参与者-->" + ctx.getTaskInstance());
		if (ctx.getProcessElement().getName().equals("U1")) {
			return "obj_c649377f56b000012274a6807f401783:admin";
		} else {
			return null;
		}
	}

}
