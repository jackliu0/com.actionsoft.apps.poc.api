package com.actionsoft.apps.poc.api.local.process.listener.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.constant.ProcessRuntimeConst;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.exception.BPMNError;

public class ProcessListenerTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Listener监听器/测试流程事件";
	}

	public String getProcessDefId() {
		return "obj_518c7924281648a9a417592228ed36be";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			info("==============");
			info("测试启动流程事件");
			info("==============");
			Map<String, Object> vars = new HashMap<String, Object>();
			ProcessInstance processInst = null;
			try {
				// 模拟抛出BPMNError变量值=begin
				vars.put("str1", "begin");
				processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), vars);
			} catch (Exception e) {
				if (e instanceof BPMNError) {
					vars.put("str1", "ok");
					processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), vars);
				} else {
					fail(e.toString());
				}
			}
			info("==============");
			info("测试挂起/恢复流程事件");
			info("==============");
			processAPI.suspend(processInst);
			processAPI.resume(processInst);
			// 启动流程至U1节点
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();

			info("==============");
			info("测试全局跳转事件");
			info("==============");
			// 执行U1，由PROCESS_ACTIVITY_ADHOC_BRANCH流程事件动态跳转到U3，并指定由admin执行
			TaskInstance u3Task = taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u3Task.getActivityDefId().equals("obj_c60c1e1780900001d21d59391c441b54"));

			info("==============");
			info("测试流程结束事件");
			info("==============");
			assertTrue(taskAPI.completeTask(u3Task, UserContext.fromUID(startUser), true).isProcessEnd());

			info("==============");
			info("测试取消流程事件");
			info("==============");
			vars.put("str1", "ok");
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), vars);
			// cancel event test
			try {
				processAPI.cancel(processInst, UserContext.fromUID(startUser));
			} catch (Exception e) {
				processAPI.setVariable(processInst, "str1", "cancel yes");
				// cancel!
				processAPI.cancel(processInst, UserContext.fromUID(startUser));
			}
			assertTrue(processInst.getControlState().equals(ProcessRuntimeConst.INST_STATE_CANCEL));

			info("==============");
			info("测试删除流程事件");
			info("==============");
			vars.put("str1", "ok");
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), vars);
			// deleted event test
			try {
				processAPI.delete(processInst, UserContext.fromUID(startUser));
			} catch (Exception e) {
				processAPI.setVariable(processInst, "str1", "remove yes");
				// cancel!
				processAPI.delete(processInst, UserContext.fromUID(startUser));
			}
			assertNull(processAPI.query().detailById(processInst.getId()));

			info("==============");
			info("测试终止流程事件");
			info("==============");
			vars.put("str1", "ok");
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), vars);
			// terminate event test
			processAPI.terminate(processInst, UserContext.fromUID(startUser));
			assertTrue(processInst.getControlState().equals(ProcessRuntimeConst.INST_STATE_TERMINATE));

			return true;
		} catch (Exception e) {
			if (e instanceof BPMNError) {
				fail("BPMNError:" + e.toString());
			} else {
				fail(e.toString());
			}
			return false;
		}
	}
}
