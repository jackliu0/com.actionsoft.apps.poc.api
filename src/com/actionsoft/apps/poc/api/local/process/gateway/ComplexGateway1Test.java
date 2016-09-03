package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.ArrayList;
import java.util.List;

import com.actionsoft.bpms.bpmn.engine.core.delegate.BehaviorContext;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ComplexGatewayInterface;
import com.actionsoft.bpms.bpmn.engine.model.def.ProcessNode;
import com.actionsoft.bpms.bpmn.engine.model.def.SequenceFlowModel;
import com.actionsoft.exception.AWSIllegalArgumentException;

/**
 * 复杂网关-Split Sample
 */
public class ComplexGateway1Test implements ComplexGatewayInterface {

	/**
	 * 汇聚前置分支
	 * 
	 * @param ctx 引擎上下文
	 * @param incomingSequenceFlows 前置分支定义
	 * @param finishedSequenceFlows 已完成的前置分支
	 * @return 符合聚合条件返回true
	 */
	public boolean mergeIncoming(final BehaviorContext ctx, final List<SequenceFlowModel> incomingSequenceFlows, final List<SequenceFlowModel> finishedSequenceFlows) {
		return true;// 不适用
	}

	/**
	 * 评估，返回后继分支路线
	 * 
	 * @param ctx 引擎上下文
	 * @param outgoingSequenceFlows 后继分支定义
	 * @return 要激活的后继分支连线，如果返回了null或一个空的List集合且该网关定义了一个默认的连线，那么该默认分支将被执行
	 */
	public List<SequenceFlowModel> branchOutgoing(final BehaviorContext ctx, final List<SequenceFlowModel> outgoingSequenceFlows) {
		List<SequenceFlowModel> branch = new ArrayList<SequenceFlowModel>();
		ProcessNode processNode = (ProcessNode) ctx.getProcessElement();
		// 判断流程变量num
		int num = 0;
		Object str1 = ctx.getVariable("num");
		try {
			if (str1 != null) {
				num = Integer.parseInt(str1.toString());
			}
		} catch (NumberFormatException e) {
			// 抛出格式非法异常，流程分支将中断于该网关
			throw new AWSIllegalArgumentException("num", AWSIllegalArgumentException.FORMAT);
		}
		if (num > 0 && num < 6) {
			branch.add(processNode.findOutgoingTransition("obj_c6011922a2100001b6fb778063c61d39"));
		} else if (num > 5 && num < 8) {
			branch.add(processNode.findOutgoingTransition("obj_c60119282c3000015be93187785c18e3"));
		} else {
			branch.add(processNode.findOutgoingTransition("obj_c6011926812000016f557dcd5f2e14da"));
		}
		return branch;
	}
}
