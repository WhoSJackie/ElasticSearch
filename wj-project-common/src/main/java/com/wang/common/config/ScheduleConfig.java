package com.wang.common.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class ScheduleConfig {

    @Autowired
    private JobFactory jobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setJobFactory(jobFactory);
        factoryBean.setWaitForJobsToCompleteOnShutdown(true);//这样当spring关闭时，会等待所有已经启动的quartz job结束后spring才能完全shutdown。
        factoryBean.setOverwriteExistingJobs(false);
        factoryBean.setStartupDelay(1);
        return factoryBean;
    }

    @Bean(name = "scheduler")
    public Scheduler getScheduler(){
        return schedulerFactoryBean().getScheduler();
    }




}
