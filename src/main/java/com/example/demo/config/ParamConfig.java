package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @Date 2020/7/1 13:35
 * @author zxl
 */
@Configuration
public class ParamConfig {


    /**
     * 核心线程数
     */
    @Value("${param.async.executor.thread.core_pool_size}")
    private int corePoolSize;
    /**
     * 最大线程数
     */
    @Value("${param.async.executor.thread.max_pool_size}")
    private int maxPoolSize;
    /**
     * 队列最大长度
     */
    @Value("${param.async.executor.thread.queue_capacity}")
    private int queueCapacity;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    @Value("${param.async.executor.thread.keep_alive_seconds}")
    private int keepAliveSeconds;
    /*** 线程名称 */
    @Value("${param.async.executor.thread.thread_name}")
    private String threadName;
    /*** 定时任务线程数 */
    @Value("${param.async.task_scheduler.pool_size}")
    private int poolSize;


    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
}
