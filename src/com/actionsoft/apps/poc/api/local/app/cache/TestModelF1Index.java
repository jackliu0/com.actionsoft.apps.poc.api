package com.actionsoft.apps.poc.api.local.app.cache;

import com.actionsoft.apps.poc.api.local.app.dao.TestModel;
import com.actionsoft.bpms.commons.cache.SingleValueIndex;

/**
 * 为F1字段建立缓存唯一索引。要求F1字段值不能有重复的
 *  
 * @author chengy
 */
public class TestModelF1Index extends SingleValueIndex<String, TestModel> {

	/**
	 * 返回模型对象的索引值
	 */
	public String key(TestModel model) {
		return model.getF1();
	}

}
