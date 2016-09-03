package com.actionsoft.apps.poc.api.local.job;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.actionsoft.bpms.schedule.ITrigger;

public class HelloTrigger implements ITrigger {

	public Trigger getTrigger(String name, String group) {
		// 执行2次，间隔5秒钟，必须使用参数中name/group为trigger的identity
		SimpleTrigger trigger = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(2, 5)).withIdentity(name, group).build();
		return trigger;
	}

}
