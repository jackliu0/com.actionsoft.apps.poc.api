package com.actionsoft.apps.poc.api.local.process.adhoc;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class UndoTaskProcess2Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Ad-Hoc即席处理/Undo任务收回/测试任务收回在多分支场景";
	}

	public String getProcessDefId() {
		return "obj_5e076e98d3db435594a5051c0b3dc248";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 2.启动流程
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			TaskInstance u1Task = tasks.get(0);
			// 3.执行U1到U2、U3、U4
			tasks = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks();
			// TestCase1:U1任务办理给U2/U3/U4后，U1任务不允许收回
			TaskInstance u4TaskInst = null;
			if (!taskAPI.isUndoTask(u1Task.getId())) {
				// 执行完U2/U3/U4，完成汇聚，执行到U5
				for (TaskInstance taskSub : tasks) {
					if (taskSub.getActivityDefId().equals("obj_c6077fc00ca0000148272503da201b90")) {
						u4TaskInst = taskSub;
					}
					taskAPI.completeTask(taskSub, UserContext.fromUID(startUser), true);
				}
				// TestCase2: U2/U3/U4办理完通过汇聚，产生U5任务后，U4允许收回(汇聚前最后一个任务)
				if (taskAPI.isUndoTask(u4TaskInst.getId())) {
					u4TaskInst = taskAPI.undoTask(u4TaskInst.getId(), startUser, "测试收回1");
					// 再汇聚，执行到U5
					taskAPI.completeTask(u4TaskInst, UserContext.fromUID(startUser), true);

				} else {
					return true;
				}
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
