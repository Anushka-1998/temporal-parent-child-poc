package com.clone.workflow.temporal;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ActivityPlanWorkflow {

	public static final String QUEUE_NAME = "ACTIVITY_PLAN_QUEUE";

	@WorkflowMethod
	String startActivityPlanWorkflow(String workflowId);

}
