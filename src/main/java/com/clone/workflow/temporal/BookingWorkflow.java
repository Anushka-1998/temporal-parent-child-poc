package com.clone.workflow.temporal;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface BookingWorkflow {

    @WorkflowMethod
    String startBookingEvent(String bookingId);

    @SignalMethod
    void bookingDone(String name);

}
