package com.actionsoft.apps.poc.api.local.process.listener.servicetask;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.InterruptListener;
import com.actionsoft.bpms.bpmn.engine.listener.InterruptListenerInterface;

public class Test_ACTIVITY_BEFORE_LEAVE extends InterruptListener implements InterruptListenerInterface {

	public boolean execute(ProcessExecutionContext ctx) throws Exception {
		info("节点离开前可以阻止被触发-->" + ctx.getTaskInstance());
		// throw new BPMNError("SCode03", "service biz error");
		return true;
	}

}
