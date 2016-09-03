package com.actionsoft.apps.poc.api.local.process.listener.servicetask;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.InterruptListener;
import com.actionsoft.bpms.bpmn.engine.listener.InterruptListenerInterface;

public class Test_TASK_BEFORE_COMPLETE extends InterruptListener implements InterruptListenerInterface {

	public boolean execute(ProcessExecutionContext ctx) throws Exception {
		info("任务结束前可以阻止被触发-->" + ctx.getTaskInstance());
		// throw new BPMNError("SCode01", "service biz error");
		return true;
	}

}
