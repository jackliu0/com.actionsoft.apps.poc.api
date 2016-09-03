package com.actionsoft.apps.poc.api.local.app.property;

import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.sdk.local.SDK;

public class AppPropertyAPITest extends LocalAPITestCase {

	public String getTestCaseName() {
		return "App开发/配置/应用自定义参数";
	}

	public boolean execute(Map<String, Object> params) {
		// 读变量值
		EngineDebug.info("---set before---");
		EngineDebug.info("p1=" + SDK.getAppAPI().getProperty("com.actionsoft.apps.poc.api", "p1"));
		EngineDebug.info("p2=" + SDK.getAppAPI().getProperty("com.actionsoft.apps.poc.api", "p2"));
		try {
			// 写变量值
			SDK.getAppAPI().setProperty("com.actionsoft.apps.poc.api", "p1", Long.toString(System.currentTimeMillis()));
			SDK.getAppAPI().setProperty("com.actionsoft.apps.poc.api", "p2", Long.toString(System.currentTimeMillis()));
		} catch (Exception e) {
			EngineDebug.err(e.toString());
			fail(e.toString());
			return false;
		}
		return true;
	}
}
