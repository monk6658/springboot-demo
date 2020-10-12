//package com.example.demo.config;
//
//import com.example.demo.intercept.LogInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * 拦截器
// * @author zxl
// * @date  2019-12-18 16:24
// */
//@EnableWebMvc
//@Configuration
//public class IntercepterConfiguration implements WebMvcConfigurer {
//
//    @Autowired
//    private LogInterceptor logInterceptor;
//
//
//    /**
//     * 添加拦截器信息
//     * @author zxl
//     * @time 2020/2/24 16:07
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        //拦截器
//        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
//
//        //添加拦截器
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
//
////    /**
////     * 字符编码转换器（增加则，不能接受json）
////     * @author zxl
////     * @date 2020/2/24 16:03
////     */
////    @Override
////    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
////        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
////        converters.add(converter);
////    }
//
//}
//
