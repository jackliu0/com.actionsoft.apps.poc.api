package com.actionsoft.apps.poc.api.local.process.listener.usertask;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListener;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListenerInterface;

public class Test_ACTIVITY_CONFIRM_PARTICIPANTS extends ValueListener implements ValueListenerInterface {

	public String execute(ProcessExecutionContext ctx) throws Exception {
		info("节点就绪参与者即将产生任务被触发");
		String participants = ctx.getParameterOfString("$PARTICIPANTS");
		info("即将给[" + participants + "]创建人工任务");
		return "admin admin admin";// 不干预当前的委派过程
	}
}
