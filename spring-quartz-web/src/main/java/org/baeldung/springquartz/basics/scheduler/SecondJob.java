
package org.baeldung.springquartz.basics.scheduler;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;


@Component
public class SecondJob implements Job {

	
	   Logger logger = LoggerFactory.getLogger(getClass());

	   /* @Autowired
	    private SampleJobService jobService;*/

	    public void execute(JobExecutionContext context) throws JobExecutionException {

	        /*logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

	        jobService.executeSecondJob();

	        logger.info("Next job scheduled @ {}", context.getNextFireTime());
*/	
	    /*System.out.println("Job Key is : "+context.getJobDetail().getKey()+"  Job Name : "+context.getJobDetail().getKey().getName()+"   Job Result : "+context.getResult());*/	
	    System.out.println("Second job is execute "+context.getJobDetail().getKey().getName());
	    }
	    
	    @Bean(name = "Job1")
	    public JobDetailFactoryBean job() {
	    	String groupName="OMS";
	        return SchedulerConfig.createJobDetail(this.getClass(),"Second Job",groupName);
	    }

	    
	    @Bean(name = "job1Trigger")
	    public SimpleTriggerFactoryBean jobTrigger(@Qualifier("Job1")JobDetail jobDetail) {
	    	int frequency=10;
	    	String groupName="OMS";
	    	String triggerName="Second Trigger";
	        return SchedulerConfig.createSimpleTrigger(jobDetail, frequency,triggerName,groupName);
	    }
	    
	    
	
}
