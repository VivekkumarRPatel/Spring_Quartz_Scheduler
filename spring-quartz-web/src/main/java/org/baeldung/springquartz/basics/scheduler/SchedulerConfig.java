package org.baeldung.springquartz.basics.scheduler;

import java.io.IOException;
import java.util.Properties;

import org.baeldung.springquartz.config.AutoWiringSpringBeanJobFactory;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;


@Configuration
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='true'")
public class SchedulerConfig {
		
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfig.class);

	
	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
	    jobFactory.setApplicationContext(applicationContext);
	    return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("jobTrigger") Trigger trigger, @Qualifier("job1Trigger") Trigger trigger2)  throws IOException {
	    SchedulerFactoryBean factory = new SchedulerFactoryBean();
	         factory.setAutoStartup(true);
	         factory.setJobFactory(springBeanJobFactory());   
	         /*factory.setQuartzProperties(quartzProperties());*/
	         /*System.out.println("Group and Name "+job3.getKey().getGroup()+"  "+job3.getKey().getName());
	         factory.setJobDetails(job3);*/
	         factory.setTriggers(trigger,trigger2);
	         System.out.println("Data of trigger:  "+trigger.getKey().getName());
	         System.out.println("Data of trigger2:  "+trigger2.getKey().getName());

	         /*factory.setGlobalJobListeners(new SimpleJobListener("GlobalListener"));*/
	         factory.setGlobalJobListeners(new SimpleJobListener());

	        /* System.out.println("Data of trigger3:  "+trigger3.getKey().getName());*/
	     
	    return factory;
	    }

	
	
	
	
/*	public void schedulerFactoryBean()  throws IOException {
	    SchedulerFactoryBean factory = new SchedulerFactoryBean();
	         factory.setAutoStartup(true);
	         factory.setJobFactory(springBeanJobFactory());
	         factory.setQuartzProperties(quartzProperties());
	         System.out.println("Group and Name "+job3.getKey().getGroup()+"  "+job3.getKey().getName());
	         factory.setJobDetails(job3);
	         factory.setTriggers(trigger,trigger2);
	         System.out.println("Data of trigger:  "+trigger.getKey().getName());
	         System.out.println("Data of trigger2:  "+trigger2.getKey().getName());
	       }*/
	
		
	@Bean
	public Properties quartzProperties() throws IOException {
	    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
	    propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
	    propertiesFactoryBean.afterPropertiesSet();
	    return propertiesFactoryBean.getObject();
	}


/*	public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
	    CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
	    factoryBean.setJobDetail(jobDetail);
	    LOG.info("Configuring trigger to fire every {} seconds", cronExpression);
	    factoryBean.setCronExpression(cronExpression);
	    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
	    return factoryBean;
	}*/
	
	public static SimpleTriggerFactoryBean createSimpleTrigger(JobDetail jobDetail, int frequencyInSec,String triggerName,String grpName) {

        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);

    
        LOG.info("Configuring trigger to fire every {} seconds", frequencyInSec);

        trigger.setRepeatInterval(frequencyInSec * 1000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName(triggerName);
        trigger.setGroup(grpName);
        return trigger;
    }

	public static JobDetailFactoryBean createJobDetail(Class jobClass,String jobName,String groupName) {
	    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
	    factoryBean.setJobClass(jobClass);
	    factoryBean.setName(jobName);
	    factoryBean.setDurability(true);
	    factoryBean.setGroup(groupName);
	    factoryBean.setRequestsRecovery(true);
	    return factoryBean;
	}


}
