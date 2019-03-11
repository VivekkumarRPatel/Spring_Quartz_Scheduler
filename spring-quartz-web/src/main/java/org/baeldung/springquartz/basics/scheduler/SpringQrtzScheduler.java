package org.baeldung.springquartz.basics.scheduler;

import javax.annotation.PostConstruct;

import org.baeldung.springquartz.config.AutoWiringSpringBeanJobFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='false'")
public class SpringQrtzScheduler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        logger.info("Hello world from Spring...");
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        logger.debug("Configuring Job factory");

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(@Qualifier("firstJob") Trigger trigger, @Qualifier("secondJob") Trigger trigger2, @Qualifier("firstJob") JobDetail job, @Qualifier("secondJob") JobDetail job1) {

        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));

  /*     Scheduler scheduler =schedulerFactory.getScheduler();*/
        logger.debug("Setting the Scheduler up");
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(job,job1);
        schedulerFactory.setTriggers(trigger,trigger);

        return schedulerFactory;
    }
    
    

    @Bean(name="sampleJob")
    public JobDetailFactoryBean jobDetail(JobDetail jobClass) {

        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SampleJob.class);
   /*     jobDetailFactory.setJobClass(jobClass);*/
        jobDetailFactory.setName("Qrtz_Job_Detail");
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }
    
    @Bean(name="secondJob")
    public JobDetailFactoryBean jobDetail2(JobDetail jobClass1) {

        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SecondJob.class);
   /*     jobDetailFactory.setJobClass(jobClass);*/
        jobDetailFactory.setName("Qrtz_Job_Detail_2");
        jobDetailFactory.setDescription("Invoke Second Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }
    
    @Bean
    public SimpleTriggerFactoryBean trigger1(@Qualifier("secondJob") JobDetail job) {

        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);

        int frequencyInSec = 5;
        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);

        trigger.setRepeatInterval(frequencyInSec * 1000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName("Qrtz_Trigger_2");
        return trigger;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(@Qualifier("firstJob") JobDetail job) {

        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);

        int frequencyInSec = 10;
        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);

        trigger.setRepeatInterval(frequencyInSec * 1000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName("Qrtz_Trigger");
        return trigger;
    }
}
