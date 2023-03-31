package com.clone.workflow.temporal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookingActivityImpl implements BookingActivity {

	@Override
	public void generateInvoice() {
		log.info("***** Invoice has been generated *****");
	}

}
