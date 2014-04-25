package com.gae.service;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskQueueService {

	public void addToQueue(){
		Queue queue = QueueFactory.getQueue("OrderProcessingQueue");
		TaskOptions taskOptions = TaskOptions.Builder.withUrl("/processOrder");
		                        /* .param("customerName", customerName)
		                         .param("orderKey", String.valueOf(order.getKey().getId())).method(Method.POST);*/
		queue.add(taskOptions);
	} 
}
