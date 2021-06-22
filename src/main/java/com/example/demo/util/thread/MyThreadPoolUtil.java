package com.example.demo.util.thread;

import com.example.demo.config.ParamConfig;
import com.example.demo.util.SpringUtil;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 线程池创建工具类
 * @author zxl
 * @date 2021/5/24 16:15
 */
@NoArgsConstructor
public class MyThreadPoolUtil {

    /*** 预防指令重排 致使初始化问题 */
    private volatile static MyThreadPoolExecutor myThreadPoolExecutor = null;
    /*** 线程池名称 */
    private final static String THREAD_NAME_PREFIX = "de-pool-";
    /*** 存活时间 */
    private final static long KEEP_ALIVE_TIME = 60000;
    /*** 核心线程数 */
    private final static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    /*** 最大线程数 */
    private final static int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;


    /**
     * 获取自定义线程信息
     * @param namePreFix 线程池名称前缀
     * @param keepAliveTime 超时时间
     * @param corePoolSize 核心线程
     * @param maxPoolSize 最大线程
     * @return 自定义线程信息
     */
    public static MyThreadPoolExecutor getMyThreadPoolExecutor(String namePreFix,long keepAliveTime,int corePoolSize,int maxPoolSize){
        if(myThreadPoolExecutor == null){
            synchronized (MyThreadPoolUtil.class){
                if(myThreadPoolExecutor == null) {
                    CustomizableThreadFactory customizableThreadFactory = new CustomizableThreadFactory(namePreFix);
                    myThreadPoolExecutor = new MyThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), customizableThreadFactory);
                    // 超时线程自动回收
                    myThreadPoolExecutor.allowCoreThreadTimeOut(true);
                }
            }
        }
        return myThreadPoolExecutor;
    }

    /**
     * 获取默认线程池信息
     * @return 线程池
     */
    public static MyThreadPoolExecutor getDefaultThreadPoolExecutor(){
        return getMyThreadPoolExecutor(THREAD_NAME_PREFIX, KEEP_ALIVE_TIME, CORE_POOL_SIZE, MAX_POOL_SIZE);
    }

    /**
     * 使用双检索单例形式创建线程池对象
     * @return 自定义线程池信息
     */
    public static MyThreadPoolExecutor getMyThreadPoolExecutor(){
        ParamConfig paramConfig = SpringUtil.getBean(ParamConfig.class);
        return getMyThreadPoolExecutor(paramConfig.getThreadNamePrefix(),paramConfig.getKeepAliveTime(),paramConfig.getCorePoolSize(),paramConfig.getMaximumPoolSize());
    }

}
