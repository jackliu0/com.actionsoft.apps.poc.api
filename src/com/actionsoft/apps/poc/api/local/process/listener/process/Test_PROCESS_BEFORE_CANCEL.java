package com.actionsoft.apps.poc.api.local.process.listener.process;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.InterruptListener;
import com.actionsoft.bpms.util.UtilString;

public class Test_PROCESS_BEFORE_CANCEL extends InterruptListener {

	public String getDescription() {
		return "测试用例";
	}

	public boolean execute(ProcessExecutionContext ctx) throws Exception {
		info("流程取消前事件被触发-->" + ctx.getProcessInstance());
		String str1 = (String) ctx.getVariable("str1");
		if (UtilString.isEmpty(str1) || !str1.equals("cancel yes")) {
			info("流程取消前事件-->测试str1必须为cancel yes值才可以取消");
			return false;
		} else {
			info("流程取消前事件-->str1=" + str1);
		}
		return true;
	}

}
