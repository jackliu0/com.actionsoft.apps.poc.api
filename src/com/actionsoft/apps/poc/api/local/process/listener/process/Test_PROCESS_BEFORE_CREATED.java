package com.actionsoft.apps.poc.api.local.process.listener.process;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.InterruptListener;
import com.actionsoft.exception.BPMNError;

public class Test_PROCESS_BEFORE_CREATED extends InterruptListener {

	public String getDescription() {
		return "测试用例";
	}

	public boolean execute(ProcessExecutionContext ctx) throws Exception {
		info("流程创建前事件被触发");
		String str1 = ctx.getParameterOfString("str1");// 从临时变量中获得，在创建前尚未保存流程变量值
		if (str1 == null || str1.equals("begin")) {
			// 模拟抛出业务异常
			throw new BPMNError("BIZ001", "PROCESS_BEFORE_CREATED事件模拟抛出业务异常");
		} else {
			return true;
		}
	}
}
