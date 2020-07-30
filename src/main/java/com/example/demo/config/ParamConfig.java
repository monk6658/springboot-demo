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

    /**************** redis 配置 ******************/
    /*** 最大连接数 */
    @Value("${param.redis.pool.max_idle}")
    private int maxIdle;
    @Value("${param.redis.pool.max_wait_millis}")
    private int maxWaitMillis;
    @Value("${param.redis.pool.max_active}")
    private int maxActive;
    @Value("${param.redis.pool.min_idle}")
    private int minIdle;

    /****** tPos缓存信息 *****/
    @Value("${param.redis.tPosAdr.host}")
    private String tPosHost;
    @Value("${param.redis.tPosAdr.port}")
    private int tPosPort;
    @Value("${param.redis.tPosAdr.password}")
    private String tPosPassword;
    @Value("${param.redis.tPosAdr.sel}")
    private String tPosSel;

    /****** 异步缓存信息 *****/
    @Value("${param.redis.notify.host}")
    private String notifyHost;
    @Value("${param.redis.notify.port}")
    private int notifyPort;
    @Value("${param.redis.notify.password}")
    private String notifyPassword;
    @Value("${param.redis.notify.sel}")
    private String notifySel;


    /**************** redis 配置 ******************/



    /***************************** 线程池配置 ******************/
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
    /***************************** 线程池配置 ******************/

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

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public String gettPosHost() {
        return tPosHost;
    }

    public void settPosHost(String tPosHost) {
        this.tPosHost = tPosHost;
    }

    public int gettPosPort() {
        return tPosPort;
    }

    public void settPosPort(int tPosPort) {
        this.tPosPort = tPosPort;
    }

    public String gettPosPassword() {
        return tPosPassword;
    }

    public void settPosPassword(String tPosPassword) {
        this.tPosPassword = tPosPassword;
    }


    public String getNotifyHost() {
        return notifyHost;
    }

    public void setNotifyHost(String notifyHost) {
        this.notifyHost = notifyHost;
    }

    public int getNotifyPort() {
        return notifyPort;
    }

    public void setNotifyPort(int notifyPort) {
        this.notifyPort = notifyPort;
    }

    public String getNotifyPassword() {
        return notifyPassword;
    }

    public void setNotifyPassword(String notifyPassword) {
        this.notifyPassword = notifyPassword;
    }

    public String gettPosSel() {
        return tPosSel;
    }

    public void settPosSel(String tPosSel) {
        this.tPosSel = tPosSel;
    }

    public String getNotifySel() {
        return notifySel;
    }

    public void setNotifySel(String notifySel) {
        this.notifySel = notifySel;
    }
}
