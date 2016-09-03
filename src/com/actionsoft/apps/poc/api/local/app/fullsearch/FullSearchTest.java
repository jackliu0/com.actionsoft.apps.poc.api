package com.actionsoft.apps.poc.api.local.app.fullsearch;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.lifecycle.api.AppsAPIManager;
import com.actionsoft.apps.poc.api.constant.APITestConstant;
import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;

import net.sf.json.JSONObject;

public class FullSearchTest extends LocalAPITestCase {
	public String getTestCaseName() {
		return "App开发/全文检索/常规接口测试";
	}

	public boolean execute(Map<String, Object> params) {
		EngineDebug.info("该测试用例模拟调用方调用全文检索的基本搜索方法");
		// 模拟调用方
		String sourceAppId = "com.actionsoft.apps.poc.api";

		params = new HashMap<String, Object>();
		String aslp;
		try {

			/************************************ 创建索引 **************************************/
			// 服务地址
			// 根据内容创建索引
			aslp = "aslp://com.actionsoft.apps.fullsearch/createIndexByContent";
			params.put("documentId", "1111");
			params.put("repositoryName", "pocFullSearchTest");
			params.put("content", "阳光下慎重地开满了花，朵朵都是我前世的盼望。当你走近请你细听,那颤抖的叶是我等待的热情。");
			params.put("abstract", "只缘感君一回顾，使我思君暮与朝");
			params.put("title", "古乐府");
			params.put("documentPath", "fullsearch/古乐府.doc");
			ResponseObject ro1 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro1.toString().indexOf("ok") > -1);
			EngineDebug.info(ro1.toString());

			// 根据文件路径创建索引
			aslp = "aslp://com.actionsoft.apps.fullsearch/createIndexByFile";
			params = new HashMap<String, Object>();
			params.put("repositoryName", "pocFullSearchTest");
			String path = AppsAPIManager.getInstance().getAppContext(APITestConstant.APITEST).getPath();
			String documentPath = path + "古乐府.doc";
			params.put("documentPath", documentPath);
			ro1 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro1.toString().indexOf("ok") > -1);
			EngineDebug.info(ro1.toString());

			// 查询索引
			aslp = "aslp://com.actionsoft.apps.fullsearch/search";
			params = new HashMap<String, Object>();
			params.put("repositoryName", "pocFullSearchTest");
			params.put("searchText", "阳光下慎重地开满了花");
			JSONObject sortFields = new JSONObject();
			sortFields.put("content", true);
			params.put("sortFields", sortFields.toString());
			ResponseObject ro2 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro2.toString().indexOf("ok") > -1);
			EngineDebug.info(ro2.toString());

			// 高级搜索
			aslp = "aslp://com.actionsoft.apps.fullsearch/advancedSearch";
			params = new HashMap<String, Object>();
			params.put("repositoryName", "pocFullSearchTest");
			params.put("searchText", "content:(+阳光 -遇见)");
			ro2 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro2.toString().indexOf("ok") > -1);
			EngineDebug.info(ro2.toString());

			params.put("searchText", "content:遇见~");
			ro2 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro2.toString().indexOf("ok") > -1);
			EngineDebug.info(ro2.toString());

			/************************************ 修改索引 **************************************/
			aslp = "aslp://com.actionsoft.apps.fullsearch/updateIndex";
			params = new HashMap<String, Object>();
			params.put("documentId", "1111");
			params.put("repositoryName", "pocFullSearchTest");
			params.put("content", "如何让你遇见我，在我最美丽的时刻。为这，我已在佛前求了五百年,求他让我们结一段尘缘");
			params.put("abstract", "只缘感君一回顾，使我思君暮与朝");
			ResponseObject ro3 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro3.toString().indexOf("ok") > -1);
			EngineDebug.info(ro3.toString());

			// 查询索引
			aslp = "aslp://com.actionsoft.apps.fullsearch/search";
			params = new HashMap<String, Object>();
			params.put("repositoryName", "pocFullSearchTest");
			params.put("searchText", "阳光");
			ro2 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro2.toString().indexOf("ok") > -1);
			EngineDebug.info(ro2.toString());

			/************************************ 删除索引 **************************************/
			aslp = "aslp://com.actionsoft.apps.fullsearch/deleteIndex";
			params = new HashMap<String, Object>();
			params.put("repositoryName", "pocFullSearchTest");
			params.put("documentId", "1111");
			ResponseObject ro4 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro4.toString().indexOf("ok") > -1);
			EngineDebug.info(ro4.toString());

			// 查询索引
			aslp = "aslp://com.actionsoft.apps.fullsearch/search";
			params = new HashMap<String, Object>();
			params.put("repositoryName", "pocFullSearchTest");
			params.put("searchText", "遇见");
			ro2 = appAPI.callASLP(appAPI.getAppContext(sourceAppId), aslp, params);
			assertTrue(ro2.toString().indexOf("ok") > -1);
			EngineDebug.info(ro2.toString());

			// 测试http服务调用方式
			// Map<String, Object> httpData =
			// UtilJson.getMapByUrl("http://localhost:8088/portal/r/jd?cmd=API_CALL_ASLP&sourceAppId=com.actionsoft.apps.poc.api&aslp=aslp://com.actionsoft.apps.fullsearch/search&params={\"repositoryName\":\"D:\\fullsearch\",\"searchText\":\"AWS\"});
			// EngineDebug.info(httpData.toString());
		} catch (Exception e) {
			fail(e.toString());
		}
		return true;
	}

}
