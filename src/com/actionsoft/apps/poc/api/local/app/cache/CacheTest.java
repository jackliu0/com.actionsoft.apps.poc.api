package com.actionsoft.apps.poc.api.local.app.cache;

import java.util.Iterator;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.LocalAPITestCase;
import com.actionsoft.apps.poc.api.local.app.dao.TestModel;
import com.actionsoft.bpms.bpmn.engine.core.EngineDebug;
import com.actionsoft.bpms.server.conf.server.AWSServerConf;
import com.actionsoft.bpms.util.DBSql;
import com.actionsoft.bpms.util.UUIDGener;

public class CacheTest extends LocalAPITestCase {

	public String getTestCaseName() {
		return "App开发/Cache/常规接口测试";
	}

	public boolean execute(Map<String, Object> params) {
		// 删除临时表结构
		try {
			DBSql.update("DROP TABLE TEST_DAO");
		} catch (Exception e) {
		}
		try {
			// 创建临时表结构
			if (AWSServerConf.isMySQL()) {
				DBSql.update("CREATE TABLE TEST_DAO(ID varchar(36) NOT NULL ,F1 varchar(64) ,F2 NUMERIC(16,2) ,ORDERINDEX NUMERIC(4) ,REFRESHTIME NUMERIC(10) ,CONSTRAINT TEST_DAO_ID_PK PRIMARY KEY (ID))");
			} else if (AWSServerConf.isSQLServer()) {
				DBSql.update("CREATE TABLE TEST_DAO(ID varchar(36) NOT NULL ,F1 varchar(64) ,F2 NUMERIC(16,2) ,ORDERINDEX NUMERIC(4) ,REFRESHTIME NUMERIC(10) ,CONSTRAINT TEST_DAO_ID_PK PRIMARY KEY (ID))");
			} else if (AWSServerConf.isOracle()) {
				DBSql.update("CREATE TABLE TEST_DAO(ID varchar(36) NOT NULL ,F1 varchar(64) ,F2 NUMERIC(16,2) ,ORDERINDEX NUMERIC(4) ,REFRESHTIME NUMERIC(10) ,CONSTRAINT TEST_DAO_ID_PK PRIMARY KEY (ID))");
			} else if (AWSServerConf.isDB2()) {
				DBSql.update("CREATE TABLE TEST_DAO(ID varchar(36) NOT NULL ,F1 varchar(64) ,F2 NUMERIC(16,2) ,ORDERINDEX NUMERIC(4) ,REFRESHTIME NUMERIC(10) ,CONSTRAINT TEST_DAO_ID_PK PRIMARY KEY (ID))");
			}
			EngineDebug.info("init cache dao TEST_DAO创建成功!");
			int size = TestCache.getCache().size();
			EngineDebug.info("test cache init size--->" + size + "...ok");
			String id = UUIDGener.getUUID();
			TestModel model = new TestModel();
			model.setId(id);
			String f1 = "F1_" + size;
			model.setF1(f1);
			TestCache.getCache().put(id, model);
			assertNotNull(TestCache.getModel(id));
			EngineDebug.info("test cache put/get--->...ok");
			assertNotNull(TestCache.getByF1(f1));
			EngineDebug.info("test cache get by index--->...ok");
			assertEquals(size + 1, TestCache.getCache().size());
			EngineDebug.info("test cache size now--->" + (size + 1) + "...ok");

			Iterator<TestModel> it = TestCache.getCache().iterator();
			while (it.hasNext()) {
				TestModel m = it.next();
				EngineDebug.info("cache.iterator--->" + m.toJson() + "...ok");
			}
			TestCache.getCache().remove(id);
			assertNull(TestCache.getModel(id));
			assertNull(TestCache.getByF1(f1));
			EngineDebug.info("test cache remove--->...ok");
			assertEquals(size, TestCache.getCache().size());
			EngineDebug.info("test cache size after remove--->" + size + "...ok");
		} catch (Exception e) {
			e.printStackTrace();
			EngineDebug.err(e.toString());
			assertTrue(false);
		}
		return true;
	}
}
