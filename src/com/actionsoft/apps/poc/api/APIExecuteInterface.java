package com.actionsoft.apps.poc.api;

import java.util.Map;

public interface APIExecuteInterface {
	public String getTestCaseName();

	public void testExecute();

	public boolean execute(Map<String, Object> params);
}
