package com.actionsoft.apps.poc.api.local.process.var;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.FileItem;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.util.UtilIO;
import com.actionsoft.bpms.util.UtilString;
import com.actionsoft.sdk.local.SDK;

public class ProcessVariableTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/流程变量/测试流程变量接口";
	}

	public String getProcessDefId() {
		return "obj_4419369e222d4dfd8c4b8b0ce7a62a2a";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			// 1，测试默认分支：读写流程变量
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("user", "周星驰");
			vars.put("age", 52);
			vars.put("salary", 99999.99);
			vars.put("resume", mockLoadResumeStream());
			vars.put("act", "default");
			info("初始化了默认分支的流程变量");
			ProcessInstance processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), vars);
			processAPI.start(processInst, null);
			info("查询流程变量值");
			Map<String, Object> values = SDK.getProcessAPI().getVariables(processInst);
			assertEquals(values.get("user").getClass(), String.class);
			assertTrue(Long.class.isAssignableFrom(values.get("age").getClass()));
			assertTrue(Double.class.isAssignableFrom(values.get("salary").getClass()));
			assertTrue(FileItem[].class.isAssignableFrom(values.get("resume").getClass()));
			assertEquals(values.get("age"), 52l);
			assertEquals(values.get("user"), "周星驰");
			assertEquals(values.get("salary"), 99999.99d);

			// 附件操作
			// a.读取流程变量附件内容
			FileItem[] resumes = (FileItem[]) values.get("resume");
			assertEquals(UtilIO.toString(resumes[0].getInputStream()), UtilIO.toString(mockLoadResumeStream()[0].getInputStream()));
			// b.删除附件resume2.doc,FileItem的getInputStream()为空时会删除对应名称的附件
			FileItem delete = new FileItem();
			delete.setName("resume2.doc");
			// delete.setInputStream(null);默认是null
			SDK.getProcessAPI().setVariable(processInst, "resume", new FileItem[] { delete });
			assertTrue(((FileItem[]) SDK.getProcessAPI().getVariable(processInst, "resume")).length == 1);
			// c.删除流程变量所有附件
			SDK.getProcessAPI().setVariable(processInst, "resume", null);
			assertTrue(((FileItem[]) SDK.getProcessAPI().getVariable(processInst, "resume")) == null);
			// d.设置附件变量
			SDK.getProcessAPI().setVariable(processInst, "resume", mockLoadResumeStream());

			// 2，测试修改分支：服务任务修改流程变量值
			info("初始化了修改分支的流程变量");
			vars.put("act", "modify");
			processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), vars);
			// 启动流程，直接结束
			processAPI.start(processInst, null);
			values = SDK.getProcessAPI().getVariables(processInst);
			assertEquals(values.get("user"), "hello " + vars.get("user"));
			assertEquals(values.get("salary"), ((double) vars.get("salary")) + 10000);

			// 3，测试删除分支：服务任务删除流程变量值
			info("初始化了删除分支的流程变量");
			// 走删除的服务任务分支
			vars.put("act", "delete");
			processInst = processAPI.createShortProcessInstance(getProcessDefId(), null, "SDK API-" + System.currentTimeMillis(), vars);
			// 启动流程，直接结束
			processAPI.start(processInst, null);
			Map<String, Object> varsEnd = SDK.getProcessAPI().getVariables(processInst, null);
			assertTrue((long) varsEnd.get("age") == 0L);
			assertTrue(UtilString.isEmpty(varsEnd.get("user")));
			assertTrue((double) varsEnd.get("salary") == 0.0D);
			assertNull(varsEnd.get("resume"));
			assertNull(SDK.getProcessAPI().getVariable(processInst, "resume"));
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			fail(e.toString());
			return false;
		}
	}

	private FileItem[] mockLoadResumeStream() {
		String s = "这是个人的履历信息，可以读取外部文件。。";
		ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
		FileItem fi = new FileItem();
		fi.setInputStream(is);
		fi.setName("resume1.doc");

		ByteArrayInputStream is2 = new ByteArrayInputStream(s.getBytes());
		FileItem fi2 = new FileItem();
		fi2.setInputStream(is2);
		fi2.setName("resume2.doc");
		return new FileItem[] { fi, fi2 };
	}

}
