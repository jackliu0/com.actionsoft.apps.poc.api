package com.actionsoft.apps.poc.api.local.process.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.util.UUIDGener;

public class QueryProcessTest extends LocalProcessAPITestCase {
	public String getTestCaseName() {
		return "Process API/Query查询/测试流程查询功能";
	}

	public String getProcessDefId() {
		return "obj_325ec74acf684f0eab83213a3cf0bb61";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			Date beginDate = new Date();
			List<String> ids = new ArrayList<String>();
			List<String> keys = new ArrayList<String>();
			// 创建8个人工和系统混合的流程实例
			String key = UUIDGener.getUUID();
			for (int i = 0; i < 8; i++) {
				ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), key + "_" + i, startUser, "", "", "OrderProcess-" + i, null);
				assertNotNull(processAPI.query().detailById(processInst.getId()));
				assertNotNull(processAPI.query().detailByBusinessKey(key + "_" + i));
				// 启动流程,执行到U1
				List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
				assertTrue(tasks.size() == 1);
				// 执行U1到S1，结束流程
				taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true);
				// id&key
				ids.add(processInst.getId());
				keys.add(processInst.getProcessBusinessKey());
			}
			Date endDate = new Date();

			// assert!
			assertTrue(processAPI.query().ids(ids).list().size() == 8);
			assertTrue(processAPI.query().keys(keys).list().size() == 8);

			// create new instance
			key = UUIDGener.getUUID();
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), key, startUser, "", "", key, null);

			assertTrue(processAPI.query().title(key).activeProcess().count() == 1);
			// cancel instance
			processAPI.cancel(processInst, UserContext.fromUID(startUser));
			assertTrue(processAPI.query().title(key).canceledProcess().count() == 1);

			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
