package com.actionsoft.apps.poc.api.local.app.entaddress;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.resource.AppContext;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.sdk.local.SDK;

public class SettingHistoryRecordsASLPTest extends LocalAPITestCase {

	public String getTestCaseName() {
		return "App开发/单位通讯录/记录查看历史";
	}

	public boolean execute(Map<String, Object> params) {
		AppContext app = SDK.getAppAPI().getAppContext("com.actionsoft.apps.entaddress");
		if (app != null) {
			String sourceAppId = "com.actionsoft.apps.poc.api";
			// 服务地址
			String aslp = "aslp://com.actionsoft.apps.entaddress/settingHistoryRecords";
			params = new HashMap<String, Object>();
			try {
				EngineDebug.info("---test start---");
				params.put("uniqueId", "5232");	// 被查看用户的唯一编号
				params.put("userId", "admin");
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
