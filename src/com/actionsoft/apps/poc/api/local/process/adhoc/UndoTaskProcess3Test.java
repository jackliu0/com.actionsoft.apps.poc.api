package com.actionsoft.apps.poc.api.local.process.adhoc;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class UndoTaskProcess3Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Ad-Hoc即席处理/Undo任务收回/测试任务收回在Manual任务场景";
	}

	public String getProcessDefId() {
		return "obj_f62e53c7e50a4f57b9ce8c3e72f8231d";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 2.启动流程
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			TaskInstance u1Task = tasks.get(0);
			// 3.执行U1到M1、U2
			tasks = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks();
			// TestCase1: M1是特殊的空任务，当执行完到U2后，U1允许收回
			if (taskAPI.isUndoTask(u1Task.getId())) {
				u1Task = taskAPI.undoTask(u1Task.getId(), startUser, "收回测试");
				taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
