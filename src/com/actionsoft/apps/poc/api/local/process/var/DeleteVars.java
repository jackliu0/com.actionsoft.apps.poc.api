package com.actionsoft.apps.poc.api.local.process.var;

import java.sql.Connection;

import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.servicetask.ServiceDelegate;
import com.actionsoft.bpms.bpmn.engine.variable.ProcessVariableInstance;
import com.actionsoft.bpms.util.DBSql;

public class DeleteVars extends ServiceDelegate {

	public boolean execute(ProcessExecutionContext ctx) throws Exception {
		Connection conn = DBSql.open();
		try {
			ProcessVariableInstance.getInstance().removeVariables(conn, ctx.getProcessInstance().getId(), ctx.getProcessDef().getId());
		} finally {
			DBSql.close(conn);
		}

		return true;
	}

}
