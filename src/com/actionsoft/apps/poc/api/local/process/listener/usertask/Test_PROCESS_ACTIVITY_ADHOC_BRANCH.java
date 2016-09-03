package com.actionsoft.apps.poc.api.local.process.listener.usertask;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListener;

public class Test_PROCESS_ACTIVITY_ADHOC_BRANCH extends ValueListener {

	public String getDescription() {
		return "测试用例";
	}

	// 规则activityDefId:执行人
	public String execute(ProcessExecutionContext ctx) throws Exception {
		info("流程全局跳转事件被触发-->" + ctx.getProcessInstance());
		if (ctx.getProcessElement().getName().equals("U2")) {
			return "obj_c60c1f2336400001f6603901127d1b40:admin";
		} else {
			return null;// 不干涉
		}
	}

}
