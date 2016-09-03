package com.actionsoft.apps.poc.api.local.process.listener.process;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListener;

public class Test_PROCESS_ACTIVITY_ADHOC_BRANCH extends ValueListener {

	public String getDescription() {
		return "测试用例";
	}

	// 规则activityDefId:执行人
	public String execute(ProcessExecutionContext ctx) throws Exception {
		if (ctx.getProcessElement().getName().equals("U1")) {
			info("流程全局跳转事件被触发-->" + ctx.getProcessInstance());
			return "obj_c60c1e1780900001d21d59391c441b54:admin";
		} else {
			return null;
		}
	}

}
