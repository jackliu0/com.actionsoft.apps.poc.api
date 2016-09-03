package com.actionsoft.apps.poc.api;

import java.util.ArrayList;
import java.util.List;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_HTTP_KeyTest;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_HTTP_RSATest;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_HTTP_SessionIdTest;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_Java_AsynTest;
import com.actionsoft.apps.poc.api.local.app.aslp.ASLP_Java_Test;
import com.actionsoft.apps.poc.api.local.app.at.AtAPITest;
import com.actionsoft.apps.poc.api.local.app.cache.CacheTest;
import com.actionsoft.apps.poc.api.local.app.dao.DaoTest;
import com.actionsoft.apps.poc.api.local.app.entaddress.QueryHistoryRecordsASLPTest;
import com.actionsoft.apps.poc.api.local.app.entaddress.QueryMyCompanysAndDepartmentASLPTest;
import com.actionsoft.apps.poc.api.local.app.entaddress.QuerySubDepartmentAndContactsASLPTest;
import com.actionsoft.apps.poc.api.local.app.entaddress.SearchDepartmentsAndContactsASLPTest;
import com.actionsoft.apps.poc.api.local.app.entaddress.SettingHistoryRecordsASLPTest;
import com.actionsoft.apps.poc.api.local.app.fullsearch.FullSearchTest;
import com.actionsoft.apps.poc.api.local.app.i18n.I18NAPITest;
import com.actionsoft.apps.poc.api.local.app.notification.AppNotificationAPITest1;
import com.actionsoft.apps.poc.api.local.app.notification.AppNotificationAPITest2;
import com.actionsoft.apps.poc.api.local.app.property.AppPropertyAPITest;
import com.actionsoft.apps.poc.api.local.process.adhoc.UndoTaskProcess1Test;
import com.actionsoft.apps.poc.api.local.process.adhoc.UndoTaskProcess2Test;
import com.actionsoft.apps.poc.api.local.process.adhoc.UndoTaskProcess3Test;
import com.actionsoft.apps.poc.api.local.process.adhoc.UndoTaskProcess4Test;
import com.actionsoft.apps.poc.api.local.process.comment.CommentProcess1Test;
import com.actionsoft.apps.poc.api.local.process.comment.CommentProcess2Test;
import com.actionsoft.apps.poc.api.local.process.comment.CommentProcess3Test;
import com.actionsoft.apps.poc.api.local.process.delegation.QueryDelegationWorklistTest;
import com.actionsoft.apps.poc.api.local.process.eaitask.EAITaskTest;
import com.actionsoft.apps.poc.api.local.process.gateway.ComplexGatewayProcess1Test;
import com.actionsoft.apps.poc.api.local.process.gateway.ComplexGatewayProcess2Test;
import com.actionsoft.apps.poc.api.local.process.gateway.ComplexGatewayProcess3Test;
import com.actionsoft.apps.poc.api.local.process.gateway.EventBasedGateway1ProcessTest;
import com.actionsoft.apps.poc.api.local.process.gateway.EventBasedGateway2ProcessTest;
import com.actionsoft.apps.poc.api.local.process.gateway.EventBasedGateway3ProcessTest;
import com.actionsoft.apps.poc.api.local.process.gateway.ExclusiveGatewayProcessTest;
import com.actionsoft.apps.poc.api.local.process.gateway.InclusiveGatewayProcessTest;
import com.actionsoft.apps.poc.api.local.process.gateway.ParallelGatewayProcessTest;
import com.actionsoft.apps.poc.api.local.process.listener.process.ProcessListenerTest;
import com.actionsoft.apps.poc.api.local.process.listener.servicetask.ServiceTaskListenerTest;
import com.actionsoft.apps.poc.api.local.process.listener.usertask.UserTaskListenerTest;
import com.actionsoft.apps.poc.api.local.process.query.QueryProcessTest;
import com.actionsoft.apps.poc.api.local.process.query.QueryTaskTest;
import com.actionsoft.apps.poc.api.local.process.query.QueryWorklistTest;
import com.actionsoft.apps.poc.api.local.process.system.GatewayProcessTest;
import com.actionsoft.apps.poc.api.local.process.system.NoneProcessTest;
import com.actionsoft.apps.poc.api.local.process.var.ProcessVariableTest;
import com.actionsoft.bpms.bpmn.engine.core.test.ProcessAPITestCase;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class APITestExecuter extends ProcessAPITestCase {

	private static List<LocalAPITestCase> list = new ArrayList<LocalAPITestCase>();

	static {
		// gateway
		list.add(new InclusiveGatewayProcessTest());
		list.add(new ParallelGatewayProcessTest());
		list.add(new ExclusiveGatewayProcessTest());
		list.add(new ComplexGatewayProcess1Test());
		list.add(new ComplexGatewayProcess2Test());
		list.add(new ComplexGatewayProcess3Test());
		list.add(new EventBasedGateway1ProcessTest());
		list.add(new EventBasedGateway2ProcessTest());
		list.add(new EventBasedGateway3ProcessTest());

		// comments
		list.add(new CommentProcess1Test());
		list.add(new CommentProcess2Test());
		list.add(new CommentProcess3Test());

		// adhoc
		list.add(new UndoTaskProcess1Test());
		list.add(new UndoTaskProcess2Test());
		list.add(new UndoTaskProcess3Test());
		list.add(new UndoTaskProcess4Test());
		// system
		list.add(new GatewayProcessTest());
		list.add(new NoneProcessTest());

		// processvar
		list.add(new ProcessVariableTest());

		// eai task
		list.add(new EAITaskTest());
		// query
		list.add(new QueryTaskTest());
		list.add(new QueryDelegationWorklistTest());
		list.add(new QueryProcessTest());
		list.add(new QueryWorklistTest());
		// listener
		list.add(new ProcessListenerTest());
		list.add(new ServiceTaskListenerTest());
		list.add(new UserTaskListenerTest());

		// app api
		list.add(new ASLP_Java_Test());
		list.add(new ASLP_Java_AsynTest());
		list.add(new ASLP_HTTP_RSATest());
		list.add(new ASLP_HTTP_KeyTest());
		list.add(new ASLP_HTTP_SessionIdTest());
		list.add(new I18NAPITest());
		list.add(new FullSearchTest());
		list.add(new AppPropertyAPITest());
		list.add(new AppNotificationAPITest1());
		list.add(new AppNotificationAPITest2());
		list.add(new AtAPITest());

		// dao
		list.add(new DaoTest());
		list.add(new CacheTest());

		// 单位通讯录
		list.add(new QueryHistoryRecordsASLPTest());
		list.add(new SearchDepartmentsAndContactsASLPTest());
		list.add(new QueryMyCompanysAndDepartmentASLPTest());
		list.add(new QuerySubDepartmentAndContactsASLPTest());
		list.add(new SettingHistoryRecordsASLPTest());

	}

	/**
	 * 获得POC API的全部测试用例
	 * 
	 * @return
	 */
	public static List<LocalAPITestCase> getTestCaseList() {
		return list;
	}

	// 批量执行TestCase
	public static TestSuite suite() {
		// 删除全部实例数据
		new ProcessAPITestCase().clearProcessRuntimeDB();
		TestSuite suite = new TestSuite();
		for (TestCase testCase : list) {
			suite.addTestSuite(testCase.getClass());
		}
		// suite.addTestSuite(NoneProcessTest.class);

		return suite;
	}

}
