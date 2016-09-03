package com.actionsoft.apps.poc.api.executor.util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import junit.framework.TestCase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理API list
 * @author ZhangMing
 * @date 20140224
 */

public class ListUtil {

	/**
	 * 将API list 转化为TreeData
	 * @param list API list
	 * @return String
	 */
	public static String list2Tree(List<LocalAPITestCase> list) {
		if (list == null) {
			return "";
		}
		
		JSONArray jsonArray = new JSONArray();
		TreeMap<String, JSONObject> jsonMap = new TreeMap<String, JSONObject>();
		for (TestCase ptc : list) {
			getJson((LocalAPITestCase)ptc, jsonMap);
		}
		jsonMap.firstEntry().getValue().put("open", true);//仅树的第一个节点展开
		//遍历jsonMap，将value添加到jsonArray中
		for (Map.Entry<String, JSONObject> entry : jsonMap.entrySet()) {
			jsonArray.add(entry.getValue());
        }
		return jsonArray.toString();
	}
	
	/**
	 * 将TestCase转化为JSONObject
	 * @param tc
	 */
	public static void getJson(LocalAPITestCase tc, Map<String, JSONObject> jsonMap){
		String testCaseNames[] = tc.getTestCaseName().split("/");
		
		String cName = "";
		for (int i = 0, n = testCaseNames.length; i < n; i++) {
			String name = testCaseNames[i];
			if (!jsonMap.containsKey(cName + name + "/")) {
				JSONObject jsonObject = new JSONObject();
				String id = tc.getClass().getName().replaceAll("\\.", "_");
				if (i == n-1) {
					jsonObject.put("id", id);
					jsonObject.put("isleaf", true);
				} else {
					jsonObject.put("id", id + "_" + i);
					jsonObject.put("isleaf", false);
					jsonObject.put("open", false);
				}
				if (i > 0) {
					jsonObject.put("pid", jsonMap.get(cName).get("id"));
				}
				jsonObject.put("name", name);
				
				jsonMap.put(cName + name + "/", jsonObject);
			}
			cName += name + "/";
		}
	}
	

	
}
