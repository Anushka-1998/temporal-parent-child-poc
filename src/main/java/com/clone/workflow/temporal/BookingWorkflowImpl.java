package com.clone.workflow.temporal;


import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static io.temporal.workflow.Workflow.sleep;

@Slf4j
public class BookingWorkflowImpl implements BookingWorkflow {

    private final RetryOptions retryoptions = RetryOptions.newBuilder().setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100)).setBackoffCoefficient(2).setMaximumAttempts(50000).build();
    private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryoptions).build();

    private final BookingActivity activity = Workflow.newActivityStub(BookingActivity.class, options);

    @Override
    public String startBookingEvent(String bookingId) {
        log.info("Inside startBookingEvent : {}",bookingId);
        activity.generateInvoice();
       // sleep(20000);
       return bookingId;
    }

    @Override
    public void bookingDone(String name){
        log.info("Signal from activityPlan workflow");
    }
}
