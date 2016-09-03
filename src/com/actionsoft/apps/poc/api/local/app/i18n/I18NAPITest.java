package com.actionsoft.apps.poc.api.local.app.i18n;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.commons.htmlframework.HtmlPageTemplate;
import com.actionsoft.bpms.server.DispatcherRequest;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.server.conf.portal.LanguageModel;
import com.actionsoft.bpms.util.UtilString;
import com.actionsoft.sdk.local.SDK;

public class I18NAPITest extends LocalAPITestCase {

	public String getTestCaseName() {
		return "App开发/多语言/常规接口测试";
	}

	public boolean execute(Map<String, Object> params) {
		UserContext me = DispatcherRequest.getUserContext();
		// 当前语言
		String currentLang = me.getLanguage();
		List<LanguageModel> langs = SDK.getPlatformAPI().getlanguages();
		for (LanguageModel lang : langs) {
			EngineDebug.info("---切换界面语言(" + lang.getName() + "/" + lang.getName() + ")---");
			SDK.getPortalAPI().changeUserLanguages(me.getSessionId(), lang.getName());
			EngineDebug.info(SDK.getAppAPI().i18NValue("com.actionsoft.apps.poc.api", me, "info1"));
			// 测试模版
			Map<String, Object> macroLibraries = new HashMap<String, Object>();
			String html = HtmlPageTemplate.merge("com.actionsoft.apps.poc.api", "test.htm", macroLibraries);
			EngineDebug.info(new UtilString(new UtilString(html).replace("<", "&lt;")).replace(">", "&gt;"));
		}
		// 还原
		SDK.getPortalAPI().changeUserLanguages(me.getSessionId(), currentLang);
		return true;
	}

}
