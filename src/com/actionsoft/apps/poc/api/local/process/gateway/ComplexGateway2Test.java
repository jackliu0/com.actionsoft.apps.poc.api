package com.actionsoft.apps.poc.api.local.process.gateway;

import java.util.List;

import com.actionsoft.bpms.bpmn.engine.core.delegate.BehaviorContext;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ComplexGatewayInterface;
import com.actionsoft.bpms.bpmn.engine.model.def.SequenceFlowModel;

/**
 * 复杂网关-Merge Sample
 */
public class ComplexGateway2Test implements ComplexGatewayInterface {

	/**
	 * 汇聚前置分支
	 * 
	 * @param ctx 引擎上下文
	 * @param incomingSequenceFlows 前置分支定义
	 * @param finishedSequenceFlows 已完成的前置分支
	 * @return 符合聚合条件返回true
	 */
	public boolean mergeIncoming(final BehaviorContext ctx, final List<SequenceFlowModel> incomingSequenceFlows, final List<SequenceFlowModel> finishedSequenceFlows) {
		// TestCase1: flow1、flow2、flow3三个分支全部执行，只要有2个分支完成，就通过
		if (finishedSequenceFlows.size() == 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 评估，返回后继分支路线
	 * 
	 * @param ctx 引擎上下文
	 * @param outgoingSequenceFlows 后继分支定义
	 * @return 要激活的后继分支连线，如果返回了null或一个空的List集合且该网关定义了一个默认的连线，那么该默认分支将被执行
	 */
	public List<SequenceFlowModel> branchOutgoing(final BehaviorContext ctx, final List<SequenceFlowModel> outgoingSequenceFlows) {
		return outgoingSequenceFlows;
	}
}
