package com.actionsoft.apps.poc.api.local.app.aslp;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.resource.AppContext;
import com.actionsoft.apps.resource.interop.aslp.ASLP;
import com.actionsoft.apps.resource.interop.aslp.Meta;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;

public class ASLP_Java_Test extends LocalAPITestCase implements ASLP {

	public String getTestCaseName() {
		return "App开发/ASLP/本地Java同步调用";
	}

	public boolean execute(Map<String, Object> params) {
		EngineDebug.info("该测试用例实现了ASLP接口，并模拟调用方给该App发送订单id，获得订单明细数据【同步调用】");
		// 模拟调用方
		String sourceAppId = "com.actionsoft.apps.poc.api";
		// 服务地址
		String aslp = "aslp://com.actionsoft.apps.poc.api/getOrder";
		params = new HashMap<String, Object>();
		try {
			ResponseObject ro = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro.toString().indexOf("error") > -1);
			// 给定必填参数
			params.put("orderId", "12");
			ro = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			EngineDebug.info(ro.toString());
			assertTrue(ro.toString().indexOf("ok") > -1);
		} catch (Exception e) {
			fail(e.toString());
		}
		return true;
	}

	/**
	 * 读订单id参数，给调用方返回订单明细数据
	 * 
	 */
	@Meta(parameter = { "name:'orderId',required:true,allowEmpty:false,maxLen:36,minLen:2,desc:'订单Id'" })
	public ResponseObject call(Map<String, Object> params) {
		ResponseObject ro = ResponseObject.newOkResponse();
		ro.put("orderId", params.get("orderId")).put("supplierId", "007").put("supplierName", "Abc.com");
		ro.put("caller", ((AppContext) params.get("caller")).getName());
		return ro;
	}
}
