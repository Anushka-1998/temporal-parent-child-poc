package com.clone.workflow.service;


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

	public ActivityPlanWorkflow createWorkFlowConnection(String id) {
		WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(ActivityPlanWorkflow.QUEUE_NAME)
				.setWorkflowId("Parent_" + id).build();
		return workflowClient.newWorkflowStub(ActivityPlanWorkflow.class, options);
	}

}
