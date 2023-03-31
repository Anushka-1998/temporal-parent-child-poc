package com.clone.workflow.service;


import com.clone.workflow.temporal.ActivityPlanWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

@Service
public class OrderService {

	@Autowired
	WorkflowServiceStubs workflowServiceStubs;

	@Autowired
	WorkflowClient workflowClient;

	public void placeOrder(String workflowId) {
		ActivityPlanWorkflow workflow = createWorkFlowConnection(workflowId);
		workflow.startActivityPlanWorkflow(workflowId);
	}

	/*public void makeOrderAccepted(String workflowId) {
		WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + workflowId);
		workflow.signalOrderAccepted();
	}

	public void makeOrderPickedUp(String workflowId) {
		WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + workflowId);
		workflow.signalOrderPickedUp();
	}

	public void makeOrderDelivered(String workflowId) {
		WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + workflowId);
		workflow.signalOrderDelivered();
	}*/

	public ActivityPlanWorkflow createWorkFlowConnection(String id) {
		WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(ActivityPlanWorkflow.QUEUE_NAME)
				.setWorkflowId("Parent_" + id).build();
		return workflowClient.newWorkflowStub(ActivityPlanWorkflow.class, options);
	}

}
