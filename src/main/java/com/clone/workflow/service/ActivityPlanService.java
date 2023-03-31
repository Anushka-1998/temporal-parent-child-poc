package com.clone.workflow.service;

import com.clone.workflow.constants.ActivityPlanConstants;
import com.clone.workflow.temporal.ActivityPlanWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;

@Service
public class ActivityPlanService {
	@Autowired
	WorkflowClient workflowClient;

	public void placeOrder(String workflowId) {
		ActivityPlanWorkflow workflow = createWorkFlowConnection(workflowId);
		workflow.startActivityPlanWorkflow(workflowId);
	}

	public ActivityPlanWorkflow createWorkFlowConnection(String workflowId) {
		WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(ActivityPlanConstants.ACTIVITY_PLAN_QUEUE_NAME)
				.setWorkflowId("Parent_" + workflowId).build();
		return workflowClient.newWorkflowStub(ActivityPlanWorkflow.class, options);
	}

}
