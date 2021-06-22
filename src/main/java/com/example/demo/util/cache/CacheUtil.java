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

    @Resource
    private RedisTemplate redisTemplate;

    private Object getKey(String key){
        return redisTemplate.opsForValue().get(key);
    }









}
