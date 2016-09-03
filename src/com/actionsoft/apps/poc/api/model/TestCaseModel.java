package com.actionsoft.apps.poc.api.model;

import java.util.List;

/**
 * 测试用例
 * @author ZhangMing
 *
 */
public class TestCaseModel {
	private String className;		//测试用例类名
	private String testCaseName;	//测试用例名称
	private String processDefId;	//测试用例ID
	private String beginTime;		//执行时间时间
	private String endTime;			//执行结束时间
	private int state;				//执行状态 0：未执行；1：执行中；2：执行结束
	private boolean result;			//执行结果 true:执行成功；false：执行失败
	private List<String> log;		//执行日志
	
	public TestCaseModel() {
		super();
	}

	public TestCaseModel(String className) {
		super();
		this.className = className;
		this.state = 0;
	}
	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public List<String> getLog() {
		return log;
	}
	public void setLog(List<String> log) {
		this.log = log;
	}
}
