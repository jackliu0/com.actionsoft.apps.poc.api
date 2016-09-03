package com.actionsoft.apps.poc.api.local.app.dao;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.server.conf.server.AWSServerConf;
import com.actionsoft.bpms.util.DBSql;

public class DaoTest extends LocalAPITestCase {

	public String getTestCaseName() {
		return "App开发/DAO/常规接口测试";
	}

	public boolean execute(Map<String, Object> params) {
		// 删除临时表结构
		try {
			DBSql.update("DROP TABLE TEST_DAO");
		} catch (Exception e) {

		}
		// 创建临时表结构
		int result = 0;
		if (AWSServerConf.isMySQL()) {
			result = DBSql.update("CREATE TABLE TEST_DAO(ID varchar(36) NOT NULL ,F1 varchar(64) ,F2 NUMERIC(16,2) ,ORDERINDEX NUMERIC(4) ,REFRESHTIME NUMERIC(10) ,CONSTRAINT TEST_DAO_ID_PK PRIMARY KEY (ID))");
		} else if (AWSServerConf.isSQLServer()) {
			result = DBSql.update("CREATE TABLE TEST_DAO(ID varchar(36) NOT NULL ,F1 varchar(64) ,F2 NUMERIC(16,2) ,ORDERINDEX NUMERIC(4) ,REFRESHTIME NUMERIC(10) ,CONSTRAINT TEST_DAO_ID_PK PRIMARY KEY (ID))");
		} else if (AWSServerConf.isOracle()) {
			result = DBSql.update("CREATE TABLE TEST_DAO(ID varchar(36) NOT NULL ,F1 varchar(64) ,F2 NUMERIC(16,2) ,ORDERINDEX NUMERIC(4) ,REFRESHTIME NUMERIC(10) ,CONSTRAINT TEST_DAO_ID_PK PRIMARY KEY (ID))");
		} else if (AWSServerConf.isDB2()) {
			result = DBSql.update("CREATE TABLE TEST_DAO(ID varchar(36) NOT NULL ,F1 varchar(64) ,F2 NUMERIC(16,2) ,ORDERINDEX NUMERIC(4) ,REFRESHTIME NUMERIC(10) ,CONSTRAINT TEST_DAO_ID_PK PRIMARY KEY (ID))");
		}
		if (result >= 0) {
			EngineDebug.info("TEST_DAO创建成功!");
		} else {
			EngineDebug.err("TEST_DAO创建失败!");
		}

		TestDao dao = new TestDao();
		try {
			// 插入10条记录
			for (int i = 0; i < 10; i++) {
				TestModel model = new TestModel();
				model.setF1("Value-" + i);
				long times = System.currentTimeMillis();
				model.setF2(times);// 当前时间搓
				EngineDebug.info("insert--->" + dao.insert(model));
				EngineDebug.info("insert--->" + model.toJson());
			}
			// 获得第6条记录
			TestModel model = dao.queryByF1("Value-5").get(0);
			EngineDebug.info("queryByF1--->" + model.toJson());
			// 修改F1值
			model.setF1("NewValue-5");
			EngineDebug.info("queryByF1--->" + dao.update(model));
			// 查询全部，按插入时间倒序
			List<TestModel> listAll = dao.query().orderBy("F2").asc().list();
			if (listAll.size() == 10) {
				assertTrue(true);
			}
			for (TestModel test : listAll) {
				EngineDebug.info("dao.query().list(排序)--->" + test.getF1());
			}
			// 取结果的3条记录，按插入时间倒序
			List<TestModel> listPage = dao.query().orderBy("F2").asc().list(1, 3);
			if (listPage.size() == 3) {
				assertTrue(true);
			}
			for (TestModel test : listPage) {
				EngineDebug.info("dao.query().list(分页)--->" + test.getF1());
			}
			// 按条件查询
			List<TestModel> listCondition = dao.query("F2 > ? AND F1 like ?", 0, "Value-%").orderBy("F2").asc().list();
			if (listCondition.size() == 9) {
				assertTrue(true);
			}
			for (TestModel test : listCondition) {
				EngineDebug.info("dao.query().list(条件查询)--->" + test.getF1());
			}
		} catch (Exception e) {
			e.printStackTrace();
			EngineDebug.err(e.toString());
			assertTrue(false);
		}
		return true;
	}

}
