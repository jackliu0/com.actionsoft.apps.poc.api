package com.actionsoft.apps.poc.api.local.app.aslp;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.resource.AppContext;
import com.actionsoft.apps.resource.interop.aslp.ASLP;
import com.actionsoft.apps.resource.interop.aslp.Meta;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;
import com.actionsoft.bpms.util.UtilJson;

public class ASLP_HTTP_SessionIdTest extends LocalAPITestCase implements ASLP {

	public String getTestCaseName() {
		return "App开发/ASLP/HTTP 会话鉴权";
	}

	// 测试http服务调用方式
	public boolean execute(Map<String, Object> params) {
		EngineDebug.info("该测试用例实现了ASLP接口，并模拟调用方通过HTTP+AWS SID的鉴权方式给该App发送订单id，获得订单明细数据");
		params = new HashMap<String, Object>();
		try {
			// 当前用户访问会话
			String authentication = appAPI.getUserContext().getSessionId();
			Map<String, Object> httpData = UtilJson.getMapByUrl("http://localhost:8088/portal/r/jd?cmd=API_CALL_ASLP&sourceAppId=com.actionsoft.apps.poc.api&aslp=aslp://com.actionsoft.apps.poc.api/getOrderBySid&params={\"orderId\":\"123456\"}&authentication=" + authentication);
			EngineDebug.info(httpData.toString());
			assertTrue(httpData.get("result").equals("ok"));
		} catch (Exception e) {
			fail(e.toString());
		}
		return true;
	}

	/**
	 * 读订单id参数，给调用方返回订单明细数据
	 */
	@Meta(parameter = { "name:'orderId',required:true,desc:'订单Id'" })
	public ResponseObject call(Map<String, Object> params) {
		ResponseObject ro = ResponseObject.newOkResponse();
		ro.put("orderId", params.get("orderId")).put("supplierId", "007").put("supplierName", "Abc.com");
		// test caller info
		ro.put("caller", ((AppContext) params.get("caller")).getName());
		return ro;
	}
}
