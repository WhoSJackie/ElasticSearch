package com.wang.business.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobExecutionService {

    @Autowired
    private Scheduler scheduler;

    public void scheduleJobImpJob()   {
        JobDetail jobDetail = JobBuilder.newJob(JobInstance01.class).withIdentity("job01","g1").withDescription("JobInstance1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("30 * * * * ? *");
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("tr01","trg01").withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail,trigger);
//            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void schedulerJobExtQuartz(){
        JobDetail jobDetail = JobBuilder.newJob(JobInstance02.class).withIdentity("job02","g2").withDescription("JobInstance2").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("30 * * * * ? *");
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("tr02","trg02").withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail,trigger);
//            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
