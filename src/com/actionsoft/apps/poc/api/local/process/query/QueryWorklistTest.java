package com.actionsoft.apps.poc.api.local.process.query;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.constant.UserTaskDefinitionConst;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class QueryWorklistTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Query查询/产生各类状态的任务，测试Worklist";
	}

	public String getProcessDefId() {
		return "obj_49700cea6bf348209f470203a74727d1";
	}

	public boolean execute(Map<String, Object> params) {
		String user = "admin";
		try {
			UserContext startUser = UserContext.fromUID(user);
			// ------------跑完一笔流程，产生已办-----------------------
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, user, "", "", "采购A设备", null);
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			tasks = taskAPI.completeTask(tasks.get(0), startUser, true).fetchActiveTasks();
			assertTrue(taskAPI.completeTask(tasks.get(0), startUser, true).isProcessEnd());

			// ------------创建一个未读申请待办+一个待办传阅+一个已办传阅-----------------------
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, user, "", "", "采购B设备", null);
			tasks = processAPI.start(processInst, null).fetchActiveTasks();
			tasks = taskAPI.createUserCCTaskInstance(processInst, tasks.get(0), startUser, "admin admin", "采购B设备");
			taskAPI.completeTask(tasks.get(0), startUser, true);

			// ------------创建一个未读申请待办+一个加签等待待办+一个加签待办+一个加签已办-----------------------
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, user, "", "", "采购C设备", null);
			tasks = processAPI.start(processInst, null).fetchActiveTasks();
			// 给admin加签
			tasks = taskAPI.createUserAdHocTaskInstance(processInst, tasks.get(0), startUser, "admin", UserTaskDefinitionConst.ADHOC_TASK_TYPE_1, "采购C设备");
			// admin又给admin加签
			tasks = taskAPI.createUserAdHocTaskInstance(processInst, tasks.get(0), startUser, "admin", UserTaskDefinitionConst.ADHOC_TASK_TYPE_1, "采购C设备");
			// 最后一个加签结束
			taskAPI.completeTask(tasks.get(0), startUser, true);

			// ------------创建一个未读申请待办+一个待办传阅+挂起流程-----------------------
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, user, "", "", "采购D设备", null);
			tasks = processAPI.start(processInst, null).fetchActiveTasks();
			processAPI.suspend(processInst);

			// ------------创建一个未读的待认领待办（池）-----------------------
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, user, "", "", "采购E设备", null);
			tasks = processAPI.start(processInst, null).fetchActiveTasks();
			taskAPI.completeTask(tasks.get(0), startUser, false);
			taskAPI.createUserClaimTaskInstance(processInst, tasks.get(0), startUser, "obj_c62b8ba76a900001b219c460c0101cb3", 1, "5148", "采购E设备");
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
