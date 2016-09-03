package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class ComplexGatewayProcess2Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Gateway网关/复杂网关流程(自定义mergeIncoming)";
	}

	public String getProcessDefId() {
		return "obj_3afcccdb1633498783559355599d28c7";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 2.启动流程,执行到flow1/flow2/flow3分支
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			TaskInstance u1Task = tasks.get(0);
			TaskInstance u3Task = tasks.get(2);

			// 3.执行U1到U11
			tasks = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks();
			// 4.执行U11，汇聚等待
			taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true);
			// 5.执行U3到U31
			tasks = taskAPI.completeTask(u3Task, UserContext.fromUID(startUser), true).fetchActiveTasks();
			// 6.执行U11，汇聚通过，结束流程
			taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true);
			// 应该前置被策略取消，流程结束了
			assertTrue(processAPI.isEndById(processInst.getId()));
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
