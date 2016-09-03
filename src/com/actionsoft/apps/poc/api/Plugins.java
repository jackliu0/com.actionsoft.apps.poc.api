package com.actionsoft.apps.poc.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionsoft.apps.listener.PluginListener;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_HTTP_KeyTest;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_HTTP_RSATest;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_HTTP_SessionIdTest;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_Java_AsynTest;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_Java_Test;
import com.actionsoft.apps.poc.api.local.app.cache.TestCache;
import com.actionsoft.apps.poc.api.local.app.notification.AppNotificationAPITest1;
import com.actionsoft.apps.poc.api.local.app.notification.AppNotificationAPITest2;
import com.actionsoft.apps.poc.api.local.app.notification.TestFormatter;
import com.actionsoft.apps.poc.api.local.app.workbench.BaiduEAIBehavior;
import com.actionsoft.apps.poc.api.local.process.listener.pub.TestUserTaskNotification;
import com.actionsoft.apps.resource.AppContext;
import com.actionsoft.apps.resource.plugin.profile.ASLPPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.AWSPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.AppExtensionProfile;
import com.actionsoft.apps.resource.plugin.profile.CachePluginProfile;
import com.actionsoft.apps.resource.plugin.profile.FullSearchPluginProfile;
import com.actionsoft.apps.resource.plugin.profile.HttpASLP;
import com.actionsoft.apps.resource.plugin.profile.ProcessPublicEventPluginProfile;

/**
 * 注册插件
 */
public class Plugins implements PluginListener {
	public Plugins() {
	}

	public List<AWSPluginProfile> register(AppContext context) {
		// 存放本应用的全部插件扩展点描述
		List<AWSPluginProfile> list = new ArrayList<AWSPluginProfile>();
		// Java同步调用
		list.add(new ASLPPluginProfile("getOrder", ASLP_Java_Test.class.getName(), "读取订单信息", null));
		// Java异步调用
		list.add(new ASLPPluginProfile("notifyUpdateOrder", ASLP_Java_AsynTest.class.getName(), "通知检查订单出库状态", new HttpASLP(HttpASLP.AUTH_AWS_SID)));
		// http+RSA
		list.add(new ASLPPluginProfile("getOrderByRSA", ASLP_HTTP_RSATest.class.getName(), "读取订单信息", new HttpASLP(HttpASLP.AUTH_RSA)));
		// http+Key
		list.add(new ASLPPluginProfile("getOrderByKey", ASLP_HTTP_KeyTest.class.getName(), "读取订单信息", new HttpASLP(HttpASLP.AUTH_KEY, "ASLP-KEY")));
		// http+SessionId
		list.add(new ASLPPluginProfile("getOrderBySid", ASLP_HTTP_SessionIdTest.class.getName(), "读取订单信息", new HttpASLP(HttpASLP.AUTH_AWS_SID)));

		list.add(new CachePluginProfile(TestCache.class));
		// ---------------------流程全局事件监听-----------------------------
		list.add(new ProcessPublicEventPluginProfile(TestUserTaskNotification.class.getName(), "测试监听全局流程事件"));
		// test fullsearch
		list.add(new FullSearchPluginProfile("pocFullSearchTest", false, "测试全文检索API接口"));

		// 注册应用扩展点
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("systemName", AppNotificationAPITest1.systemName);
		params1.put("icon", "");
		list.add(new AppExtensionProfile("通知中心->API测试-简单消息", "aslp://com.actionsoft.apps.notification/registerApp", params1));

		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("systemName", AppNotificationAPITest2.systemName);
		params2.put("icon", "");
		params2.put("formatter", TestFormatter.class.getName());
		list.add(new AppExtensionProfile("通知中心->API测试-自定义消息", "aslp://com.actionsoft.apps.notification/registerApp", params2));

		// 注册应用扩展点
		Map<String, Object> params3 = new HashMap<String, Object>();
		params3.put("applicationName", "百度");
		params3.put("behaviorClass", BaiduEAIBehavior.class.getName());
		list.add(new AppExtensionProfile("我的工作台->百度任务行为", "aslp://com.actionsoft.apps.workbench/registerEAIBehavior", params3));

		return list;
	}
}
