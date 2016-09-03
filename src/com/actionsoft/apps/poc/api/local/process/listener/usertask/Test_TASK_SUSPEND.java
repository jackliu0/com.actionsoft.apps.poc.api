package com.actionsoft.apps.poc.api.local.process.listener.usertask;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ExecuteListener;

public class Test_TASK_SUSPEND extends ExecuteListener {

	public String getDescription() {
		return "测试用例";
	}

	public void execute(ProcessExecutionContext ctx) throws Exception {
		info("任务挂起后事件被触发-->" + ctx.getTaskInstance());
	}

}
