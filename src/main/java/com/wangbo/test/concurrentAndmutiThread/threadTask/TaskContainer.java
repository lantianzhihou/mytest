package com.wangbo.test.concurrentAndmutiThread.threadTask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskContainer{

	@Scheduled(cron = "0/10 * * ? * *")
	public void heartbeat() {
		System.err.println(Thread.currentThread().getName() + "心跳任务");
	}
	
}
