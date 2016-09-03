package com.actionsoft.apps.poc.api.local.app.notification;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.resource.AppContext;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.sdk.local.SDK;

public class AppNotificationAPITest1 extends LocalAPITestCase {
	public static String systemName = "API测试-简单消息";

	public String getTestCaseName() {
		return "App开发/通知/简单通知";
	}

	public boolean execute(Map<String, Object> params) {
		AppContext notificationApp = SDK.getAppAPI().getAppContext("com.actionsoft.apps.notification");
		if (notificationApp != null) {
			String sourceAppId = "com.actionsoft.apps.poc.api";
			// 服务地址
			String aslp = "aslp://com.actionsoft.apps.notification/sendMessage";
			params = new HashMap<String, Object>();
			try {
				EngineDebug.info("---send info message---");
				params.put("sender", "admin");
				params.put("targetIds", "admin");
				params.put("content", "Hello Notification, This is Info!");
				params.put("level", "info");
				params.put("systemName", systemName);
				EngineDebug.info(appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params).toString());

				params = new HashMap<String, Object>();
				EngineDebug.info("---send warning message---");
				params.put("sender", "admin");
				params.put("targetIds", "admin admin");
				params.put("content", "Hello Notification(2), This is Warning!");
				params.put("level", "warning");
				params.put("systemName", systemName);
				EngineDebug.info(appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params).toString());

				params = new HashMap<String, Object>();
				EngineDebug.info("---send error message---");
				params.put("sender", "admin");
				params.put("targetIds", "admin");
				params.put("content", "Hello Notification, This is Error!");
				params.put("level", "error");
				params.put("systemName", systemName);
				EngineDebug.info(appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params).toString());

				EngineDebug.info("---test finashed---");
			} catch (Exception e) {
				fail(e.toString());
				return false;
			}
		}
		return true;
	}
}
