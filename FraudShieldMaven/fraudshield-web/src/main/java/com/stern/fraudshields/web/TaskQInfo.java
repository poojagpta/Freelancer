package com.stern.fraudshields.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskQInfo extends HttpServlet {

	   private static volatile int TASK_COUNTER = 0;
	   private static final Logger log = LoggerFactory
				.getLogger(TaskQInfo.class);
	   

	   // Executed by user menu click
	   public void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws IOException {

	       // Build a task using the TaskOptions Builder pattern from ** above
	       Queue queue = QueueFactory.getDefaultQueue();
	       queue.add(TaskOptions.Builder.withUrl("/taskq_demo").method(TaskOptions.Method.POST)); 

	       resp.getWriter().println( 
	             "Task have been added to default queue...");
	       
	       resp.getWriter().println(
	             "Refresh this page to add another count task");
	}

	   // Executed by TaskQueue
	   @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	       throws ServletException, IOException {
	       
	       // This is the body of the task
	       for(int i = 0; i < 1000; i++) {
	             log.info("Processing: " + req.getHeader("X-AppEngine-TaskName") + "-" +           
	               TASK_COUNTER++); 
	              try { 
	                 // Sleep for a second (if the rate is set to 1/s this will allow at 
	                           //most 1 more task to be processed)
	                 Thread.sleep(1000); 
	              } catch (InterruptedException e) { // ignore}
	       }
	    }
	}
}  