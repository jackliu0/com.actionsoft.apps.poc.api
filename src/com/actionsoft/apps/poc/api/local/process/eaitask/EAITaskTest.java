package com.actionsoft.apps.poc.api.local.process.eaitask;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.EAITaskInstance;

public class EAITaskTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/EAITask外部任务/测试外部任务";
	}

	public String getProcessDefId() {
		return "";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			// test case1
			EAITaskInstance eaiTask = taskAPI.createEAITaskInstance("百度", "0001", "admin", "admin", "这是百度的首页", "http://www.baidu.com?uid=@uid", 1);
			assertNotNull(eaiTask);
			assertTrue(taskAPI.query().activeTask().target("admin").orderByBeginTime().desc().list().get(0).isEAITask());
			taskAPI.completeEAITask("百度", "0001");

			// test case2
			eaiTask = taskAPI.createEAITaskInstance("百度", "0002", "admin", "admin", "这是百度的首页", "http://www.baidu.com?uid=@uid", 1);
			assertNotNull(eaiTask);
			assertNull(eaiTask.getReadTime());
			// 标记已读
			taskAPI.tagReadTime(eaiTask.getId());
			eaiTask = taskAPI.query().detailById(eaiTask.getId()).toEAITask();
			assertNotNull(eaiTask);
			assertTrue(eaiTask.getApplicationName().equals("百度"));
			assertTrue(eaiTask.getOuterId().equals("0002"));
			assertNotNull(eaiTask.getReadTime());

			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
