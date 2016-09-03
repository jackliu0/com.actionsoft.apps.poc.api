package com.actionsoft.apps.poc.api.local;

import java.util.HashMap;
import java.util.Map;

import com.actionsoft.apps.poc.api.LocalAPIExecuteInterface;
import com.actionsoft.bpms.bpmn.engine.core.test.ProcessAPITestCase;

/**
 * junit test case & API Excution interface
 * 
 */
public abstract class LocalAPITestCase extends ProcessAPITestCase implements LocalAPIExecuteInterface {

	// junit test case
	public void testExecute() {
		Map<String, Object> params = new HashMap<String, Object>();
		assertTrue(execute(params));
	}

}
