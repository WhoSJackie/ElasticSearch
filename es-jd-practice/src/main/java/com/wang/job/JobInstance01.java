package com.wang.job;

import com.wang.service.TestBeanInitService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JobInstance01 implements Job {

    @Autowired
    TestBeanInitService testBeanInitService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String desc = jobExecutionContext.getJobDetail().getDescription();
        Map<String, String> cacheMap = testBeanInitService.getCacheMap();
        System.out.println("测试实现Job的quartz任务执行->"+desc);
        System.out.println("获取cachemap的数据-->map=>"+cacheMap.get("msg")+"value=>"+cacheMap.get("value"));
    }
}
