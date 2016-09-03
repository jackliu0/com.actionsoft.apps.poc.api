package com.actionsoft.apps.poc.api.local.app.workbench;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionsoft.bpms.bpmn.engine.model.run.delegate.EAITaskInstance;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;
import com.actionsoft.bpms.server.UserContext;

public class BaiduEAIBehavior {

	public ResponseObject getBehaviors(UserContext user, List<EAITaskInstance> instances) {
		Map<String, Map<String, String>> behaviors = new HashMap<>();
		for (EAITaskInstance instance : instances) {
			Map<String, String> behavior = new HashMap<>();
			// 假设该示例可自定义的actionParameter只提供了一个url
			// 通常可以由开发者自定义一个json串，用来传递更丰富的信息
			String url = instance.getActionParameter();
			// 对含有的@公式进行处理

			behavior.put("font-color", "red");// 字体颜色，如果不定义由工作台App默认给出
			behavior.put("background-color", "yellow");// 行背景颜色，如果不定义由工作台App默认给出
			behavior.put("icon", "../apps/com.actionsoft.apps.poc.api/img/baidu.png");// 16*16图标url地址，如果不定义由工作台App默认给出
			behavior.put("icon-title", "鼠标提示文字");// 图标鼠标提示文字,若不提供则没有文字提示
			behavior.put("title", instance.getTitle());// 标题，如果不定义由工作台App默认给出
			behavior.put("url", url);// 点击该行时的url地址，如果未提供，不支持点击事件
			behavior.put("type", "百度");// 任务类型,分组的基本类型分组依据,若不提供则按照应用系统名称（EXT1字段分组）
			behaviors.put(instance.getId(), behavior);
		}

		return ResponseObject.newOkResponse().put("behaviors", behaviors);
	}
}
