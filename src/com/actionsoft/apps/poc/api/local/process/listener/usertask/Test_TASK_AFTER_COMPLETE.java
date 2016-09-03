package com.actionsoft.apps.poc.api.local.process.listener.usertask;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ExecuteListener;
import com.actionsoft.bpms.bpmn.engine.listener.ExecuteListenerInterface;

public class Test_TASK_AFTER_COMPLETE extends ExecuteListener implements ExecuteListenerInterface {

	public void execute(ProcessExecutionContext ctx) throws Exception {
		info("任务结束后可以补偿被触发-->" + ctx.getTaskInstance());
		// throw new BPMNError("UCode02", "User biz error");
	}

}
