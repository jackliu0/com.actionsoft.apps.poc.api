package com.actionsoft.apps.poc.api.executor.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import com.actionsoft.apps.lifecycle.api.AppsAPIManager;
import com.actionsoft.apps.poc.api.APITestExecuter;
import com.actionsoft.apps.poc.api.constant.APITestConstant;
import com.actionsoft.apps.poc.api.executor.util.FileUtil;
import com.actionsoft.apps.poc.api.executor.util.ListUtil;
import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.apps.poc.api.model.TestCaseModel;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.commons.htmlframework.HtmlPageTemplate;
import com.actionsoft.bpms.commons.mvc.view.ActionWeb;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.exception.AWSAPIException;
import com.actionsoft.sdk.local.SDK;

/**
 * @description API 功能测试用例 - web
 * @author ZhangMing
 * @date 20140224
 * 
 */

public class APITestWeb extends ActionWeb {
	private static Map<String, Map<String, TestCaseModel>> testCaseMap = new HashMap<String, Map<String, TestCaseModel>>(); // key:线程ID，
																															// value:测试用例
	private static Map<String, List<String>> monitorLogsMap = new ConcurrentHashMap<String, List<String>>();

	public APITestWeb(UserContext uc) {
		super(uc);
	}

	/**
	 * API功能测试入口
	 * 
	 * @return
	 */
	public String getPortal() {
		Map<String, Object> macroLibraries = new HashMap<String, Object>();
		macroLibraries.put("treeData", ListUtil.list2Tree(APITestExecuter.getTestCaseList()));
		macroLibraries.put("sid", super.getContext().getSessionId());
		macroLibraries.put("version", SDK.getPlatformAPI().getAWSEngine().getProcessEngineVersion());
		return HtmlPageTemplate.merge(APITestConstant.APITEST, "com.actionsoft.apps.poc.api.home.htm", macroLibraries);
	}

	/**
	 * API详细信息页面
	 * 
	 * @param name API类名
	 * @return
	 */
	public String getAPIInfo(String name) {
		Map<String, Object> macroLibraries = new HashMap<String, Object>();
		macroLibraries.put("sid", super.getContext().getSessionId());
		macroLibraries.put("name", name);

		String className = null;
		LocalAPITestCase tc = getTestCaseObjectByName(name);
		if (tc != null) {
			className = tc.getClass().toString();
			className = className.substring(6).replace('.', '/');
			String path = AppsAPIManager.getInstance().getAppContext(APITestConstant.APITEST).getPath();
			String filePath = path + "src/" + className + ".java";
			macroLibraries.put("source", FileUtil.readFile(filePath));
		}
		if (tc instanceof LocalProcessAPITestCase) { // 如果是Process
														// APITest，显示BPMN文件和图形，否则不显示
			LocalProcessAPITestCase ptc = (LocalProcessAPITestCase) tc;
			macroLibraries.put("id", ptc.getProcessDefId());
		} else {
			macroLibraries.put("showFile", "display:none;");
		}
		return HtmlPageTemplate.merge(APITestConstant.APITEST, "com.actionsoft.apps.poc.api.info.htm", macroLibraries);
	}

	/**
	 * 获得BPMN文件
	 * 
	 * @param name
	 * @return
	 */
	public String getBPMN(String id) {
		String bpmn = "";
		if (id != null && !"".equals(id)) {
			try {
				bpmn = SDK.getRepositoryAPI().getBPMN(id);
			} catch (AWSAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bpmn;
	}

	/**
	 * 获得BPMN图URL
	 * 
	 * @param id
	 * @return
	 */
	public String getBPMNDiagram(String id) {
		String bpmnDiagramUrl = "";
		if (id != null && !"".equals(id)) {
			try {
				bpmnDiagramUrl = SDK.getRepositoryAPI().getBPMNDiagramUrl(id, 1, super.getContext().getSessionId());
			} catch (AWSAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bpmnDiagramUrl;
	}

	/**
	 * 根据TestCase的类名获得对应的类对象
	 * 
	 * @param id
	 * @return
	 */
	private LocalAPITestCase getTestCaseObjectByName(String className) {
		LocalAPITestCase ptc = null;
		List<LocalAPITestCase> list = APITestExecuter.getTestCaseList();
		for (int i = 0; i < list.size(); i++) {
			ptc = (LocalAPITestCase) list.get(i);
			if (className.equals(ptc.getClass().getName().replaceAll("\\.", "_"))) {
				return ptc;
			}
		}
		return null;
	}

	/**
	 * API测试用例执行页面
	 * 
	 * @return
	 */
	public String apiExecuteInfo() {
		Map<String, Object> macroLibraries = new HashMap<String, Object>();
		macroLibraries.put("sid", super.getContext().getSessionId());

		return HtmlPageTemplate.merge(APITestConstant.APITEST, "com.actionsoft.apps.poc.api.execute.htm", macroLibraries);
	}

	/**
	 * 执行API TestCase
	 * 
	 * @param names
	 * @return 线程ID
	 */
	public String apiExecuteAll(String[] names) {
		String currentThread = Thread.currentThread().hashCode() + "";

		Map<String, TestCaseModel> map = new HashMap<String, TestCaseModel>();
		for (String name : names) {
			TestCaseModel testCase = new TestCaseModel(name);
			map.put(name, testCase);
		}
		testCaseMap.put(currentThread, map);
		monitorLogsMap.put(currentThread, new ArrayList<String>());

		// 监控当前线程产生的日志
		EngineDebug.openMonitor();
		// 顺序执行测试用例
		long allCost = 0;
		for (String name : names) {
			TestCaseModel testCase = testCaseMap.get(currentThread).get(name);
			LocalAPITestCase ptc = getTestCaseObjectByName(testCase.getClassName());
			testCase.setTestCaseName(ptc.getTestCaseName());
			testCase.setBeginTime(new Date().toString());
			testCase.setState(1);
			long begin = System.currentTimeMillis();
			EngineDebug.info("");
			EngineDebug.info("---------------------------------------");
			EngineDebug.info("准备执行[" + ptc.getTestCaseName() + "]");
			boolean isOk = false;
			try {
				isOk = ptc.execute(null);
			} catch (Error re) {
				EngineDebug.err(re.toString());
			} catch (Exception e) {
				EngineDebug.err(e.toString());
			}
			testCase.setResult(isOk);
			long cost = System.currentTimeMillis() - begin;
			if (testCase.isResult()) {
				EngineDebug.info("执行完毕[" + ptc.getTestCaseName() + "]状态[成功],用时[" + cost + "]毫秒");
			} else {
				EngineDebug.err("执行完毕[" + ptc.getTestCaseName() + "]状态[失败],用时[" + cost + "]毫秒");
			}
			allCost = allCost + cost;
			testCase.setEndTime(new Date().toString());
			testCase.setState(2);
		}
		EngineDebug.info("");
		EngineDebug.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		EngineDebug.info("共" + names.length + "个Case被执行，总用时[" + allCost + "]毫秒");
		// 释放当前线程产生的日志
		try {
			Thread.sleep(1000);
			EngineDebug.closeMonitor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentThread;
	}

	/**
	 * 获得TestCase执行信息
	 * 
	 * @param id TestCase id
	 * @return TestCase执行状态
	 */
	public String getExecuteInfo(String name) {
		String thread = getThreadByTestCaseId(name);
		ResponseObject ro = ResponseObject.newOkResponse();
		if (thread == null || !testCaseMap.containsKey(thread) || !testCaseMap.get(thread).containsKey(name)) {

		} else {
			ro.put("className", name);
			TestCaseModel testCaseModel = testCaseMap.get(thread).get(name);
			ro.put("beginTime", testCaseModel.getBeginTime());
			ro.put("endTime", testCaseModel.getEndTime());
			ro.put("state", testCaseModel.getState());
			ro.put("result", testCaseModel.isResult());
			ro.put("log", getExecuteLog(thread));
		}
		return ro.toString();
	}

	/**
	 * 获得TestCase执行日志
	 * 
	 * @param id TestCase id
	 * @return 执行日志
	 */
	public String getExecuteLog(String thread) {
		StringBuffer log = new StringBuffer();
		// 获得监控的历史和最新产生的日志
		List<String> newLogs = EngineDebug.getMonitorLogs(Integer.parseInt(thread));
		if (newLogs != null && !newLogs.isEmpty()) {
			List<String> logs;
			if (monitorLogsMap != null && monitorLogsMap.containsKey(thread)) {
				logs = monitorLogsMap.get(thread);
			} else {
				logs = new ArrayList<String>();
			}
			newLogs = newLogs.subList(logs.size(), newLogs.size());
			if (newLogs != null && newLogs.size() > 0) {
				logs.addAll(newLogs);
				monitorLogsMap.put(thread, logs);
				for (String str : newLogs) {
					String color = "#fff";
					if (str.contains("ENGINE INF")) {
						color = "#7bd827"; // 绿色
					} else if (str.contains("ENGINE ERR")) {
						color = "#af2018"; // 红色
					} else if (str.contains("ENGINE WARN")) {
						color = "#f08047"; // 橙色
					}
					log.append("<div style='color:" + color + ";'>" + str + "</div>");
				}
			}
		}
		return log.toString();
	}

	/**
	 * 通过TestCaseId获得线程Id
	 * 
	 * @param id
	 * @return 线程Id
	 */
	private String getThreadByTestCaseId(String id) {
		for (Map.Entry<String, Map<String, TestCaseModel>> entry : testCaseMap.entrySet()) {
			if (entry.getValue().containsKey(id)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 清除执行信息
	 * 
	 * @param thread 线程
	 */
	public void clearExecuteInfo(String thread) {
		if (thread != null) {
			if (testCaseMap.containsKey(thread))
				testCaseMap.remove(thread);
			if (monitorLogsMap.containsKey(thread))
				monitorLogsMap.remove(thread);
		}
	}
}
