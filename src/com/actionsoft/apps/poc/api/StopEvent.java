package com.actionsoft.apps.poc.api;

import com.actionsoft.apps.listener.AppListener;
import com.actionsoft.apps.resource.AppContext;

public class StopEvent implements AppListener {

	public boolean before(AppContext context) {
		System.out.println("Test StopEvent[Before]");
		return true;
	}

	public void after(AppContext context) {
		System.out.println("Test StopEvent[After]");
	}

}
