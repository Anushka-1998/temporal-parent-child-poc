package com.clone.workflow.temporal;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface BookingActivity {

	@ActivityMethod
	void generateInvoice();

}
