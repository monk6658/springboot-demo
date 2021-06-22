package com.example.demo.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * springboot 定时任务
 * @author zxl
 * @date 2021/6/16 17:00
 */
@Component
public class ScheduleConfig implements SchedulingConfigurer {

    @Resource
    private ParamConfig paramConfig;

    /**
     * 异步线程池配置
     * @param scheduledTaskRegistrar 异步任务注册
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(getScheduledExecutorService());
    }

    /**
     * 定时任务线程配置
     * @return 定时任务线程
     */
    public ScheduledExecutorService getScheduledExecutorService(){
        return new ScheduledThreadPoolExecutor(paramConfig.getTimingCorePoolSize(), new CustomizableThreadFactory(paramConfig.getTimingNamePrefix()));
    }

}
