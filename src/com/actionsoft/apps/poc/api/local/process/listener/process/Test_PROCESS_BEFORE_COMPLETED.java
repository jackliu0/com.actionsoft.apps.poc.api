package com.actionsoft.apps.poc.api.local.process.listener.process;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.InterruptListener;

public class Test_PROCESS_BEFORE_COMPLETED extends InterruptListener {

	public String getDescription() {
		return "测试用例";
	}

	public boolean execute(ProcessExecutionContext ctx) throws Exception {
		info("流程完成前事件被触发-->" + ctx.getProcessInstance());
		return true;
	}

}
