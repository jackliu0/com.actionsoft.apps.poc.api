package com.actionsoft.apps.poc.api.local.process.adhoc;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class UndoTaskProcess1Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Ad-Hoc即席处理/Undo任务收回/测试任务收回基本功能";
	}

	public String getProcessDefId() {
		return "obj_93ce12b45c89406a9c6fd89c69924502";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			String title = "API-" + System.currentTimeMillis();
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", title, null);
			assertTrue(processAPI.query().title(title).count() == 1);
			// 2.启动流程
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().count() == 1);
			// union query test
			assertTrue(taskAPI.queryHistory().processInstId(processInst.getId()).supportUnion().count() == 1);
			assertTrue(taskAPI.queryHistory().processInstId(processInst.getId()).supportUnion().list().size() == 1);
			assertTrue(taskAPI.queryHistory().processInstId(processInst.getId()).supportUnion().listPage(0, 100).size() == 1);
			// 3.执行U1到U2
			taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true);
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().count() == 1);
			assertTrue(taskAPI.queryHistory().processInstId(processInst.getId()).count() == 1);
			// union query test
			assertTrue(taskAPI.queryHistory().processInstId(processInst.getId()).supportUnion().count() == 2);
			assertTrue(taskAPI.queryHistory().processInstId(processInst.getId()).supportUnion().list().size() == 2);
			assertTrue(taskAPI.queryHistory().processInstId(processInst.getId()).supportUnion().listPage(0, 100).size() == 2);

			// TestCase1:U1任务办理给U2后，再收回U2任务
			if (taskAPI.isUndoTask(tasks.get(0).getId())) {
				// 收回到U1
				TaskInstance taskInst = taskAPI.undoTask(tasks.get(0).getId(), startUser, "测试收回");
				// 再执行U1到U2
				tasks = taskAPI.completeTask(taskInst, UserContext.fromUID(startUser), true).fetchActiveTasks();
				TaskInstance taskInst2 = tasks.get(0);
				// 执行U2到S1再到U3
				tasks = taskAPI.completeTask(taskInst2, UserContext.fromUID(startUser), true).fetchActiveTasks();

				// TestCase2: U2任务办理给U3后，U3任务不允许收回
				if (taskAPI.isUndoTask(taskInst2.getId())) {
					return false;
				}
				taskAPI.completeTask(tasks.get(1), UserContext.fromUID(startUser), true);
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
