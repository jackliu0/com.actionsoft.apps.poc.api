package com.actionsoft.apps.poc.api.local.process.query;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.constant.UserTaskDefinitionConst;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.org.cache.TeamCache;
import com.actionsoft.bpms.org.dao.OrgDaoFactory;
import com.actionsoft.bpms.org.model.TeamModel;
import com.actionsoft.bpms.org.model.impl.TeamMemberModelImpl;
import com.actionsoft.bpms.org.model.impl.TeamModelImpl;
import com.actionsoft.bpms.server.UserContext;

public class QueryTaskTest extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Query查询/测试任务查询功能";
	}

	public String getProcessDefId() {
		return "obj_23a61e81a48c406cb75692cd061a9ca7";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {

			// create a new instance
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "SalesProcess-Meta", null);
			// 启动流程,执行到U1
			List<TaskInstance> tasks = processAPI.start(processInst, null).fetchActiveTasks();
			// 创建传阅任务
			List<TaskInstance> ccTasks = taskAPI.createUserCCTaskInstance(processInst, tasks.get(0), UserContext.fromUID(startUser), startUser, "传阅SalesProcess-Meta");
			assertTrue(taskAPI.query().processInstId(processInst.getId()).userTask().userTaskOfWorking().orderByBeginTime().desc().listPage(0, 100).size() == 1);
			assertTrue(taskAPI.query().processInstId(processInst.getId()).userTask().userTaskOfNotification().orderByBeginTime().desc().listPage(0, 100).size() == 1);
			// 创建加签任务
			List<TaskInstance> adHocTasks = taskAPI.createUserAdHocTaskInstance(processInst, tasks.get(0), UserContext.fromUID(startUser), startUser, UserTaskDefinitionConst.ADHOC_TASK_TYPE_2, "SalesProcess-Meta");
			assertNotNull(taskAPI.query().detailById(adHocTasks.get(0).getId()));
			assertTrue(taskAPI.query().processInstId(processInst.getId()).userTask().userTaskOfWorking().orderByBeginTime().desc().listPage(0, 100).size() == 2);
			// 完成加签
			taskAPI.completeTask(adHocTasks.get(0), UserContext.fromUID(startUser), true);
			// 已办历史查询
			assertTrue(taskAPI.queryHistory().processInstId(processInst.getId()).userTask().userTaskOfWorking().orderByEndTime().desc().listPage(0, 100).size() == 1);
			assertTrue(taskAPI.query().processInstId(processInst.getId()).userTask().userTaskOfWorking().orderByBeginTime().desc().listPage(0, 100).size() == 1);
			// 执行到U2
			tasks = taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true).fetchActiveTasks();

			// 设置流程变量var1=2,S1抛出BPMN异常，执行Error节点，并设置成1后重新执行S1到S2，由于脚本任务S2设置流程变量var1=2,S3执行抛出异常
			processAPI.setVariable(processInst, "var1", "2");
			try {
				tasks = taskAPI.completeTask(tasks.get(0), UserContext.fromUID(startUser), true).fetchActiveTasks();
			} catch (Exception e) {
			}

			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}

	private TeamModel initTeamModel(String userId) {
		List<TeamModel> teams = TeamCache.getListByUser(userId);
		if (!teams.isEmpty()) {
			return teams.get(0);
		} else {
			// create a test team
			TeamModelImpl team = new TeamModelImpl();
			team.setOrderIndex(0);
			team.setName("QueryTaskTest(测试用例创建)");
			team.setClosed(false);
			OrgDaoFactory.createTeam().insert(team);

			TeamMemberModelImpl member = new TeamMemberModelImpl();
			member.setOrderIndex(0);
			member.setTeamId(team.getId());
			member.setUserId(userId);

			OrgDaoFactory.createTeamMember().insert(member);
			return OrgDaoFactory.createTeam().getModel(team.getId());
		}
	}
}
