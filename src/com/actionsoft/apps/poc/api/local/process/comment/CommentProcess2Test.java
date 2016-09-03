package com.actionsoft.apps.poc.api.local.process.comment;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.TaskCommentModel;
import com.actionsoft.bpms.bpmn.engine.model.run.TaskCommentTempModel;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class CommentProcess2Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Comment审核菜单/特殊动作-退回（从哪来回哪去）";
	}

	public String getProcessDefId() {
		return "obj_e939c111ff4a42d9947ba1d835a23783";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 启动流程,执行到U1
			TaskInstance u1Task = processAPI.start(processInst, null).fetchActiveTasks().get(0);
			// 选择审核菜单为‘选U2’
			taskAPI.setComment(u1Task, "选U2", "这里是U1留言内容");
			TaskCommentTempModel u1TaskTempComment = taskAPI.getCommentOfTemp(u1Task.getId());
			info("临时选择了：" + u1TaskTempComment.getActionName());
			assertTrue(u1TaskTempComment.getActionName().equals("选U2"));
			TaskInstance u2Task = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u2Task.getTitle().indexOf("U2") > -1);
			TaskInstance u4Task = taskAPI.completeTask(u2Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			// 选择审核菜单为‘不同意’
			taskAPI.setComment(u4Task, "不同意", "这里是U4留言内容");
			u2Task = taskAPI.completeTask(u4Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u2Task.getTitle().indexOf("U2") > -1);
			u4Task = taskAPI.completeTask(u2Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			// 选择审核菜单为‘同意’
			taskAPI.setComment(u4Task, "同意", "这里是U4留言内容");
			taskAPI.completeTask(u4Task, UserContext.fromUID(startUser), true);
			assertTrue(processAPI.isEndById(processInst.getId()));
			List<TaskCommentModel> comments = processAPI.getCommentsById(processInst.getId());
			for (TaskCommentModel comment : comments) {
				info("历史：" + comment);
			}

			// 2.创建实例
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 启动流程,执行到U1
			u1Task = processAPI.start(processInst, null).fetchActiveTasks().get(0);
			// 选择审核菜单为‘选U3’
			taskAPI.setComment(u1Task, "选U3", "这里是U1留言内容");
			TaskCommentTempModel u3TaskTempComment = taskAPI.getCommentOfTemp(u1Task.getId());
			info("临时选择了：" + u3TaskTempComment.getActionName());
			assertTrue(u3TaskTempComment.getActionName().equals("选U3"));
			TaskInstance u3Task = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u3Task.getTitle().indexOf("U3") > -1);
			u4Task = taskAPI.completeTask(u3Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			// 选择审核菜单为‘不同意’
			taskAPI.setComment(u4Task, "不同意", "这里是U4留言内容");
			u3Task = taskAPI.completeTask(u4Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u3Task.getTitle().indexOf("U3") > -1);
			u4Task = taskAPI.completeTask(u3Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			// 选择审核菜单为‘同意’
			taskAPI.setComment(u4Task, "同意", "这里是U4留言内容");
			taskAPI.completeTask(u4Task, UserContext.fromUID(startUser), true);
			assertTrue(processAPI.isEndById(processInst.getId()));
			comments = processAPI.getCommentsById(processInst.getId());
			for (TaskCommentModel comment : comments) {
				info("历史：" + comment);
			}
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
