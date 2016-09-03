package com.actionsoft.apps.poc.api.local.app.at;

import java.util.Date;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.commons.at.ExpressionContext;
import com.actionsoft.bpms.server.DispatcherRequest;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.util.Sequence;
import com.actionsoft.bpms.util.UtilDate;
import com.actionsoft.sdk.local.SDK;

public class AtAPITest extends LocalAPITestCase {

	public String getTestCaseName() {
		return "App开发/At公式/测试验证";
	}

	public boolean execute(Map<String, Object> params) {
		try {
			EngineDebug.info("---base---");
			assertTrue(SDK.getRuleAPI().executeAtScript("a@a").equals("a@a"));
			EngineDebug.info("a@a -> " + SDK.getRuleAPI().executeAtScript("a@a"));
			assertTrue(SDK.getRuleAPI().executeAtScript("a,a@a,a").equals("a,a@a,a"));
			EngineDebug.info("a,a@a,a -> " + SDK.getRuleAPI().executeAtScript("a,a@a,a"));
			assertTrue(SDK.getRuleAPI().executeAtScript("a+a@a+a").equals("a+a@a+a"));
			EngineDebug.info("a+a@a+a -> " + SDK.getRuleAPI().executeAtScript("a+a@a+a"));
			assertTrue(SDK.getRuleAPI().executeAtScript("a@uid(admin)a").equals("aadmina"));
			EngineDebug.info("a@uid(admin)a -> " + SDK.getRuleAPI().executeAtScript("aadmina"));

			EngineDebug.info("---date---");
			assertTrue(SDK.getRuleAPI().executeAtScript("@date").equals(SDK.getRuleAPI().executeAtScript("@year-@month-@dayOfMonth")));
			EngineDebug.info("@date -> " + SDK.getRuleAPI().executeAtScript("@date"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@datetime").equals(SDK.getRuleAPI().executeAtScript("@year-@month-@dayOfMonth @time")));
			EngineDebug.info("@datetime -> " + SDK.getRuleAPI().executeAtScript("@datetime"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@time(2014-07-14 12:30:30)").equals("12:30:30"));
			EngineDebug.info("@time -> " + SDK.getRuleAPI().executeAtScript("@time"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@hour(@datetime)").equals(SDK.getRuleAPI().executeAtScript("@hour")));
			EngineDebug.info("@hour -> " + SDK.getRuleAPI().executeAtScript("@hour"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@minute(@datetime)").equals(SDK.getRuleAPI().executeAtScript("@minute")));
			EngineDebug.info("@minute -> " + SDK.getRuleAPI().executeAtScript("@minute"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@second(2014-07-14 12:30:30)").equals("30"));
			EngineDebug.info("@second -> " + SDK.getRuleAPI().executeAtScript("@second"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@nextDate(2015-12-31)").equals("2016-01-01"));
			EngineDebug.info("@nextDate -> " + SDK.getRuleAPI().executeAtScript("@nextDate"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@preDate(2015-12-31)").equals("2015-12-30"));
			EngineDebug.info("@preDate -> " + SDK.getRuleAPI().executeAtScript("@preDate"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@year(2015-12-31)").equals("2015"));
			EngineDebug.info("@year -> " + SDK.getRuleAPI().executeAtScript("@year"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@month(2015-07-31)").equals("7"));
			EngineDebug.info("@month -> " + SDK.getRuleAPI().executeAtScript("@month"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@monthBegin(2014-09-18)").equals("2014-09-01"));
			EngineDebug.info("@monthBegin -> " + SDK.getRuleAPI().executeAtScript("@monthBegin"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@monthEnd(2014-09-18)").equals("2014-09-30"));
			EngineDebug.info("@monthEnd -> " + SDK.getRuleAPI().executeAtScript("@monthEnd"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@weekDay(2014-07-14)").equals("2"));
			EngineDebug.info("@weekDay -> " + SDK.getRuleAPI().executeAtScript("@weekDay"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@weekDay(2014-07-14)").equals("2"));
			EngineDebug.info("@weekDay -> " + SDK.getRuleAPI().executeAtScript("@weekDay"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@dayOfMonth(2014-07-14)").equals("14"));
			EngineDebug.info("@dayOfMonth -> " + SDK.getRuleAPI().executeAtScript("@dayOfMonth"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@quarter(2014-07-14)").equals("3"));
			EngineDebug.info("@quarter -> " + SDK.getRuleAPI().executeAtScript("@quarter"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@quarterBegin(2014-07-14)").equals("2014-07-01"));
			EngineDebug.info("@quarterBegin -> " + SDK.getRuleAPI().executeAtScript("@quarterBegin"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@quarterEnd(2014-07-14)").equals("2014-09-30"));
			EngineDebug.info("@quarterEnd -> " + SDK.getRuleAPI().executeAtScript("@quarterEnd"));
			EngineDebug.info("@timestemp -> " + SDK.getRuleAPI().executeAtScript("@timestemp"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@dateAdd(day,-100,2014-07-14)").equals("2014-04-05"));
			EngineDebug.info("@dateAdd -> " + SDK.getRuleAPI().executeAtScript("@dateAdd(day,-100,2014-07-14)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@date2Chinese(2014-08-18)").equals("二零一四年八月十八日"));
			EngineDebug.info("@date2Chinese -> " + SDK.getRuleAPI().executeAtScript("@date2Chinese(2014-08-18)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@if(@isLeapYear(2014-08-18),是闰年,不是闰年)").equals("不是闰年"));
			EngineDebug.info("@isLeapYear -> " + SDK.getRuleAPI().executeAtScript("@isLeapYear"));

			EngineDebug.info("---String---");
			assertTrue(SDK.getRuleAPI().executeAtScript("@len(abcd中文字符)").equals("8"));
			EngineDebug.info("@len -> " + SDK.getRuleAPI().executeAtScript("@len(abcd中文字符)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@lower(abCD123中文字符)").equals("abcd123中文字符"));
			EngineDebug.info("@lower -> " + SDK.getRuleAPI().executeAtScript("@lower(abCD123中文字符)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@upper(abCD123中文字符)").equals("ABCD123中文字符"));
			EngineDebug.info("@upper -> " + SDK.getRuleAPI().executeAtScript("@upper(abCD123中文字符)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@trim(  去掉前后空格    )").equals("去掉前后空格"));
			EngineDebug.info("@trim -> " + SDK.getRuleAPI().executeAtScript("@trim(  去掉前后空格    )"));
			EngineDebug.info("@rmb -> " + SDK.getRuleAPI().executeAtScript("@rmb(123456789.32)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@substring(阿根廷德国,3,5)").equals("德国"));
			EngineDebug.info("@substring -> " + SDK.getRuleAPI().executeAtScript("@substring(阿根廷德国,3,5)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@indexOf(AWS PaaS,PaaS)").equals("4"));
			EngineDebug.info("@indexOf -> " + SDK.getRuleAPI().executeAtScript("@indexOf(AWS PaaS,PaaS)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@replace(AWS PaaS,PaaS,PaaS 6)").equals("AWS PaaS 6"));
			EngineDebug.info("@replace -> " + SDK.getRuleAPI().executeAtScript("@replace(AWS PaaS,PaaS,PaaS 6)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@phonetic(商学院)").equals("SXY"));
			EngineDebug.info("@phonetic -> " + SDK.getRuleAPI().executeAtScript("@phonetic(商学院)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@lpad(7,3)").equals("007"));
			EngineDebug.info("@lpad -> " + SDK.getRuleAPI().executeAtScript("我是@lpad(7,3)，一个7位定长串@lpad(99,7,*)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@rpad(7,3)").equals("700"));
			EngineDebug.info("@rpad -> " + SDK.getRuleAPI().executeAtScript("我是@rpad(7,3)，一个7位定长串@rpad(99,7,*)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@encode(BPM业务流程管理)").equals("QlBN5Lia5Yqh5rWB56iL566h55CG"));
			EngineDebug.info("@encode -> " + SDK.getRuleAPI().executeAtScript("@encode(BPM业务流程管理)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@decode(QlBN5Lia5Yqh5rWB56iL566h55CG)").equals("BPM业务流程管理"));
			EngineDebug.info("@decode -> " + SDK.getRuleAPI().executeAtScript("@decode(QlBN5Lia5Yqh5rWB56iL566h55CG)"));

			EngineDebug.info("@UrlEncode -> " + SDK.getRuleAPI().executeAtScript("@UrlEncode(BPM业务流程管理)"));
			EngineDebug.info("@UrlEncode -> " + SDK.getRuleAPI().executeAtScript("@UrlEncode(BPM业务流程管理,gbk)"));
			String urlStr = SDK.getRuleAPI().executeAtScript("@UrlEncode(BPM业务流程管理)");
			EngineDebug.info("@urlDecode -> " + SDK.getRuleAPI().executeAtScript("@urlDecode(" + urlStr + ")"));

			assertTrue(SDK.getRuleAPI().executeAtScript("@md5(BPM业务流程管理)").equals("69dbece80c9db26c35be1d39a3bc4c95"));
			EngineDebug.info("@md5 -> " + SDK.getRuleAPI().executeAtScript("@md5(BPM业务流程管理)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@sha256(BPM业务流程管理,aaaaabbbbb)").equals("39B74F0F1398784C410BDD76A0A5CD1AE1D9359F4DB90A747A1FAE698EFA6D5B"));
			EngineDebug.info("@sha256 -> " + SDK.getRuleAPI().executeAtScript("@sha256(BPM业务流程管理,aaaaabbbbb)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@ascii(BPM业务流程管理)").equals("66 80 77 19994 21153 27969 31243 31649 29702"));
			EngineDebug.info("@ascii -> " + SDK.getRuleAPI().executeAtScript("@ascii(BPM业务流程管理)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@loadFile(com.actionsoft.apps.poc.api,/script/rule1.txt)").equals("这是AWS BPM PaaS 6测试脚本"));
			EngineDebug.info("@loadFile -> " + SDK.getRuleAPI().executeAtScript("@loadFile(com.actionsoft.apps.poc.api,/script/rule1.txt)"));

			EngineDebug.info("---Number---");
			assertTrue(SDK.getRuleAPI().executeAtScript("@numAdd(1000000000,9999999999,3.1415926)").equals("11000000002.1415926"));
			EngineDebug.info("@numAdd -> " + SDK.getRuleAPI().executeAtScript("@numAdd(1000000000,9999999999,3.1415926)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@numSub(1000000000,9999999999,3.1415926)").equals("-9000000002.1415926"));
			EngineDebug.info("@numSub -> " + SDK.getRuleAPI().executeAtScript("@numSub(1000000000,9999999999,3.1415926)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@numMul(9999999999,3.1415926)").equals("31415925996.8584074"));
			EngineDebug.info("@numMul -> " + SDK.getRuleAPI().executeAtScript("@numMul(9999999999,3.1415926)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@numDiv(9999999999,3.1415926,3)").equals("3183098915.817"));
			EngineDebug.info("@numDiv -> " + SDK.getRuleAPI().executeAtScript("@numDiv(9999999999,3.1415926,3)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@round(3.1415926,3)").equals("3.142"));
			EngineDebug.info("@round -> " + SDK.getRuleAPI().executeAtScript("@round(3.1415926,3)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@calc(((10+2)/2)+19.5)").equals("25.5"));
			EngineDebug.info("@calc -> " + SDK.getRuleAPI().executeAtScript("@calc(((10+2)/2)+19.5)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@abs(-99.99)").equals("99.99"));
			EngineDebug.info("@abs -> " + SDK.getRuleAPI().executeAtScript("@abs(-99.99)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@mod(198,256)").equals("198"));
			EngineDebug.info("@mod -> " + SDK.getRuleAPI().executeAtScript("@mod(198,256)"));

			EngineDebug.info("---logical---");
			assertTrue(SDK.getRuleAPI().executeAtScript("@equals(A,a)").equals("FALSE"));
			EngineDebug.info("@equals -> " + SDK.getRuleAPI().executeAtScript("@equals(A,a)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@lessThan(100,99)").equals("FALSE"));
			EngineDebug.info("@lessThan -> " + SDK.getRuleAPI().executeAtScript("@lessThan(100,99)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@max(1,2,3,4,5)").equals("5"));
			EngineDebug.info("@max -> " + SDK.getRuleAPI().executeAtScript("@max(1,2,3,4,5)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@min(1,2,-3,4,5)").equals("-3"));
			EngineDebug.info("@min -> " + SDK.getRuleAPI().executeAtScript("@min(1,2,-3,4,5)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@and(@equals(18,18),@equals(在校,在校))").equals("TRUE"));
			EngineDebug.info("@and -> " + SDK.getRuleAPI().executeAtScript("@and(@equals(18,18),@equals(在校,在校))"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@or(@equals(18,16),@equals(在校,在校))").equals("TRUE"));
			EngineDebug.info("@or -> " + SDK.getRuleAPI().executeAtScript("@or(@equals(18,16),@equals(在校,在校))"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@if(true,身份证,毕业证)").equals("身份证"));
			EngineDebug.info("@if -> " + SDK.getRuleAPI().executeAtScript("@if(true,身份证,毕业证)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@in(Tom,smith,jack,tom)").equals("FALSE"));
			EngineDebug.info("@in -> " + SDK.getRuleAPI().executeAtScript("@in(Tom,smith,jack,tom)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@in(Tom,smith,jack,tom)").equals("FALSE"));
			EngineDebug.info("@nullValue -> " + SDK.getRuleAPI().executeAtScript("@nullValue(,转换空值)"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@execJavaBean(com.actionsoft.apps.poc.api,com.actionsoft.apps.poc.api.local.app.at.AtAPITest,处理Id=99的业务)").equals("处理Id=99的业务-ok"));
			EngineDebug.info("@execJavaBean -> ok");

			EngineDebug.info("---ORG---");
			UserContext userContext = DispatcherRequest.getUserContext();
			assertTrue(SDK.getRuleAPI().executeAtScript("@userName(admin<管理员> admin<管理员>)").equals("管理员 管理员"));
			EngineDebug.info("@userName -> " + SDK.getRuleAPI().executeAtScript("@userName", userContext));
			assertTrue(SDK.getRuleAPI().executeAtScript("@uid(admin<管理员> admin<管理员>)").equals("admin admin"));
			EngineDebug.info("@uid -> " + SDK.getRuleAPI().executeAtScript("@uid", userContext));
			assertTrue(SDK.getRuleAPI().executeAtScript("@userUniqueId(admin<管理员>)").equals(userContext.getUserModel().getUniqueId()));
			EngineDebug.info("@userUniqueId -> " + SDK.getRuleAPI().executeAtScript("@userUniqueId", userContext));
			EngineDebug.info("@userNo -> " + SDK.getRuleAPI().executeAtScript("@userNo", userContext));
			EngineDebug.info("@userPositionNo -> " + SDK.getRuleAPI().executeAtScript("@userPositionNo", userContext));
			EngineDebug.info("@userPositionName -> " + SDK.getRuleAPI().executeAtScript("@userPositionName", userContext));
			EngineDebug.info("@userPositionLayer -> " + SDK.getRuleAPI().executeAtScript("@userPositionLayer", userContext));
			EngineDebug.info("@userTel -> " + SDK.getRuleAPI().executeAtScript("@userTel", userContext));
			EngineDebug.info("@userEmail -> " + SDK.getRuleAPI().executeAtScript("@userEmail", userContext));
			EngineDebug.info("@userExt1 -> " + SDK.getRuleAPI().executeAtScript("@userExt1", userContext));
			EngineDebug.info("@userExt2 -> " + SDK.getRuleAPI().executeAtScript("@userExt2", userContext));
			EngineDebug.info("@userExt3 -> " + SDK.getRuleAPI().executeAtScript("@userExt3", userContext));
			EngineDebug.info("@userExt4 -> " + SDK.getRuleAPI().executeAtScript("@userExt4", userContext));
			EngineDebug.info("@userExt5 -> " + SDK.getRuleAPI().executeAtScript("@userExt5", userContext));
			EngineDebug.info("@userBOExt -> " + SDK.getRuleAPI().executeAtScript("@userBOExt(ID)", userContext));
			assertTrue(SDK.getRuleAPI().executeAtScript("@companyName", userContext).length() > 0);
			// process context
			String startUser = "admin";
			String businessKey = "AT-" + System.currentTimeMillis();
			ProcessInstance processInst = processAPI.createProcessInstance("obj_93ce12b45c89406a9c6fd89c69924502", businessKey, startUser, "", "", "At公式测试", null);
			TaskInstance taskInst = processAPI.start(processInst, null).fetchActiveTasks().get(0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@companyName(process," + processInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@companyName(process)", userContext, processInst, taskInst, null).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@companyName(taskOwner," + taskInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@companyName(taskOwner)", userContext, processInst, taskInst, null).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@companyName(taskTarget," + taskInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@companyName(taskTarget)", userContext, processInst, taskInst, null).length() > 0);
			EngineDebug.info("@company*(contextType,contextId) -> ok");
			EngineDebug.info("@companyName -> " + SDK.getRuleAPI().executeAtScript("@companyName", userContext));
			EngineDebug.info("@companyId -> " + SDK.getRuleAPI().executeAtScript("@companyId", userContext));
			EngineDebug.info("@companyExt1 -> " + SDK.getRuleAPI().executeAtScript("@companyExt1", userContext));
			EngineDebug.info("@companyExt2 -> " + SDK.getRuleAPI().executeAtScript("@companyExt2", userContext));
			EngineDebug.info("@companyBOExt -> " + SDK.getRuleAPI().executeAtScript("@companyBOExt(ID)", userContext));
			assertTrue(SDK.getRuleAPI().executeAtScript("@departmentName", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@departmentName(process," + processInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@departmentName(process)", userContext, processInst, taskInst, null).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@departmentName(taskOwner," + taskInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@departmentName(taskOwner)", userContext, processInst, taskInst, null).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@departmentName(taskTarget," + taskInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@departmentName(taskTarget)", userContext, processInst, taskInst, null).length() > 0);
			EngineDebug.info("@department*(contextType,contextId) -> ok");
			EngineDebug.info("@departmentName -> " + SDK.getRuleAPI().executeAtScript("@departmentName", userContext));
			EngineDebug.info("@departmentPathName -> " + SDK.getRuleAPI().executeAtScript("@departmentPathName", userContext));
			EngineDebug.info("@departmentId -> " + SDK.getRuleAPI().executeAtScript("@departmentId", userContext));
			EngineDebug.info("@departmentPathId -> " + SDK.getRuleAPI().executeAtScript("@departmentPathId", userContext));
			EngineDebug.info("@departmentNo -> " + SDK.getRuleAPI().executeAtScript("@departmentNo", userContext));
			EngineDebug.info("@departmentZone -> " + SDK.getRuleAPI().executeAtScript("@departmentZone", userContext));
			EngineDebug.info("@departmentManager -> " + SDK.getRuleAPI().executeAtScript("@departmentManager", userContext));
			EngineDebug.info("@isDepartmentManager -> " + SDK.getRuleAPI().executeAtScript("@isDepartmentManager", userContext));
			EngineDebug.info("@departmentExt1 -> " + SDK.getRuleAPI().executeAtScript("@departmentExt1", userContext));
			EngineDebug.info("@departmentExt2 -> " + SDK.getRuleAPI().executeAtScript("@departmentExt2", userContext));
			EngineDebug.info("@departmentBOExt -> " + SDK.getRuleAPI().executeAtScript("@departmentBOExt(ID)", userContext));
			assertTrue(SDK.getRuleAPI().executeAtScript("@roleName", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@roleName(process," + processInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@roleName(process)", userContext, processInst, taskInst, null).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@roleName(taskOwner," + taskInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@roleName(taskOwner)", userContext, processInst, taskInst, null).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@roleName(taskTarget," + taskInst.getId() + ")", userContext).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@roleName(taskTarget)", userContext, processInst, taskInst, null).length() > 0);
			EngineDebug.info("@role*(contextType,contextId) -> ok");
			EngineDebug.info("@roleName -> " + SDK.getRuleAPI().executeAtScript("@roleName", userContext));
			EngineDebug.info("@roleNameKey -> " + SDK.getRuleAPI().executeAtScript("@roleNameKey", userContext));
			EngineDebug.info("@roleBOExt -> " + SDK.getRuleAPI().executeAtScript("@roleBOExt(ID)", userContext));

			EngineDebug.info("---Process---");
			assertTrue(SDK.getRuleAPI().executeAtScript("@processTitle", userContext, processInst, taskInst, null).equals("At公式测试"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@processTitle(id," + processInst.getId() + ")").equals("At公式测试"));
			assertTrue(SDK.getRuleAPI().executeAtScript("@processTitle(businessKey," + businessKey + ")").equals("At公式测试"));
			EngineDebug.info("@processRuntime*(contextType,contextId) -> ok");
			EngineDebug.info("@processTitle -> " + SDK.getRuleAPI().executeAtScript("@processTitle", userContext, processInst, taskInst, null));
			assertTrue(SDK.getRuleAPI().executeAtScript("@processId", userContext, processInst, taskInst, null).equals(processInst.getId()));
			assertTrue(SDK.getRuleAPI().executeAtScript("@processId(" + businessKey + ")").equals(processInst.getId()));
			EngineDebug.info("@processId -> " + SDK.getRuleAPI().executeAtScript("@processId", userContext, processInst, taskInst, null));
			EngineDebug.info("@processParentId -> " + SDK.getRuleAPI().executeAtScript("@processParentId", userContext, processInst, taskInst, null));
			EngineDebug.info("@processParentTaskId -> " + SDK.getRuleAPI().executeAtScript("@processParentTaskId", userContext, processInst, taskInst, null));
			EngineDebug.info("@processCreateUser -> " + SDK.getRuleAPI().executeAtScript("@processCreateUser", userContext, processInst, taskInst, null));
			EngineDebug.info("@processCreateTime -> " + SDK.getRuleAPI().executeAtScript("@processCreateTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@processStartTime -> " + SDK.getRuleAPI().executeAtScript("@processStartTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@processEndTime -> " + SDK.getRuleAPI().executeAtScript("@processEndTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@processStatus -> " + SDK.getRuleAPI().executeAtScript("@processStatus", userContext, processInst, taskInst, null));
			EngineDebug.info("@processBusinessKey -> " + SDK.getRuleAPI().executeAtScript("@processBusinessKey", userContext, processInst, taskInst, null));
			EngineDebug.info("@isProcessEnd -> " + SDK.getRuleAPI().executeAtScript("@isProcessEnd", userContext, processInst, taskInst, null));
			EngineDebug.info("@isSubProcess -> " + SDK.getRuleAPI().executeAtScript("@isSubProcess", userContext, processInst, taskInst, null));
			EngineDebug.info("@processCostTime -> " + SDK.getRuleAPI().executeAtScript("@processCostTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@processExpireTime -> " + SDK.getRuleAPI().executeAtScript("@processExpireTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@processExt1 -> " + SDK.getRuleAPI().executeAtScript("@processExt1", userContext, processInst, taskInst, null));
			EngineDebug.info("@processExt2 -> " + SDK.getRuleAPI().executeAtScript("@processExt2", userContext, processInst, taskInst, null));
			EngineDebug.info("@processExt3 -> " + SDK.getRuleAPI().executeAtScript("@processExt3", userContext, processInst, taskInst, null));
			EngineDebug.info("@processVar -> " + SDK.getRuleAPI().executeAtScript("@processVar(P1)", userContext, processInst, taskInst, null));
			assertTrue(SDK.getRuleAPI().executeAtScript("@processDefName", userContext, processInst, taskInst, null).length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@processDefName(id," + processInst.getId() + ")").length() > 0);
			assertTrue(SDK.getRuleAPI().executeAtScript("@processDefName(businessKey," + businessKey + ")").length() > 0);
			EngineDebug.info("@processDef*(contextType,contextId) -> ok");
			EngineDebug.info("@processDefId -> " + SDK.getRuleAPI().executeAtScript("@processDefId", userContext, processInst, taskInst, null));
			EngineDebug.info("@processDefName -> " + SDK.getRuleAPI().executeAtScript("@processDefName", userContext, processInst, taskInst, null));
			EngineDebug.info("@processDefVersionId -> " + SDK.getRuleAPI().executeAtScript("@processDefVersionId", userContext, processInst, taskInst, null));
			EngineDebug.info("@processDefGroupId -> " + SDK.getRuleAPI().executeAtScript("@processDefGroupId", userContext, processInst, taskInst, null));
			EngineDebug.info("@processDefGroupName -> " + SDK.getRuleAPI().executeAtScript("@processDefGroupName", userContext, processInst, taskInst, null));
			EngineDebug.info("@processDefAppId -> " + SDK.getRuleAPI().executeAtScript("@processDefAppId", userContext, processInst, taskInst, null));
			EngineDebug.info("@processDefDurationTime -> " + SDK.getRuleAPI().executeAtScript("@processDefDurationTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@processDefWarningTime -> " + SDK.getRuleAPI().executeAtScript("@processDefWarningTime", userContext, processInst, taskInst, null));
			assertTrue(SDK.getRuleAPI().executeAtScript("@taskTitle", userContext, processInst, taskInst, null).equals(taskInst.getTitle()));
			assertTrue(SDK.getRuleAPI().executeAtScript("@taskTitle(" + taskInst.getId() + ")").equals(taskInst.getTitle()));
			EngineDebug.info("@taskRuntime*(taskInstId) -> ok");
			EngineDebug.info("@taskId -> " + SDK.getRuleAPI().executeAtScript("@taskId", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskTitle -> " + SDK.getRuleAPI().executeAtScript("@taskTitle", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskOwner -> " + SDK.getRuleAPI().executeAtScript("@taskOwner", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskTarget -> " + SDK.getRuleAPI().executeAtScript("@taskTarget", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskPreTaskId -> " + SDK.getRuleAPI().executeAtScript("@taskPreTaskId", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskPreHumanTaskId -> " + SDK.getRuleAPI().executeAtScript("@taskPreHumanTaskId", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskPriority -> " + SDK.getRuleAPI().executeAtScript("@taskPriority", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskStatus -> " + SDK.getRuleAPI().executeAtScript("@taskStatus", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskHumanType -> " + SDK.getRuleAPI().executeAtScript("@taskHumanType", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskDueTime -> " + SDK.getRuleAPI().executeAtScript("@taskDueTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskBeginTime -> " + SDK.getRuleAPI().executeAtScript("@taskBeginTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskReadTime -> " + SDK.getRuleAPI().executeAtScript("@taskReadTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskEndTime -> " + SDK.getRuleAPI().executeAtScript("@taskEndTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskCostTime -> " + SDK.getRuleAPI().executeAtScript("@taskCostTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskExpireTime -> " + SDK.getRuleAPI().executeAtScript("@taskExpireTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskExt1 -> " + SDK.getRuleAPI().executeAtScript("@taskExt1", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskExt2 -> " + SDK.getRuleAPI().executeAtScript("@taskExt2", userContext, processInst, taskInst, null));
			EngineDebug.info("@taskExt3 -> " + SDK.getRuleAPI().executeAtScript("@taskExt3", userContext, processInst, taskInst, null));
			EngineDebug.info("@isTaskEnd -> " + SDK.getRuleAPI().executeAtScript("@isTaskEnd", userContext, processInst, taskInst, null));
			EngineDebug.info("@activityDefId -> " + SDK.getRuleAPI().executeAtScript("@activityDefId", userContext, processInst, taskInst, null));
			EngineDebug.info("@activityDefNo -> " + SDK.getRuleAPI().executeAtScript("@activityDefNo", userContext, processInst, taskInst, null));
			EngineDebug.info("@activityDefName -> " + SDK.getRuleAPI().executeAtScript("@activityDefName", userContext, processInst, taskInst, null));
			EngineDebug.info("@activityDefDurationTime -> " + SDK.getRuleAPI().executeAtScript("@activityDefDurationTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@activityDefWarningTime -> " + SDK.getRuleAPI().executeAtScript("@activityDefWarningTime", userContext, processInst, taskInst, null));
			EngineDebug.info("@activityDefExt -> " + SDK.getRuleAPI().executeAtScript("@activityDefExt", userContext, processInst, taskInst, null));

			EngineDebug.info("---Sequence---");
			String sequenceKey = "APITest";
			EngineDebug.info("@sequence -> " + SDK.getRuleAPI().executeAtScript("@sequence", userContext, processInst, taskInst, null));
			EngineDebug.info("@sequence -> " + SDK.getRuleAPI().executeAtScript("@sequence(,10,*)", userContext, processInst, taskInst, null));
			assertTrue(SDK.getRuleAPI().executeAtScript("@sequenceYear", userContext, processInst, taskInst, null).length() == 5);
			EngineDebug.info("@sequenceYear -> " + SDK.getRuleAPI().executeAtScript("@sequenceYear", userContext, processInst, taskInst, null));
			EngineDebug.info("@sequenceYear -> " + SDK.getRuleAPI().executeAtScript("@sequenceYear(APITest,10,0)", userContext, processInst, taskInst, null));
			EngineDebug.info("@sequenceMonth -> " + SDK.getRuleAPI().executeAtScript("@sequenceMonth(APITest,10,0)", userContext, processInst, taskInst, null));
			EngineDebug.info("@sequenceMonth -> " + SDK.getRuleAPI().executeAtScript("@sequenceMonth(,10,0)", userContext, processInst, taskInst, null));
			String year = UtilDate.yearFormat(new Date());
			String month = UtilDate.monthFormat(new Date());
			// remove test key
			Sequence.remove("AT_" + sequenceKey);
			Sequence.remove("AT_" + sequenceKey + year);
			Sequence.remove("AT_" + sequenceKey + year + month);
			Sequence.remove("AT_" + processInst.getProcessGroupId());
			Sequence.remove("AT_" + processInst.getProcessGroupId() + year);
			Sequence.remove("AT_" + processInst.getProcessGroupId() + year + month);

			EngineDebug.info("---db---");
			assertTrue(SDK.getRuleAPI().executeAtScript("@sqlValue(SELECT USERID FROM ORGUSER WHERE USERID='admin')").equals("admin"));
			EngineDebug.info("@sqlValue -> " + SDK.getRuleAPI().executeAtScript("@sqlValue(SELECT USERID FROM ORGUSER WHERE USERID='admin')"));
			EngineDebug.info("@sqlSet -> " + SDK.getRuleAPI().executeAtScript("@sqlSet(SELECT COMPANYNAME FROM ORGCOMPANY)"));
			EngineDebug.info("@sqlClauseOfManager -> " + SDK.getRuleAPI().executeAtScript("@sqlClauseOfManager(DEPTID)", userContext));
			EngineDebug.info("@sqlClauseOfManager -> " + SDK.getRuleAPI().executeAtScript("@sqlClauseOfManager(DEPTID,true)", userContext));

			EngineDebug.info("---system---");
			EngineDebug.info("@sid -> " + SDK.getRuleAPI().executeAtScript("@sid", userContext));
			EngineDebug.info("@lang -> " + SDK.getRuleAPI().executeAtScript("@lang", userContext));
			EngineDebug.info("@i18n -> " + SDK.getRuleAPI().executeAtScript("@i18n(info1,com.actionsoft.apps.poc.api)", userContext));
			EngineDebug.info("@i18n -> " + SDK.getRuleAPI().executeAtScript("@i18n(info1,com.actionsoft.apps.poc.api,cn)"));
			EngineDebug.info("@i18n -> " + SDK.getRuleAPI().executeAtScript("@i18n(info1,com.actionsoft.apps.poc.api,en)"));
			EngineDebug.info("@deviceType -> " + SDK.getRuleAPI().executeAtScript("@deviceType", userContext));
			EngineDebug.info("@clientIp -> " + SDK.getRuleAPI().executeAtScript("@clientIp", userContext));
			EngineDebug.info("@portalUrl -> " + SDK.getRuleAPI().executeAtScript("@portalUrl"));
			EngineDebug.info("@serverInstance -> " + SDK.getRuleAPI().executeAtScript("@serverInstance"));
			EngineDebug.info("@serverProperty -> " + SDK.getRuleAPI().executeAtScript("@serverProperty(dc.path)"));

		} catch (Exception e) {
			EngineDebug.err(e.toString());
			fail(e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 测试JavaBean
	 * 
	 * @param param 由实施人员在公式内提供的扩展参数
	 * @param atContext 上下文对象
	 * @return 处理后的结果
	 */
	public String exec(String param, ExpressionContext atContext) {
		EngineDebug.info("exec -> begin ");
		EngineDebug.info("exec -> param = " + param);
		return param + "-ok";
	}
}
