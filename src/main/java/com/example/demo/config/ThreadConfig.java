package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池工具
 * @Date 2020/6/29 14:41
 * @author zxl
 */
@Component
public class ThreadConfig {

    @Resource
    private ParamConfig paramConfig;

    /**
     * 定时任务启动线程数
     * @author zxl
     * @time 2020/3/18 14:29
     */
    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //设置线程池大小
        taskScheduler.setPoolSize(paramConfig.getPoolSize());
        return taskScheduler;
    }

    /**
     * 默认线程池线程池
     *
     * @return Executor
     */
    @Bean
    public ThreadPoolTaskExecutor defaultThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(paramConfig.getCorePoolSize());
        //指定最大线程数
        executor.setMaxPoolSize(paramConfig.getMaxPoolSize());
        //队列中最大的数目
        executor.setQueueCapacity(paramConfig.getQueueCapacity());
        //线程名称前缀
        executor.setThreadNamePrefix(paramConfig.getThreadName());
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(paramConfig.getKeepAliveSeconds());
        //加载
        executor.initialize();
        return executor;
    }


}
