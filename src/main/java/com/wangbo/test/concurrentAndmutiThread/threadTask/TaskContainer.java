package com.wangbo.test.concurrentAndmutiThread.threadTask;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

/**
 * springBoot中可以通过以下两种方式手动添加定时任务：
 * 		1.@Component实现接口SchedulingConfigurer, 在覆写方法configureTasks中手动添加定时任务
 * 		  taskRegistrar.addTriggerTask、taskRegistrar.addFixedRateTask等等
 * 		2.引用@Configuration中的TaskScheduler，通过TaskScheduler的方法添加定时任务
 * 		  TaskScheduler.scheduleAtFixedRate
 * @author 0380008788
 */
@Service
@PropertySource("classpath:application.properties")
public class TaskContainer implements SchedulingConfigurer {
	
	private final Logger logger = LoggerFactory.getLogger("taskContainer");

//	@Scheduled(cron = "0/10 * * ? * *")
	public void heartbeat() {
		System.err.println(Thread.currentThread().getName() + "心跳任务");
	}
	
    @Scheduled(initialDelay = 1000, fixedRate = 2000)
    public void reatbeat() {
        System.err.println(new Date() + "心跳任务");
    }
    
    // com.wangbo.test.concurrentAndmutiThread.threadTask.ScheduleConfigurations.taskExecutor2()
    @Autowired
    @Qualifier("taskScheduler")
	private TaskScheduler taskScheduler;
    
    public void init() {
    	if (gatewayModel) {
    		taskScheduler.schedule(new Runnable() {  
                @Override  
                public void run() {  
                	heartbeat();
                }  
            }, new Trigger() {

    			@Override
    			public Date nextExecutionTime(TriggerContext triggerContext) {
    				CronTrigger trigger = new CronTrigger(cron);
    				Date nextExec = trigger.nextExecutionTime(triggerContext);
    				return nextExec;
    			}
    		});
    	} else {
    		
    	}
    }

    @Value("${unicorn.gateway.model}")
	private boolean gatewayModel;
	private String cron = "0/5 * * ? * *";
    
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		if (gatewayModel) {
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
		} else {
			
		}
	}
    
}

