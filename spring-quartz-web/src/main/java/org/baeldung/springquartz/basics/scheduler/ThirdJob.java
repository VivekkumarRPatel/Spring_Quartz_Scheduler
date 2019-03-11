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
public class ThirdJob  implements Job{

	Logger logger = LoggerFactory.getLogger(getClass());

	   /* @Autowired
	    private SampleJobService jobService;*/

	    public void execute(JobExecutionContext context) throws JobExecutionException {

	        /*logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

	        jobService.executeSampleJob();

	        logger.info("Next job scheduled @ {}", context.getNextFireTime());
	*/    
	    /*System.out.println("Job Key is : "+context.getJobDetail().getKey()+"  Job Name : "+context.getJobDetail().getKey().getName()+"   Job Result : "+context.getResult());*/	
	    System.out.println("Third Job is execute "+context.getJobDetail().getKey().getName());	
	    
	    }
	    
	    @Bean(name = "Job3")
	    public JobDetailFactoryBean job() {
	    	String groupName="BILLING";
	        return SchedulerConfig.createJobDetail(this.getClass(),"Third Job",groupName);
	    }

	    @Bean(name = "thirdJobTrigger")
	    public SimpleTriggerFactoryBean jobTrigger(@Qualifier("Job3")JobDetail jobDetail) {
	    	int frequency=15;
	    	String grpName="BILLING";
	    	String triggerName="Third Trigger";
	        return SchedulerConfig.createSimpleTrigger(jobDetail, frequency,triggerName,grpName);
	    }
	
}
