package com.example.demo.util.thread;

import com.example.demo.util.RunningData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 扩展自定义线程池，钩子函数
 *
 * @author zxl
 * @date 2021/5/24 14:39
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    /*** 显式锁(实现公平锁) */
    private final ReentrantLock lock = new ReentrantLock(true);
    /*** 暂停状态 */
    private final Condition unPaused = lock.newCondition();

    /*** 线程池全局状态标识 */
    private boolean isPaused;

    /**
     * 构造方法
     * @param corePoolSize 保持在池中的线程数，即使它们是空闲的，除非设置了allowCoreThreadTimeOut
     * @param maximumPoolSize 池中允许的最大线程数
     * @param keepAliveTime 当线程数大于核心时，这是多余的空闲线程在终止之前等待新任务的最大时间。
     * @param unit keepAliveTime参数的时间单位
     * @param workQueue 用于在任务执行之前保存任务的队列。这个队列将只保存execute方法提交的Runnable任务。
     * @param threadFactory 执行器创建新线程时使用的工厂
     */
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /**
     * 构造方法
     * @param corePoolSize 保持在池中的线程数，即使它们是空闲的，除非设置了allowCoreThreadTimeOut
     * @param maximumPoolSize 池中允许的最大线程数
     * @param keepAliveTime 当线程数大于核心时，这是多余的空闲线程在终止之前等待新任务的最大时间。
     * @param unit keepAliveTime参数的时间单位
     * @param workQueue 用于在任务执行之前保存任务的队列。这个队列将只保存execute方法提交的Runnable任务。
     */
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 构造方法
     * @param corePoolSize 保持在池中的线程数，即使它们是空闲的，除非设置了allowCoreThreadTimeOut
     * @param maximumPoolSize 池中允许的最大线程数
     * @param keepAliveTime 当线程数大于核心时，这是多余的空闲线程在终止之前等待新任务的最大时间。
     * @param unit keepAliveTime参数的时间单位
     * @param workQueue 用于在任务执行之前保存任务的队列。这个队列将只保存execute方法提交的Runnable任务。
     * @param handler 当执行被阻塞时使用的处理程序，因为达到了线程边界和队列容量
     */
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    /**
     * 构造方法
     * @param corePoolSize 保持在池中的线程数，即使它们是空闲的，除非设置了allowCoreThreadTimeOut
     * @param maximumPoolSize 池中允许的最大线程数
     * @param keepAliveTime 当线程数大于核心时，这是多余的空闲线程在终止之前等待新任务的最大时间。
     * @param unit keepAliveTime参数的时间单位
     * @param workQueue 用于在任务执行之前保存任务的队列。这个队列将只保存execute方法提交的Runnable任务。
     * @param threadFactory 执行器创建新线程时使用的工厂
     * @param handler 当执行被阻塞时使用的处理程序，因为达到了线程边界和队列容量
     */
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 钩子方法，执行任务前执行
     * @param t 运行任务r的线程
     * @param r 要执行的任务
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        lock.lock();
        try {
            while (isPaused) {
                unPaused.await(); // 阻塞挂起，释放锁
            }
            // 每次线程执行前，清除当前线程变量
            RunningData.flushAll();
            long start = System.nanoTime();
            System.out.println("当前线程："+ t.getThreadGroup().getName() + " " + t.getName()+ "正准备执行任务......start " + start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    /**
     * 钩子方法，执行任务后执行
     * @param t 运行任务r的线程
     * @param r 要执行的任务
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long end = System.nanoTime();
        System.out.println("当前线程："+Thread.currentThread().getName()+ "执行任务结束......end " + end);
    }

    /**
     * 线程执行完成
     */
    @Override
    protected void terminated() {
        super.terminated();
    }

    /**
     * 线程池暂停
     */
    private void pause() {
        lock.lock();
        try {
            isPaused = true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 线程池恢复
     */
    private void resume() {
        lock.lock();
        try {
            isPaused = false;
            unPaused.signalAll(); // 通知唤醒
        } finally {
            lock.unlock();
        }
    }


    /**
     * 自定义拒绝策略
     */
    public static class MyRejectedHandler implements RejectedExecutionHandler {
        private final Logger loggerEr = LogManager.getLogger("sysPack");

        public MyRejectedHandler() { }

        /**
         * 自定义拒绝策略
         * @param r 请求被执行的可运行任务
         * @param e 试图执行此任务的执行器
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            loggerEr.error("task " + r.toString() + " reject from " + e.toString());
            if (!e.isShutdown()) {
                r.run();
            }
        }
    }


}
