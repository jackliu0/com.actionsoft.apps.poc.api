package com.actionsoft.apps.poc.api.local.process;

import com.actionsoft.bpms.bpmn.engine.listener.BizBeanImpl;
import com.actionsoft.bpms.bpmn.engine.servicetask.MappingField;
import com.actionsoft.exception.BPMNError;

public class ExceptionJavaServiceTest extends BizBeanImpl {

	public String getDescription() {
		return "如果流程变量var1(String)的值不等于1，该服务会抛出BPMNError异常，Error代码TEST001";
	}

	public String execute(@MappingField(title = "流程变量var1") String var1) throws BPMNError {
		// testcase 如果var1不等于1，抛出异常，中断执行
		if (!var1.equals("1")) {
			throw new BPMNError("TEST001");
		}
		return var1;
	}
}
