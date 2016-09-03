package com.actionsoft.apps.poc.api.local.process.var;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.servicetask.ServiceDelegate;
import com.actionsoft.sdk.local.SDK;

public class ModifyVars extends ServiceDelegate {

	public boolean execute(ProcessExecutionContext ctx) throws Exception {
		String user = (String) ctx.getVariable("user");
		SDK.getProcessAPI().setVariable(ctx.getProcessInstance(), "user", "hello " + user);

		double salary = (double) ctx.getVariable("salary");
		SDK.getProcessAPI().setVariable(ctx.getProcessInstance(), "salary", salary + 10000);
		return true;
	}

}
