package com.actionsoft.apps.poc.api.local.process.listener.usertask;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.constant.TaskRuntimeConst;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class UserTaskListenerTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Listener监听器/测试人工任务事件";
	}

	public String getProcessDefId() {
		return "obj_6af1ec79e17f41bfa34b7f7ed2f61264";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);

			info("==============");
			info("测试常规的任务事件");
			info("==============");
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			TaskInstance u2Task = null;
			for (TaskInstance task : tasks) {
				List<TaskInstance> newTasks = taskAPI.completeTask(task, UserContext.fromUID(startUser), true).fetchActiveTasks();
				if (newTasks != null && !newTasks.isEmpty()) {
					if (newTasks.size() > 1) {
						fail("超出测试用处理预期");
					}
					u2Task = newTasks.get(0);
				}
			}
			assertTrue(u2Task.getActivityDefId().equals("obj_c649377f56b000012274a6807f401783"));
			info("==============");
			info("测试全局跳转事件");
			info("==============");
			TaskInstance u3Task = taskAPI.completeTask(u2Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u3Task.getActivityDefId().equals("obj_c60c1f2336400001f6603901127d1b40"));

			info("==============");
			info("测试任务挂起/恢复事件");
			info("==============");
			taskAPI.suspend(u3Task);
			assertEquals(TaskRuntimeConst.INST_STATE_SUSPEND, u3Task.getControlState());
			taskAPI.resume(u3Task);
			assertEquals(TaskRuntimeConst.INST_STATE_ACTIVE, u3Task.getControlState());

			taskAPI.completeTask(u3Task, UserContext.fromUID(startUser), true);
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
