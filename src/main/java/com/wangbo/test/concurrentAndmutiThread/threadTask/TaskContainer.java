package com.wangbo.test.concurrentAndmutiThread.threadTask;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskContainer{

//	@Scheduled(cron = "0/10 * * ? * *")
	public void heartbeat() {
		System.err.println(Thread.currentThread().getName() + "心跳任务");
	}
	
    @Scheduled(initialDelay = 1000, fixedRate = 2000)
    public void reatbeat() {
        System.err.println(new Date() + "心跳任务");
    }
    
	
}

