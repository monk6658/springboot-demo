package com.example.demo.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * 全局异常 页面跳转统一处理(异常和视图映射，不能携带信息)
 * @author zxl
 * @date 2020/10/12 11:10
 */
@Configuration
public class GlobalException1 {


    @Bean
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();

        /**
         * 参数一：异常类型，并且是全名
         * 参数二：视图名称
         */
        properties.put("java.lang.MethodArgumentNotValidException","error");
        properties.put("java.lang.Exception","error2");
        resolver.setExceptionMappings(properties);
        return resolver;
    }

}
