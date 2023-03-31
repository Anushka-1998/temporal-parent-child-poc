package com.clone.workflow.temporal;

import java.time.Duration;
import java.util.UUID;

import io.temporal.activity.ActivityOptions;
import io.temporal.api.enums.v1.ParentClosePolicy;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActivityPlanWorkflowImpl implements ActivityPlanWorkflow {

	private final RetryOptions retryoptions = RetryOptions.newBuilder().setInitialInterval(Duration.ofSeconds(1))
			.setMaximumInterval(Duration.ofSeconds(100)).setBackoffCoefficient(2).setMaximumAttempts(50000).build();
	private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
			.setRetryOptions(retryoptions).build();

	private final BookingActivity activity = Workflow.newActivityStub(BookingActivity.class, options);

	public boolean isOrderConfirmed = false;

	public boolean isOrderPickedUp = false;

	public boolean isOrderDelivered = false;

	@Override
	public String startActivityPlanWorkflow(String workflowId) {
		log.info("Inside ActivityPlan workflow");


		/* Untyped ChildStub
		ChildWorkflowOptions options = ChildWorkflowOptions.newBuilder().setTaskQueue(ChildWorkflow.QUEUE_NAME)
				.setWorkflowId("Child_" + id).build();
		ChildWorkflowStub childUntyped = Workflow.newUntypedChildWorkflowStub("ChildWorkflow",options);
		Promise<String> greeting = childUntyped.executeAsync(String.class, String.class, "Hello", "Anushka");
       */

		/* Normal ChildWork flow
		ChildWorkflowOptions options = ChildWorkflowOptions.newBuilder().setTaskQueue(ChildWorkflow.QUEUE_NAME)
				.setWorkflowId("Child_" + id).build();
		ChildWorkflow childWorkflow = Workflow.newChildWorkflowStub(ChildWorkflow.class,options);
		Promise<String> greeting = Async.function(childWorkflow::composeGreeting, "Hello", "Anushka");
		*/


		//Two childs spawning

		//Abandon case : parent closes but child continue to run
		//Terminate case : parent close child will also get terminate by parent reason : by Parent close policy
		//Request Cancel case : parent sends a request to child to cancel and both get cancelled
		//Unspecified case : terminates the child if parent terminates
		ChildWorkflowOptions bookingOptions1 = ChildWorkflowOptions.newBuilder()
				.setParentClosePolicy(ParentClosePolicy.PARENT_CLOSE_POLICY_ABANDON)
				.setTaskQueue(BookingWorkflow.QUEUE_NAME)
				.setWorkflowId("Booking_1_" + workflowId).build();

		ChildWorkflowOptions bookingOptions2 = ChildWorkflowOptions.newBuilder()
				.setParentClosePolicy(ParentClosePolicy.PARENT_CLOSE_POLICY_ABANDON)
				.setTaskQueue(BookingWorkflow.QUEUE_NAME)
				.setWorkflowId("Booking_2_" + workflowId).build();


		BookingWorkflow bookingChildWorkflow1 = Workflow.newChildWorkflowStub(BookingWorkflow.class,bookingOptions1);
		Promise<String> bookingChildWorkflowResponse1 = Async.function(bookingChildWorkflow1::startBookingEvent, UUID.randomUUID().toString());

		bookingChildWorkflow1.bookingDone("Misha");

		// Both children will run concurrently.
		BookingWorkflow bookingChildWorkflow2 = Workflow.newChildWorkflowStub(BookingWorkflow.class,bookingOptions2);
		Promise<String> bookingChildWorkflowResponse2 = Async.function(bookingChildWorkflow2::startBookingEvent, UUID.randomUUID().toString());


		return bookingChildWorkflowResponse1.get() + bookingChildWorkflowResponse2.get();

	}




}
