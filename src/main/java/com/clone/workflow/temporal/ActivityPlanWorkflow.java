package com.clone.workflow.temporal;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ActivityPlanWorkflow {

	@WorkflowMethod
	String startActivityPlanWorkflow(String workflowId);

}
