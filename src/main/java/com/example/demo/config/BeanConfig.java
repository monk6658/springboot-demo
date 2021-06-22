package com.example.demo.config;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * bean 注入工具管理
 * @Date 2020/6/29 14:41
 * @author zxl
 */
@Component
public class BeanConfig {

    @Resource
    private ParamConfig paramConfig;


}
