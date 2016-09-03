package com.actionsoft.apps.poc.api.local.app.web;

import com.actionsoft.bpms.commons.mvc.view.ActionWeb;
import com.actionsoft.bpms.server.UserContext;

public class TestWeb extends ActionWeb {

	public TestWeb(UserContext me) {
		super(me);
	}

	/**
	 * No Session Response Demo
	 * 
	 * @param msg
	 * @return
	 */
	public String getResult(String msg) {
		System.out.println(msg);
		return msg;
	}
}
