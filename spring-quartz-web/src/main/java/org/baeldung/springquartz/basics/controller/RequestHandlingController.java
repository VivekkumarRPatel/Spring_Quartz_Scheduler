package org.baeldung.springquartz.basics.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.baeldung.springquartz.basics.dispatcher.Request;
import org.baeldung.springquartz.basics.scheduler.SchedulerConfig;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestHandlingController {
	
	@Autowired
	private SchedulerConfig schedulerConfig;
	
	
	@Autowired
	@Qualifier("thirdJobTrigger")
	private Trigger trigger;
	
	@Autowired
	@Qualifier("Job3")JobDetail jobDetail;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	
	
	
	@PostMapping("/scheduleJob")
	public void ScheduledJob(@Valid @RequestBody Request jobExecuteRequest) throws Exception{
	
		String groupName="BILLING";
		
		
		System.out.println("Hi Job"+jobExecuteRequest.getJobName()+" Execute");
		System.out.println("Trigger Name is "+trigger.getKey().getName());

		System.out.println("Scheduling Third Trigger");
		Scheduler scheduler=schedulerFactoryBean.getScheduler();
		scheduler.scheduleJob(jobDetail,trigger);		
	}
	}
	

