package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class ComplexGatewayProcess3Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/复杂网关流程(自定义令牌num)";
	}

	public String getProcessDefId() {
		return "obj_3c18050a65cf46ceb553b6f36ca638f4";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 2.启动流程,执行到flow1/flow2/flow3/flow4分支
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			for (TaskInstance task : tasks) {
				boolean isProcessClose = taskAPI.completeTask(task, UserContext.fromUID(startUser), true).isProcessEnd();
				if (isProcessClose)
					break;
			}
			// 应该前置被策略取消，流程结束了
			assertTrue(processAPI.isEndById(processInst.getId()));
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
