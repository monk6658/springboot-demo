package com.example.demo.util.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 * @author zxl
 * @date 2020/7/23 16:47
 */
@Component
public class CacheUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取String 类型的key
     * @param key 键
     * @return 值
     */
    public String getKey(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置键值
     * @param key 键
     * @param value 值
     */
    public void setKey(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }


    /**
     * 设置键值
     * @param key 键
     * @param value 值
     * @param timeOut 失效时间
     * @param timeUnit 时间单位
     */
    public void setKey(String key,String value,Long timeOut,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,timeOut,timeUnit);
    }

    /**
     * 设置键值(默认分钟)
     * @param key 键
     * @param value 值
     * @param timeOut 失效时间
     */
    public void setKey(String key,String value,int timeOut){
        setKey(key,value, (long) timeOut,TimeUnit.MINUTES);
    }

    /**
     * 发布者
     * @param channel 主体
     * @param value 值
     */
    public void convertAndSend(String channel,String value){
        redisTemplate.convertAndSend(channel,value);
    }

    /**
     * 加入有序队列
     * @param key 键
     * @param value 值
     * @param score 分数
     * @return 执行结果
     */
    public Boolean zSetAdd(String key,String value,Long score){
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    /**
     * 检索有序队列
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     */
    public Set<Object> zSetRangeByScore(String key, double min, double max){
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 有序队列 新增，并且原score+delta ， 不存则创建新的，score 默认为delta
     * @param key 键
     * @param value 值
     * @param delta 默认 或 + delta
     */
    public void zSetIncrementScore(String key, String value, double delta){
        redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 有序队列 新增，并且原score+1 ， 不存则创建新的，score 默认为 1
     * @param key 键
     * @param value 值
     */
    public void zSetIncrementScore(String key,String value){
        zSetIncrementScore(key, value, 1);
    }

    /**
     * 有序队列 在score范围内清除
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     */
    public void zSetRemoveRangeByScore(String key,double min,double max){
        redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
    }

    /**
     * 有序队列 清除 values
     * @param key 键
     * @param values 值
     */
    public void zSetRemove(String key, Object... values){
        redisTemplate.opsForZSet().remove(key,values);
    }

    /**
     * 设置失效时间
     * @param key 键值
     * @param timeOut 失效时间
     * @param timeUnit 失效单位
     * @return 结果
     */
    public Boolean expire(String key, Long timeOut,TimeUnit timeUnit){
        return redisTemplate.expire(key, timeOut, timeUnit);
    }

    /**
     * 设置失效时间(默认分钟)
     * @param key 键值
     * @param timeOut 失效时间
     * @return 结果
     */
    public Boolean expire(String key, Long timeOut){
        return expire(key, timeOut, TimeUnit.MINUTES);
    }


    /**
     * 加入有序队列
     * @param key 键
     * @param value 值
     * @param score 分数
     * @return 执行结果
     */
    public Boolean zHAdd(String key,String value,Long score){
        return redisTemplate.opsForZSet().add(key,value,score);
    }









}
