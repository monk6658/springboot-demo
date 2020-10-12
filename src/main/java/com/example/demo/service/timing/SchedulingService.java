package com.example.demo.service.timing;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

/**
 * 定时任务
 * @author zxl
 * @date 2020/7/27 10:19
 */
@Service
public class SchedulingService {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 从 spring 容器中注入自定义的 redisTemplate
     */
    @Resource(name = "tPosRedisTemplate")
    private RedisTemplate redisTemplate;

    @Scheduled(initialDelay = 2000,fixedDelay = 50000)
    private void testS(){
//        for (int i = 0; i < 50000; i++){
//            threadPoolTaskExecutor.execute(() -> getCache());
//        }
    }

    /**
     * 获得缓存
     */
    private void getCache(){
        Set<String> set = redisTemplate.keys("*");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String str = it.next();
            System.out.print(Thread.currentThread().getName() + " " + str + " ");
            System.out.print(redisTemplate.opsForValue().get(str) + "\n");
            str = "";
        }
        //变量置空，垃圾回收
        it.remove();
        set.clear();
    }

}
