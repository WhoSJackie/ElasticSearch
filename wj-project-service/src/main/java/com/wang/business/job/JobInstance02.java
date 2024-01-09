package com.wang.business.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobInstance02 extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String desc = jobExecutionContext.getJobDetail().getDescription();
        System.out.println("测试继承自QuartzJobBean的quartz任务执行->"+desc);
    }
}
