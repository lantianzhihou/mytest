package com.wangbo.test.concurrentAndmutiThread.threadTask;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

//@Component
@Configuration
@EnableScheduling
public class ScheduleConfigurations implements SchedulingConfigurer {
	
	private final Logger logger = LoggerFactory.getLogger("taskContainer");
	
	private String cron = "0/5 * * ? * *";
	
//	@Scheduled(cron = "0/5 * * ? * *")
//	public void heartbeat() {
//		logger.info(Thread.currentThread().getName() + "心跳任务");
//	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		 // 开启新线程模拟外部更改了任务执行周期  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    Thread.sleep(15 * 1000);  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
                  
                cron = "0/10 * * * * ?";  
                System.err.println("cron change to: " + cron);  
            }  
        }).start();
        
		System.out.println("设置任务计划执行器");
//		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
		taskRegistrar.setScheduler(taskExecutor2());
		
		taskRegistrar.addTriggerTask(new Runnable() {
			
			@Override
			public void run() {
				logger.info("TaskContainer is running...");
			}
		}, new Trigger() {
			
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cron);  
                Date nextExec = trigger.nextExecutionTime(triggerContext);  
                return nextExec; 
			}
		});
	}
	
	@Bean(name="taskExecutor")
	public ScheduledExecutorService taskExecutor1() {
		return new ScheduledThreadPoolExecutor(10,new ThreadFactory() {
			final String namePrefix = "unicornTimedTask";
			
			final ThreadGroup group = (System.getSecurityManager() != null)
					? System.getSecurityManager().getThreadGroup()
					: Thread.currentThread().getThreadGroup();
			final AtomicLong count = new AtomicLong();
					
			@Override
			public Thread newThread(Runnable target) {
			    Thread thread = new Thread(group, target, namePrefix + "-" + count.incrementAndGet());
//			    thread.setPriority(1);
//			    int maxPriority = group.getMaxPriority();
//			    System.out.println(maxPriority);
				return thread;
			}
		});
	}
	
	@Bean(name="taskScheduler")
	public TaskScheduler taskExecutor2() {
		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10,new ThreadFactory() {
			final String namePrefix = "unicornTimedTask";
			
			final ThreadGroup group = (System.getSecurityManager() != null)
					? System.getSecurityManager().getThreadGroup()
					: Thread.currentThread().getThreadGroup();
			final AtomicLong count = new AtomicLong();
					
			@Override
			public Thread newThread(Runnable target) {
			    Thread thread = new Thread(group, target, namePrefix + "-" + count.incrementAndGet());
//			    thread.setPriority(1);
//			    int maxPriority = group.getMaxPriority();
//			    System.out.println(maxPriority);
				return thread;
			}
		});
		return new ConcurrentTaskScheduler(scheduledThreadPoolExecutor);
		
	}
	
	
}
