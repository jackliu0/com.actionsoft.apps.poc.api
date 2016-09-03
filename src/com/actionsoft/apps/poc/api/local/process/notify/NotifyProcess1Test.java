package com.actionsoft.apps.poc.api.local.process.notify;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class NotifyProcess1Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Notify通知/NotifyProcess1(测试流程结束通知)";
	}

	public String getProcessDefId() {
		return "obj_7838b7a4b59346379285c703082a7789";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 2.启动流程
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			// 3.执行U1到U2
			tasks = taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true).fetchActiveTasks();
			// 3.执行U2,结束流程
			if (taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true).isProcessEnd()) {
				// 查询通知任务是否被正确创建

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
