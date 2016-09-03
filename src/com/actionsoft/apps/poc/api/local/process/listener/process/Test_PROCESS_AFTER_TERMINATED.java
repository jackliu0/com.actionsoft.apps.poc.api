package com.actionsoft.apps.poc.api.local.process.listener.process;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ExecuteListener;

public class Test_PROCESS_AFTER_TERMINATED extends ExecuteListener {

	public String getDescription() {
		return "测试用例";
	}

	public void execute(ProcessExecutionContext ctx) throws Exception {
		info("流程终止后事件被触发-->" + ctx.getProcessInstance());
	}

}
