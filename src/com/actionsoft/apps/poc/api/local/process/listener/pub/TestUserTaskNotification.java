package com.actionsoft.apps.poc.api.local.process.listener.pub;

import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ProcessPubicListener;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;

/**
 * 监控新任务到达
 */
public class TestUserTaskNotification extends ProcessPubicListener {

	@Override
	public void call(String eventName, TaskInstance taskInst, ProcessExecutionContext ctx) {
		EngineDebug.info("poc.api监听到[" + eventName + "]" + taskInst);
	}

}
