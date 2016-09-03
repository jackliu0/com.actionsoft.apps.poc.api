package com.actionsoft.apps.poc.api.local.process.delegation;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.commons.security.delegation.model.DelegationModel;
import com.actionsoft.bpms.commons.security.delegation.model.DelegationScopeModel;
import com.actionsoft.bpms.org.cache.UserCache;
import com.actionsoft.bpms.org.dao.OrgDaoFactory;
import com.actionsoft.bpms.org.model.UserModel;
import com.actionsoft.bpms.org.model.impl.UserModelImpl;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.sdk.local.SDK;
import com.actionsoft.sdk.local.api.ProcessAPI;
import com.actionsoft.sdk.local.api.TaskAPI;

public class QueryDelegationWorklistTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Ad-Hoc即席处理/Delegation委托代理/测试任务委托和代理";
	}

	public String getProcessDefId() {
		return "obj_5af09aa0c46345e4951ca3e1cda29a2c";
	}

	public boolean execute(Map<String, Object> params) {
		ProcessAPI processAPI = SDK.getProcessAPI();
		TaskAPI taskAPI = SDK.getTaskAPI();
		String startUser = "admin";
		String delegateUser = "delegateAbc";
		String delegateUser2 = "delegateAbc2";
		DelegationModel delegationModel = null;
		try {
			// 创建临时测试账户
			assertNotNull(createDelegateUser(delegateUser));
			assertNotNull(createDelegateUser(delegateUser2));
			// 创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "OrderProcess-" + System.currentTimeMillis(), null);
			// 启动流程
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(startUser).count() == 1);
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().count() == 0);
			Thread.sleep(1000);// 停顿1秒

			// 为admin创建委托申请单，委托delegateAbc可以处理admin全部任务（任务创建时间=有效期内的）
			delegationModel = new DelegationModel();
			delegationModel.setApplicantUser(startUser);
			delegationModel.setDelegateUser(delegateUser);
			delegationModel.setBeginTime(new Timestamp(System.currentTimeMillis()));
			// 十分钟有效期
			delegationModel.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 10));
			delegationModel.setScopeType("0");// 范围=全部任务
			SDK.getDelegationAPI().create(delegationModel);
			// 刚刚创建给admin的任务创建时间小于begintime，因此应该还是0个任务
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().count() == 0);

			// 为admin创建第2个任务
			TaskInstance task2 = taskAPI.createUserTaskInstance(processInst, tasks.get(0), UserContext.fromUID(startUser), tasks.get(0).getActivityDefId(), startUser, "OrderProcess-888").get(0);
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(startUser).count() == 2);
			// 此时，delegateAbc可以查询到admin的这个任务
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().listPage(0, 100).size() == 1);

			// 删除委托
			SDK.getDelegationAPI().remove(delegationModel.getId());
			// 此时，delegateAbc没有任务了
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().listPage(0, 100).size() == 0);

			// 创建一个指定范围的委托
			delegationModel.setScopeType("1");// 范围=全部任务
			DelegationScopeModel delegationScopeModel1 = new DelegationScopeModel();
			delegationScopeModel1.setResourceId("111111");// 不存在的
			delegationScopeModel1.setResourceType("0");// 类型=流程模型
			delegationModel.getScope().add(delegationScopeModel1);
			DelegationScopeModel delegationScopeModel2 = new DelegationScopeModel();
			delegationScopeModel2.setResourceId("22222");// 不存在的
			delegationScopeModel2.setResourceType("0");// 类型=流程模型
			delegationModel.getScope().add(delegationScopeModel2);
			SDK.getDelegationAPI().create(delegationModel);
			// 此时，delegateAbc可代理的任务范围是admin的流程模型111111和2222222的任务，应该返回0
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().listPage(0, 100).size() == 0);

			DelegationScopeModel delegationScopeModel3 = new DelegationScopeModel();
			delegationScopeModel3.setResourceId(getProcessDefId());
			delegationScopeModel3.setResourceType("0");// 流程模型
			delegationModel.getScope().add(delegationScopeModel3);
			// 此时，delegateAbc可代理的任务范围包含了刚刚创建的任务，应该返回1
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().orderByBeginTime().desc().listPage(0, 100).size() == 1);

			// 改变代理人
			SDK.getDelegationAPI().changeDelegateUser(delegationModel.getId(), delegateUser2);
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().count() == 0);
			SDK.getDelegationAPI().changeDelegateUser(delegationModel.getId(), delegateUser);
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().count() == 1);

			// 改变有效时间，使之失效
			SDK.getDelegationAPI().changeEndtime(delegationModel.getId(), new Timestamp(System.currentTimeMillis() - 1000 * 60 * 10));
			assertTrue(taskAPI.query().processInstId(processInst.getId()).activeTask().target(delegateUser).supportDelegateTask().count() == 0);

			// 由代理人执行代理的任务
			taskAPI.completeTask(task2, UserContext.fromUID(delegateUser), false);
			// 从归档记录判断delegateUser等于'delegateAbc'
			assertTrue(taskAPI.queryHistory().detailById(task2.getId()).getDelegateUser().equals(delegateUser));

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
			return false;
		} finally {
			// 删除临时账户
			removeDelegateUser(delegateUser);
			removeDelegateUser(delegateUser2);
			// 删除测试的委托申请
			if (delegationModel != null) {
				try {
					SDK.getDelegationAPI().remove(delegationModel.getId());
				} catch (Exception e) {
				}
			}
		}
	}

	// 创建一个临时测试账户
	private UserModel createDelegateUser(String userId) {
		UserModelImpl admin = (UserModelImpl) UserCache.getModel("admin");
		UserModelImpl user = (UserModelImpl) UserCache.getModel(userId);
		if (user != null)
			return user;
		user = new UserModelImpl();
		user.setUID(userId);
		user.setDepartmentId(admin.getDepartmentId());
		user.setUserName("代理人X账户");
		user.setPassword("123456");
		user.setRoleId(admin.getRoleId());
		int r = OrgDaoFactory.createUser().insert(user);
		if (r > 0)
			return user;
		else
			return null;
	}

	// 删除临时测试账户
	private void removeDelegateUser(String userId) {
		try {
			UserModel user = UserCache.getModel(userId);
			if (user != null)
				OrgDaoFactory.createUser().delete(user.getUniqueId());
		} catch (Exception e) {

		}
	}
}
