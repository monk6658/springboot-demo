package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置文件
 * @date 2020/7/1 13:35
 * @author zxl
 */
@Configuration
@PropertySource(value = "${sys-param.path}")
@ConfigurationProperties(prefix = "sys")
@Getter
@Setter
public class ParamConfig {

    /**** 线程池配置信息 */
    private String threadNamePrefix;
    private long keepAliveTime;
    private int corePoolSize;
    private int maximumPoolSize;

    /*** 定时任务线程池配置 */
    private String timingNamePrefix;
    private int timingCorePoolSize;


}
