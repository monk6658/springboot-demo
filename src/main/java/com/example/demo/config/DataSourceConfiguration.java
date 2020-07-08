package com.example.demo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据库配置类
 * @author zxl
 * @date 2019-09-25 15:43
 */
@Configuration
public class DataSourceConfiguration {
    /**
     * 定义创建数据源方法
     */
    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.c3p0")
    public ComboPooledDataSource getDataSource()  {

        // 创建数据源构建对象
        return DataSourceBuilder.create()
                // 设置数据源类型
                .type(ComboPooledDataSource.class)
                // 构建数据源对象
                .build();
    }


}
