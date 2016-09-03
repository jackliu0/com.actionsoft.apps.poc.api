package com.actionsoft.apps.poc.api.local.process.listener.process;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.InterruptListener;
import com.actionsoft.bpms.util.UtilString;

public class Test_PROCESS_BEFORE_DELETED extends InterruptListener {

	public String getDescription() {
		return "测试用例";
	}

	public boolean execute(ProcessExecutionContext ctx) throws Exception {
		info("流程删除前事件被触发-->" + ctx.getProcessInstance());
		String str1 = (String) ctx.getVariable("str1");
		if (UtilString.isEmpty(str1) || !str1.equals("remove yes")) {
			info("流程删除前事件-->测试str1必须为remove yes值才可以删除");
			return false;
		} else {
			info("流程删除前事件-->str1=" + str1);
		}
		return true;
	}

}
