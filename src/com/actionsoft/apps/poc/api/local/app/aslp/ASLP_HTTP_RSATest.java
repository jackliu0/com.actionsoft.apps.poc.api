package com.actionsoft.apps.poc.api.local.app.aslp;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.resource.AppContext;
import com.actionsoft.apps.resource.interop.aslp.ASLP;
import com.actionsoft.apps.resource.interop.aslp.Meta;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;
import com.actionsoft.bpms.util.Base64;
import com.actionsoft.bpms.util.RSA;
import com.actionsoft.bpms.util.UtilJson;
import com.actionsoft.sdk.local.SDK;

public class ASLP_HTTP_RSATest extends LocalAPITestCase implements ASLP {

	public String getTestCaseName() {
		return "App开发/ASLP/HTTP RSA鉴权";
	}

	// 测试http服务调用方式
	public boolean execute(Map<String, Object> params) {
		EngineDebug.info("该测试用例实现了ASLP接口，并模拟调用方通过HTTP+RSA的鉴权方式给该App发送订单id，获得订单明细数据");
		// 模拟调用方
		String sourceAppId = "com.actionsoft.apps.poc.api";
		params = new HashMap<String, Object>();
		try {
			// RSA服务封装的要点：
			// a.ASLP服务提供者，用rsa-keygen.bat(sh)制作与该appId名称相同的证书，并移动到该应用的根目录下(注意不要分发.pfx私钥证书给他人)
			// b.ASLP服务消费者，获得提供者的cer公钥证书（xxxAppId.cer文件），并复制到该应用的根目录下，调用时用该公钥证书进行加密
			// c.为确保证书的安全性，建议安装或部署生产机可按a、b步骤重新制作新证书
			AppContext sourceApp = SDK.getAppAPI().getAppContext(sourceAppId);
			// 用公钥证书加密RSA-KEY并Base64URL(注意是Base64URL的编码，以能够通过http正常发送和接收)
			String authentication = new String(Base64.encodeURL(RSA.encrypt(sourceApp.getPath() + "com.actionsoft.apps.poc.api.cer", "RSA-KEY".getBytes())));
			Map<String, Object> httpData = UtilJson.getMapByUrl("http://localhost:8088/portal/r/jd?cmd=API_CALL_ASLP&sourceAppId=com.actionsoft.apps.poc.api&aslp=aslp://com.actionsoft.apps.poc.api/getOrderByRSA&params={\"orderId\":\"123456\"}&authentication=" + authentication);
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
