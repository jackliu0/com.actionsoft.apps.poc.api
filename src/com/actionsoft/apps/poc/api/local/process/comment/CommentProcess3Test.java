package com.actionsoft.apps.poc.api.local.process.comment;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.model.run.TaskCommentModel;
import com.actionsoft.bpms.bpmn.engine.model.run.TaskCommentTempModel;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class CommentProcess3Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Comment审核菜单/特殊动作-跳转等待（指定节点处理完再回来）";
	}

	public String getProcessDefId() {
		return "obj_518fceadf6464dc38d99f0c5bec150e7";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 启动流程,执行到U2
			TaskInstance u1Task = processAPI.start(processInst, null).fetchActiveTasks().get(0);
			TaskInstance u2Task = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			// 选择审核菜单为‘不同意’
			taskAPI.setComment(u2Task, "不同意", "这里是U2留言内容");
			TaskCommentTempModel u2TaskTempComment = taskAPI.getCommentOfTemp(u2Task.getId());
			info("临时选择了：" + u2TaskTempComment.getActionName());
			assertTrue(u2TaskTempComment.getActionName().equals("不同意"));
			TaskInstance u4Task = taskAPI.completeTask(u2Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u4Task.getTitle().indexOf("U4") > -1);
			taskAPI.setComment(u4Task, "不同意", "这里是U4留言内容");
			u1Task = taskAPI.completeTask(u4Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u1Task.getTitle().indexOf("U1") > -1);
			// 直接回到U4
			u4Task = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u4Task.getTitle().indexOf("U4") > -1);
			taskAPI.setComment(u4Task, "同意", "这里是U4留言内容");
			taskAPI.completeTask(u4Task, UserContext.fromUID(startUser), true);
			assertTrue(processAPI.isEndById(processInst.getId()));
			// 打印审批历史记录
			List<TaskCommentModel> comments = processAPI.getCommentsById(processInst.getId());
			for (TaskCommentModel comment : comments) {
				info("历史：" + comment);
			}

			// 2.创建实例
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 启动流程,执行到U2
			u1Task = processAPI.start(processInst, null).fetchActiveTasks().get(0);
			u2Task = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			// 选择审核菜单为‘同意’
			taskAPI.setComment(u2Task, "同意", "这里是U2留言内容");
			TaskInstance u3Task = taskAPI.completeTask(u2Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u3Task.getTitle().indexOf("U3") > -1);
			taskAPI.setComment(u3Task, "不同意", "这里是U3留言内容");
			u1Task = taskAPI.completeTask(u3Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u1Task.getTitle().indexOf("U1") > -1);
			// 直接回到U3
			u3Task = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			taskAPI.setComment(u3Task, "同意", "这里是U3留言内容");
			taskAPI.completeTask(u3Task, UserContext.fromUID(startUser), true);
			assertTrue(processAPI.isEndById(processInst.getId()));
			// 打印审批历史记录
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
