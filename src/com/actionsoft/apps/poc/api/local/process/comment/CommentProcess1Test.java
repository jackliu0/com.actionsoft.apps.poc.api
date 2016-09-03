package com.actionsoft.apps.poc.api.local.process.comment;

import java.util.List;
import java.util.Map;

import com.actionsoft.apps.poc.api.local.process.LocalProcessAPITestCase;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionTrack;
import com.actionsoft.bpms.bpmn.engine.model.def.EndEventModel;
import com.actionsoft.bpms.bpmn.engine.model.def.ProcessElement;
import com.actionsoft.bpms.bpmn.engine.model.run.TaskCommentModel;
import com.actionsoft.bpms.bpmn.engine.model.run.TaskCommentTempModel;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.server.UserContext;

public class CommentProcess1Test extends LocalProcessAPITestCase {

	public String getTestCaseName() {
		return "Process API/Comment审核菜单/用于条件判断";
	}

	public String getProcessDefId() {
		return "obj_1ff2ea6974d642b587175079cfbd0717";
	}

	public boolean execute(Map<String, Object> params) {
		String startUser = "admin";
		try {
			// -----测试选择同意-----

			// 1.创建实例
			ProcessInstance processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 2.启动流程,执行到U1
			TaskInstance u1Task = processAPI.start(processInst, null).fetchActiveTasks().get(0);
			// 3.选择审核菜单为‘同意’
			taskAPI.setComment(u1Task, "同意", "这里是U1留言内容");
			TaskCommentTempModel u1TaskTempComment = taskAPI.getCommentOfTemp(u1Task.getId());
			info("临时选择了：" + u1TaskTempComment.getActionName());
			assertTrue(u1TaskTempComment.getActionName().equals("同意"));
			// 4.根据规则，应该推进到U2这个路线
			TaskInstance u2Task = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u2Task.getTitle().indexOf("U2") > -1);
			taskAPI.completeTask(u2Task, UserContext.fromUID(startUser), true);
			// 5.打印审批历史记录
			List<TaskCommentModel> comments = processAPI.getCommentsById(processInst.getId());
			for (TaskCommentModel comment : comments) {
				info("历史：" + comment);
			}
			// 流程结束了
			assertTrue(processAPI.isEndById(processInst.getId()));

			// -----测试选择不同意-----
			// 1.创建实例
			processInst = processAPI.createProcessInstance(getProcessDefId(), null, startUser, "", "", "API-" + System.currentTimeMillis(), null);
			// 2.启动流程,执行到U1
			u1Task = processAPI.start(processInst, null).fetchActiveTasks().get(0);
			// 3.选择审核菜单为‘不同意’
			taskAPI.setComment(u1Task, "不同意", "这里是U1留言内容");
			u1TaskTempComment = taskAPI.getCommentOfTemp(u1Task.getId());
			info("临时选择了：" + u1TaskTempComment.getActionName());
			assertTrue(u1TaskTempComment.getActionName().equals("不同意"));
			// 4.根据规则，应该推进到U3这个路线
			TaskInstance u3Task = taskAPI.completeTask(u1Task, UserContext.fromUID(startUser), true).fetchActiveTasks().get(0);
			assertTrue(u3Task.getTitle().indexOf("U3") > -1);
			// 5.U3节点选择审核菜单为‘不同意’
			taskAPI.setComment(u3Task, "不同意", "这里是U3留言内容");
			List<ProcessExecutionTrack> executions = taskAPI.completeTask(u3Task, UserContext.fromUID(startUser), true).fetch();
			for (ProcessExecutionTrack track : executions) {
				ProcessElement element = track.getCurrentElement();
				if (element instanceof EndEventModel) {
					assertTrue(element.getName().equals("不同意结束"));
				}
			}
			// 6.打印审批历史记录
			comments = processAPI.getCommentsById(processInst.getId());
			for (TaskCommentModel comment : comments) {
				info("历史：" + comment);
			}
			// 流程结束了
			assertTrue(processAPI.isEndById(processInst.getId()));
			return true;
		} catch (Exception e) {
			fail(e.toString());
			return false;
		}
	}
}
