package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@EnableCaching
class SpringbootDemoApplicationTests {

    @Autowired
    HttpUtil httpUtil;

    @Resource(name = "notifyRedisTemplate")
    StringRedisTemplate notifyRedisTemplate;

    @Test
    void testCache(){
        Set<String> set = notifyRedisTemplate.keys("X*");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String str = it.next();
            System.out.println(str + " " + notifyRedisTemplate.opsForValue().get(str));
        }
    }


    @Test
    void contextLoads() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("测试","sd");
        jsonObject.put("测试1","sd");
        System.out.println(httpUtil.sendPost("http://www.baidu.com",jsonObject.toJSONString()));;
    }

    @Test
    public void testREST() {
        String url = "http://127.0.0.1:1024";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("siz","1");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(jsonObject, httpHeaders);

        String json = restTemplate.postForObject(url,request, String.class);


        System.out.println(json);
    }


}
