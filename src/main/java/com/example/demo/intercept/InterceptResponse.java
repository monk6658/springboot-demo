package com.example.demo.intercept;

import com.example.demo.util.LogUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.lang.reflect.Method;

/**
 * 统一处理一下@ResponseBody注解的返回值
 * @author zxl
 * @date 2019-12-18 15:46
 */
@ControllerAdvice
public class InterceptResponse implements ResponseBodyAdvice<Object> {

    private String noMethod = "consumeIntegral";

    /**
     * 拦截策略的配置
     * @param returnType 方法
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = returnType.getMethod();

        //方法名为consumeIntegral的，不进行拦截
        if(noMethod.equals(method.getName())){
            return false;
        }
        return true;
    }

    /**
     * 此方法是拦截返回值,并且操作返回值的,这是一个全局过滤
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        try {
            LogUtil.saveLog(body.toString(), "2");
        }catch (Exception e){
            LogUtil.errInfoE(body.toString(),e);
        }
        return body;
    }

}