package org.baeldung.springquartz.basics.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

public class SimpleJobListener  implements JobListener{

	String listenerName="Global Listener";
	
/*	SimpleJobListener(String name){
		this.listenerName=name;
	}*/
	
	
	@Override
	public String getName() {
		return listenerName; //must return a name
	}

	// Run this if job is about to be executed.
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {

		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("jobToBeExecuted");
		System.out.println("Job : " + jobName + " is going to start...");
	 }

	// No idea when will run this?
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println("jobExecutionVetoed");
	}

	//Run this after job has been executed
	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException)  {
		System.out.println("jobWasExecuted");

		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("Job : " + jobName + " is finished...");
		

		
/*		try {
			
			context.getScheduler().unscheduleJob(context.getTrigger().getKey());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		if (jobException != null) {	
			if ((jobException.getMessage()!=null)
					&& (!jobException.getMessage().equals(""))) {
				System.out.println("Deleted Job from Scheduler");
				
				try {
					context.getScheduler().deleteJob(context.getJobDetail().getKey());
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Exception thrown by: " + jobName
						+ " Exception: " + jobException.getMessage());
			}
		}
	}
}
	
	
	

