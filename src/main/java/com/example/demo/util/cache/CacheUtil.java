package com.example.demo.util.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 缓存工具类
 * @author zxl
 * @date 2020/7/23 16:47
 */
@Component
public class CacheUtil {

    @Resource(name = "tPosRedisTemplate")
    private RedisTemplate redisTemplate;

    private String getKey(String key,RedisTemplate redisTemplate){
        return (String) redisTemplate.opsForValue().get(key);
    }

    public String getTposKey(String key){
        return getKey(key,redisTemplate);
    }








}
