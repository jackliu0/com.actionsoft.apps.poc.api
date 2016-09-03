/**
 * 
 */
package com.actionsoft.apps.poc.api.local.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionsoft.bpms.commons.database.RowMapper;
import com.actionsoft.bpms.commons.mvc.dao.DaoObject;
import com.actionsoft.bpms.util.DBSql;
import com.actionsoft.bpms.util.UUIDGener;
import com.actionsoft.bpms.util.UtilString;
import com.actionsoft.exception.AWSDataAccessException;

/**
 * 示例Dao
 */
public class TestDao extends DaoObject<TestModel> {

	/**
	 * 构造
	 */
	public TestDao() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 插入一条测试记录
	 */
	@Override
	public int insert(TestModel model) throws AWSDataAccessException {
		model.setId(UUIDGener.getUUID());
		String sql = "INSERT INTO " + entityName() + "(ID,F1,F2)VALUES(:ID,:F1,:F2)";
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("ID", model.getId());
		paraMap.put("F1", model.getF1());
		paraMap.put("F2", model.getF2());
		return DBSql.update(sql, paraMap);
	}

	/**
	 * 更新一条测试记录
	 */
	@Override
	public int update(TestModel model) throws AWSDataAccessException {
		if (UtilString.isEmpty(model.getId())) {
			throw new AWSDataAccessException("Method getId() Does Not Allow Empty!");
		}
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("F1", model.getF1());
		paraMap.put("F2", model.getF2());
		// 不需要写sql，可调用基类封装的update方法
		return update(model.getId(), paraMap);
	}

	/**
	 * 封装测试
	 * 
	 * @param id
	 * @param f2
	 * @return
	 */
	public int updateF2(String id, double f2) throws AWSDataAccessException {
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("F2", f2);
		return update(id, paraMap);
	}

	/**
	 * 封装测试
	 * 
	 * @return
	 */
	public long count() {
		return DBSql.getLong("SELECT COUNT(ID) AS C FROM " + entityName(), "C");
	}

	/**
	 * 封装测试
	 * 
	 * @param f2
	 * @return
	 */
	public List<TestModel> queryByF1(String f1) {
		return query("F1=?", f1).orderBy("F2").desc().list();
	}

	/**
	 * 该Dao实现的表名称
	 */
	@Override
	public String entityName() {
		return "TEST_DAO";
	}

	/**
	 * 构建该Dao从一条记录转换成对象的映射对象
	 */
	@Override
	public RowMapper<TestModel> rowMapper() {
		return new Mapper();
	}

	/**
	 * TestDao Mapper
	 */
	private class Mapper implements RowMapper<TestModel> {
		public TestModel mapRow(ResultSet rset, int rowNum) throws SQLException {
			TestModel model = new TestModel();
			try {
				model.setId(rset.getString("ID"));
				model.setF1(rset.getString("F1"));
				model.setF2(rset.getDouble("F2"));
				model.setOrderIndex(rset.getInt("ORDERINDEX"));
				model.setRefreshTime(System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return model;
		}

	}

}
