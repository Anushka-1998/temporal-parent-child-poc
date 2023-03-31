package com.clone.workflow.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.clone.workflow.service.ActivityPlanService;

@RestController
@Slf4j
public class ActivityPlanController {

	@Autowired
    ActivityPlanService activityPlanService;

	@PostMapping("/startWorkflow")
	public String startWorkflow(@RequestParam("id") String id) {
		log.info("Request received to start an activity plan");
		activityPlanService.placeOrder(id);
		return "Invoice generated";
	}
}
