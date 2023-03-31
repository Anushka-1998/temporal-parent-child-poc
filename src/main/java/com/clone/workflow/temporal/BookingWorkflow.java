package com.clone.workflow.temporal;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface BookingWorkflow {

    public static final String QUEUE_NAME = "BOOKING_QUEUE";

    @WorkflowMethod
    String startBookingEvent(String bookingId);

    @SignalMethod
    void bookingDone(String name);

}
