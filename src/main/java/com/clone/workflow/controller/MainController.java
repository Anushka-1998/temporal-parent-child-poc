package com.clone.workflow.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clone.workflow.service.OrderService;

@RestController
@Slf4j
public class MainController {

	@Autowired
	OrderService orderService;

	@PostMapping("/startWorkflow")
	public String createOrder(@RequestParam("id") String id) {
		log.info("Request received");
		orderService.placeOrder(id);
		return "Order Placed";
	}

	/*@PostMapping("/orderAccepted")
	public String orderAccepted(@RequestParam("id") String id) {
		orderService.makeOrderAccepted(id);
		return "Order Accepted";
	}

	@PostMapping("/orderPickedUp")
	public String orderPickedUp(@RequestParam("id") String id) {
		orderService.makeOrderPickedUp(id);
		return "Order Picked Up";
	}

	@PostMapping("/orderDelivered")
	public String orderDelivered(@RequestParam("id") String id) {
		orderService.makeOrderDelivered(id);
		return "Order Delivered";
	}*/
}
