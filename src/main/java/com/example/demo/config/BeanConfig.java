package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * bean 注入工具管理
 * @Date 2020/6/29 14:41
 * @author zxl
 */
@Component
public class BeanConfig {

    @Resource
    private ParamConfig paramConfig;

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

    /**
     * 定时任务启动线程数
     * @author zxl
     * @time 2020/3/18 14:29
     */
    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //设置线程池大小
        taskScheduler.setPoolSize(paramConfig.getPoolSize());
        return taskScheduler;
    }

    /**
     * 默认线程池线程池
     *
     * @return Executor
     */
    @Bean
    public ThreadPoolTaskExecutor defaultThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(paramConfig.getCorePoolSize());
        //指定最大线程数
        executor.setMaxPoolSize(paramConfig.getMaxPoolSize());
        //队列中最大的数目
        executor.setQueueCapacity(paramConfig.getQueueCapacity());
        //线程名称前缀
        executor.setThreadNamePrefix(paramConfig.getThreadName());
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(paramConfig.getKeepAliveSeconds());
        //是否允许空闲核心线程超时
        executor.setAllowCoreThreadTimeOut(true);
        //加载
        executor.initialize();
        return executor;
    }

    /**
     * redis 公共参数配置
     * @return
     */
    @Order(2)
    @Bean(name = "JedisPoolConfig")
    public JedisPoolConfig defaultJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(paramConfig.getMaxIdle());// 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxTotal(paramConfig.getMaxActive());//最大连接数, 默认8个
        jedisPoolConfig.setMinIdle(paramConfig.getMinIdle());// 最小空闲连接数, 默认0
        jedisPoolConfig.setTestOnBorrow(false);//在获取连接的时候检查有效性, 默认false
        jedisPoolConfig.setJmxEnabled(true); // 是否开启jmx监控，可用于监控 默认值 true 建议开启，但应用本身也要开启

        // 在还会给pool时，是否提前进行validate操作
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true); //是否开启空闲资源监测, 默认值 false, 建议值 true
        jedisPoolConfig.setMinEvictableIdleTimeMillis(10000);//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(10000);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(10000);// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setNumTestsPerEvictionRun(5); //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setMaxWaitMillis(1000 * paramConfig.getMaxWaitMillis());// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        return jedisPoolConfig;
    }

    /**
     * redis 工厂
     * @param host ip
     * @param port 端口
     * @param password 密码
     * @param index 库
     * @return
     */
    private RedisConnectionFactory connectionFactory(String host, int port, String password,String index,JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        if (!StringUtils.isEmpty(index)) {
            jedisConnectionFactory.setDatabase(Integer.valueOf(index));
        }
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    /**
     * 处理redis 缓存序列化操作
     * @param template
     * @return
     */
    private StringRedisTemplate dealRedisTemplate(StringRedisTemplate template){
        Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        redisSerializer.setObjectMapper(om);
        template.setKeySerializer(stringRedisSerializer);
//        template.setValueSerializer(redisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(redisSerializer);
        return template;
    }

    @Bean(name = "tPosRedisTemplate")
    public StringRedisTemplate tPosRedisTemplate(@Qualifier(value = "JedisPoolConfig")JedisPoolConfig jedisPoolConfig) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory(paramConfig.gettPosHost(),
                paramConfig.gettPosPort(),
                paramConfig.gettPosPassword(), paramConfig.getNotifySel(),jedisPoolConfig));
        return dealRedisTemplate(template);
    }

    @Bean(name = "notifyRedisTemplate")
    public StringRedisTemplate notifyRedisTemplate(@Qualifier(value = "JedisPoolConfig")JedisPoolConfig jedisPoolConfig) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory(paramConfig.getNotifyHost(),
                paramConfig.getNotifyPort(),
                paramConfig.getNotifyPassword(), paramConfig.getNotifySel(),jedisPoolConfig));
        return dealRedisTemplate(template);
    }

}
