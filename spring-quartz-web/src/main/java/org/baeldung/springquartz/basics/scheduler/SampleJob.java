package org.baeldung.springquartz.basics.scheduler;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class SampleJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

   /* @Autowired
    private SampleJobService jobService;*/

    public void execute(JobExecutionContext context) throws JobExecutionException {

        /*logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

        jobService.executeSampleJob();

        logger.info("Next job scheduled @ {}", context.getNextFireTime());
*/
  
    /*System.out.println("Job Key is : "+context.getJobDetail().getKey()+"  Job Name : "+context.getJobDetail().getKey().getName()+"  Job Result : "+context.getResult());*/	
    System.out.println("Sample Job is execute "+context.getJobDetail().getKey().getName());
    
        throw new JobExecutionException("Sample Job Exception");
    
/*    JobExecutionException e = new JobExecutionException("Retries exceeded");
    e.unscheduleFiringTrigger()*/
    
    }
    
    @Bean(name = "Job")
    public JobDetailFactoryBean job() {
    	String groupName="MF";
        return SchedulerConfig.createJobDetail(this.getClass(),"Sample Job",groupName);
    }

    @Bean(name = "jobTrigger")
    public SimpleTriggerFactoryBean jobTrigger(@Qualifier("Job")JobDetail jobDetail) {
    	int frequency=5;
    	String groupName="MF";
    	String triggerName="Sample Trigger";
        return SchedulerConfig.createSimpleTrigger(jobDetail, frequency,triggerName,groupName);
    }
}
