package com.clone.workflow;

import com.clone.workflow.temporal.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

@SpringBootApplication
public class ActivityPlanApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(ActivityPlanApplication.class, args);
		WorkerFactory factory = appContext.getBean(WorkerFactory.class);
		BookingActivity signUpActivity = appContext.getBean(BookingActivity.class);
		Worker worker = factory.newWorker(ActivityPlanWorkflow.QUEUE_NAME);
		Worker childWorker = factory.newWorker(BookingWorkflow.QUEUE_NAME);
		childWorker.registerWorkflowImplementationTypes(BookingWorkflowImpl.class);
		childWorker.registerActivitiesImplementations(signUpActivity);
		worker.registerWorkflowImplementationTypes(ActivityPlanWorkflowImpl.class);
		worker.registerActivitiesImplementations(signUpActivity);
		factory.start();
	}

}
