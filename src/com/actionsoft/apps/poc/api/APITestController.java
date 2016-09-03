package com.actionsoft.apps.poc.api;

import com.actionsoft.apps.poc.api.executor.web.APITestWeb;
import com.actionsoft.apps.poc.api.local.app.notification.TestFormatter;
import com.actionsoft.apps.poc.api.local.app.web.TestWeb;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.server.bind.annotation.Controller;
import com.actionsoft.bpms.server.bind.annotation.Mapping;

/**
 * @description API 功能测试用例 - 控制器
 * @author ZhangMing
 * @date 20140224
 * 
 */
@Controller
public class APITestController {
	// API功能测试-入口
	@Mapping("com.actionsoft.apps.poc.api_Home")
	public String apiTestHome(UserContext me) {
		APITestWeb apiTestWeb = new APITestWeb(me);
		return apiTestWeb.getPortal();
	}

	// API功能测试-详细信息页面
	@Mapping("com.actionsoft.apps.poc.api_Info")
	public String apiInfo(UserContext me, String name) {
		APITestWeb apiTestWeb = new APITestWeb(me);
		return apiTestWeb.getAPIInfo(name);
	}

	// API功能测试-详细信息页面-BPMN文件
	@Mapping("com.actionsoft.apps.poc.api_Info_BPMN")
	public String getBPMN(UserContext me, String id) {
		APITestWeb apiTestWeb = new APITestWeb(me);
		return apiTestWeb.getBPMN(id);
	}

	// API功能测试-详细信息页面-BPMN文件
	@Mapping("com.actionsoft.apps.poc.api_Info_BPMNDiagram")
	public String getBPMNDiagram(UserContext me, String id) {
		APITestWeb apiTestWeb = new APITestWeb(me);
		return apiTestWeb.getBPMNDiagram(id);
	}

	// API测试用例-执行页面
	@Mapping("com.actionsoft.apps.poc.api_ExecuteInfo")
	public String apiExecuteInfo(UserContext me) {
		APITestWeb apiTestWeb = new APITestWeb(me);
		return apiTestWeb.apiExecuteInfo();
	}

	// 执行API测试用例
	@Mapping("com.actionsoft.apps.poc.api_ExecuteAll")
	public String apiExecuteAll(UserContext me, String names) {
		APITestWeb apiTestWeb = new APITestWeb(me);
		return apiTestWeb.apiExecuteAll(names.split(","));
	}

	// 获得API测试用例执行信息
	@Mapping("com.actionsoft.apps.poc.api_GetExecuteInfo")
	public String getAPIExecuteInfo(UserContext me, String name) {
		APITestWeb apiTestWeb = new APITestWeb(me);
		return apiTestWeb.getExecuteInfo(name);
	}

	// 清除测试用例执行信息
	@Mapping("com.actionsoft.apps.poc.api_ClearExecuteInfo")
	public String clearExecuteInfo(UserContext me, String thread) {
		APITestWeb apiTestWeb = new APITestWeb(me);
		apiTestWeb.clearExecuteInfo(thread);
		return "";
	}

	// 测试通知消息的一个按钮
	@Mapping("com.actionsoft.apps.poc.api_test_button3")
	public String testButtonAction(UserContext me, String id, String msg) {
		return new TestFormatter().testAjaxButton(me, id, msg);
	}

	// 测试通知消息的一个按钮
	@Mapping(value = "com.actionsoft.apps.poc.api_no_session_test", session = false, noSessionReason = "测试请求", noSessionEvaluate = "该命令无潜在安全风险")
	public String testNoSession(String msg) {
		return new TestWeb(null).getResult(msg);
	}

}
