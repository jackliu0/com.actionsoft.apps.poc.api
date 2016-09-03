package com.actionsoft.apps.poc.api.local.app.aslp;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.resource.interop.aslp.ASLP;
import com.actionsoft.apps.resource.interop.aslp.Meta;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;

public class ASLP_Java_AsynTest extends LocalAPITestCase implements ASLP {

	public String getTestCaseName() {
		return "App开发/ASLP/本地Java异步调用";
	}

	public boolean execute(Map<String, Object> params) {
		EngineDebug.info("该测试用例实现了ASLP接口，并模拟调用方给该App发送检查某订单出库状态的通知【异步调用】");
		// 模拟调用方
		String sourceAppId = "com.actionsoft.apps.poc.api";
		// 服务地址
		String aslp = "aslp://com.actionsoft.apps.poc.api/notifyUpdateOrder";
		params = new HashMap<String, Object>();
		try {
			// 给定必填参数
			params.put("orderId", "123456");
			appAPI.asynCallASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			EngineDebug.info("已执行完毕...");
		} catch (Exception e) {
			fail(e.toString());
		}
		return true;
	}

	/**
	 * 读订单id参数，检查订单出库状态。异步调用不会接收到ResponseObject处理结果
	 */
	@Meta(parameter = { "name:'orderId',required:true,desc:'订单Id'" })
	public ResponseObject call(Map<String, Object> params) {
		String orderId = (String) params.get("orderId");
		// todo 检查订单出库状态
		return ResponseObject.newOkResponse().put("orderId", orderId);
	}

}
