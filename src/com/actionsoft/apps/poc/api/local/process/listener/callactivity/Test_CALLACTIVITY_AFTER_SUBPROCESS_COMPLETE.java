package com.actionsoft.apps.poc.api.local.process.listener.callactivity;

import com.actionsoft.bpms.bpmn.constant.CallActivityDefinitionConst;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.core.delegate.TaskBehaviorContext;
import com.actionsoft.bpms.bpmn.engine.listener.ExecuteListener;

public class Test_CALLACTIVITY_AFTER_SUBPROCESS_COMPLETE extends ExecuteListener {

	@Override
	public void execute(ProcessExecutionContext ctx) throws Exception {
		TaskBehaviorContext callActivityCtx = (TaskBehaviorContext) ctx.getParameter(CallActivityDefinitionConst.PARAM_CALLACTIVITY_CONTEXT);
		info(callActivityCtx.getTaskInstance().getId());
		info(ctx.getTaskInstance().getId());
	}

}
