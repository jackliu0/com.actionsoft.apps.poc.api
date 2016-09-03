package com.actionsoft.apps.poc.api.local.process.adhoc;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class UndoTaskProcess4Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Ad-Hoc即席处理/Undo任务收回/测试任务收回在有传阅的任务场景";
	}

	public String getProcessDefId() {
		return "obj_1d352feb091c4189980f598d304cd48b";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 2.启动流程
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			TaskInstance u1Task = tasks.get(0);
			// 3.执行U1到U2
			tasks = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks();
			TaskInstance u2Task = tasks.get(0);
			taskAPI.createUserCCTaskInstance(u2Task.getProcessInstId(), u2Task.getId(), startUser, "admin admin", "CC-Task");
			// TestCase1: 推进到U2后，产生多个传阅任务，U1仍可以收回
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
