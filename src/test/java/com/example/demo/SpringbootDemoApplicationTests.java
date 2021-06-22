package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootDemoApplicationTests {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void testTemplate(){
        redisTemplate.opsForValue().set("5","2");
    }

}
