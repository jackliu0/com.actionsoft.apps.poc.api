package com.actionsoft.apps.poc.api.local.process.listener.usertask;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ExecuteListener;
import com.actionsoft.bpms.bpmn.engine.listener.ExecuteListenerInterface;

public class Test_ACTIVITY_AFTER_LEAVE extends ExecuteListener implements ExecuteListenerInterface {

	public void execute(ProcessExecutionContext ctx) throws Exception {
		info("节点结束后可以补偿被触发-->" + ctx.getTaskInstance());
		// throw new BPMNError("UCode04", "User biz error");
	}

}
