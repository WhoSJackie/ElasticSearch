package com.wang.business.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartJobInit implements CommandLineRunner {

    @Autowired
    JobExecutionService jobExecutionService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Job 初始化启动中...");
        jobExecutionService.scheduleJob();
        jobExecutionService.schedulerJob02();
    }
}
