package com.actionsoft.apps.poc.api.local.app.cache;

import com.actionsoft.apps.poc.api.local.app.dao.TestDao;
import com.actionsoft.apps.poc.api.local.app.dao.TestModel;
import com.actionsoft.apps.resource.plugin.profile.CachePluginProfile;
import com.actionsoft.bpms.commons.cache.Cache;
import com.actionsoft.bpms.commons.cache.CacheManager;
import com.actionsoft.bpms.commons.cache.ReplicateActionListener;

/**
 * 一个简单的测试缓存。通过load方法将所有
 * 
 * @author chengy
 */
public class TestCache extends Cache<String, TestModel> {
	// 同步事件名称
	public static final String ACTION_REFRESH = "refresh";

	/**
	 * 公共构造函数，插件启动时回调。该构造函数注册了一个Refresh的同步监听事件
	 * 
	 * @param profile - 注册时缓存配置
	 */
	public TestCache(CachePluginProfile profile) {
		super(profile);
		registeReplicateActionListener(new RefreshAction());
		registeIndex(TestModelF1Index.class, new TestModelF1Index());
	}

	public static TestModel getByF1(String f1) {
		return getCache().getByIndexSingle(TestModelF1Index.class, f1);
	}

	/**
	 * 根据key查询缓存内容。当id模型不存在或者过期时，从数据库查询对象并缓存
	 */
	public TestModel get(String id) {
		TestModel testModel = super.get(id);
		if (testModel == null) {
			testModel = new TestDao().queryById(id);
			if (testModel != null) {
				put(testModel.getId(), testModel);
			}
		}
		return testModel;
	}

	public static TestModel getModel(String id) {
		return getCache().get(id);
	}

	/**
	 * 刷新方法中发出同步其它集群节点的请求
	 * 
	 * @param id
	 */
	public static void refreshModel(String id) {
		getModel(id).setRefreshTime(System.currentTimeMillis());
		getCache().replicate(ACTION_REFRESH, id);
	}

	/**
	 * 初始化缓存的回调方法。一般永不过期类元数据在App启动时通过该方法加载所有数据到内存
	 */
	public void load() {
		// List<TestModel> list = new TestDao().query().list();
		// for (TestModel model : list) {
		// put(model.getId(), model, false);
		// }
		// ConsolePrinter.info("装载测试模型对象实例 [" + list.size() + "instances]");
	}

	public static TestCache getCache() {
		return (TestCache) CacheManager.getCache(TestCache.class);
	}

	/**
	 * 自定义的同步事件。当集群节点收到这个事件后，会设置模型对象的refreshTime属性为当前时间
	 * 
	 * @author chengy
	 */
	private static class RefreshAction implements ReplicateActionListener {

		@Override
		public void onReplicate(Object param) {
			String id = (String) param;
			getCache().get(id).setRefreshTime(System.currentTimeMillis());
		}

		@Override
		public String getName() {
			return ACTION_REFRESH;
		}
	}

}
