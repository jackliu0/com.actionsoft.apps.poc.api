package com.actionsoft.apps.poc.api.local.app.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.actionsoft.bpms.client.notification.NotificationMessageFormatter;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;
import com.actionsoft.bpms.server.UserContext;

public class TestFormatter implements NotificationMessageFormatter {

	/**
	 * 当用户接收到提醒窗口或在通知中心点击通知条目时被触发，格式化通知的内容。(例如发送的原始通知是个json格式，包含了信息id、
	 * 附加id等开发者需要的数据，开发者可以根据这些信息提供进阶处理操作)
	 * 
	 * @param user 通知查看人
	 * @param content 发送的原始内容
	 * @return ResponseObject，包含content和buttons两个变量
	 */
	public ResponseObject parser(UserContext user, String content) {
		JSONObject json = JSONObject.fromObject(content);
		// 逻辑处理，开发者自定义的格式，见发送时的封装
		String id = json.getString("id");
		String data = json.getString("data");

		// 封装结果
		ResponseObject ro = ResponseObject.newOkResponse();
		ro.put("content", "这是解析到的值[<font color=red>" + data + "</font>]");
		// 是否支持button，如果支持，按该格式封装
		List<Map<String, String>> buttons = new ArrayList<>();
		if ("1".equals(id)) {
			Map<String, String> button1 = new HashMap<>();
			button1.put("name", "按钮1");
			button1.put("action", "http://www.baidu.com");
			button1.put("target", "_blank");// 新窗口，不常用。只允许三个常量：_blank/mainFrame/ajax
			button1.put("color", "blue");// 只允许三个常量：blue/white/red
			buttons.add(button1);

			Map<String, String> button2 = new HashMap<>();
			button2.put("name", "按钮2");
			button2.put("action", "http://www.baidu.com");
			button2.put("target", "mainFrame");// 在该门户的内嵌工作区打开。只允许三个常量：_blank/mainFrame/ajax
			button2.put("color", "white");// 只允许三个常量：blue/white/red
			buttons.add(button2);
		} else if ("2".equals(id)) {
			Map<String, String> button2 = new HashMap<>();
			button2.put("name", "按钮2");
			button2.put("action", "http://www.baidu.com");
			button2.put("target", "mainFrame");// 在该门户的内嵌工作区打开。只允许三个常量：_blank/mainFrame/ajax
			button2.put("color", "white");// 只允许三个常量：blue/white/red
			buttons.add(button2);
		} else if ("3".equals(id)) {
			Map<String, String> button2 = new HashMap<>();
			button2.put("name", "按钮2");
			button2.put("action", "http://www.baidu.com");
			button2.put("target", "mainFrame");// 在该门户的内嵌工作区打开。只允许三个常量：_blank/mainFrame/ajax
			button2.put("color", "white");// 只允许三个常量：blue/white/red
			buttons.add(button2);

			Map<String, String> button3 = new HashMap<>();
			button3.put("name", "按钮3");
			button3.put("action", "./jd?sid=@sid&cmd=com.actionsoft.apps.poc.api_test_button3&id=" + id + "&msg=From Button3");
			button3.put("target", "ajax");// 执行ajax操作，并将处理结果返回，必须是一个ResponseObject简单格式。只允许三个常量：_blank/mainFrame/ajax
			button3.put("color", "red");// 只允许三个常量：blue/white/red
			buttons.add(button3);
		} else if ("4".equals(id)) {
			// 如果只给定名称，没有action动作参数，在该button区域显示一个简单提示信息。例如用户批准了加入小组的申请，在通知中心再次查看该信息时逻辑判断该用户已加入，则不提供批准的button，显示“ABC已被批准加入XYZ小组”
			Map<String, String> button4 = new HashMap<>();
			button4.put("name", "info信息");
			buttons.add(button4);
		}
		ro.put("buttons", buttons);
		return ro;
	}

	public String testAjaxButton(UserContext user, String id, String msg) {
		return ResponseObject.newOkResponse().ok("申请被批准！id=" + id + ",msg=" + msg).toString();
	}
}
