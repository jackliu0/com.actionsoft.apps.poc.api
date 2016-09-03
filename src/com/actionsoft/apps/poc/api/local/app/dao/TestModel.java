/**
 * 
 */
package com.actionsoft.apps.poc.api.local.app.dao;

import com.actionsoft.bpms.commons.mvc.model.IModelBean;
import com.actionsoft.bpms.commons.mvc.model.ModelBean;

/**
 * 示例Dao，业务实体结构
 * 
 */
public class TestModel extends ModelBean implements IModelBean {

	private String id;
	private String f1;
	private double f2;
	private int orderIndex;
	private long refreshTime;

	/**
	 * 构造
	 */
	public TestModel() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getF1() {
		if (f1 == null) {
			f1 = "";
		}
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public double getF2() {
		return f2;
	}

	public void setF2(double f2) {
		this.f2 = f2;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o instanceof TestModel) {
			return ((TestModel) o).id.equals(id);
		}
		return false;
	}
}
