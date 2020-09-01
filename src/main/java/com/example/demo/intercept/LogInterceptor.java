package com.example.demo.intercept;

import com.example.demo.annotation.IsSaveLog;
import com.example.demo.util.LogUtil;
import com.example.demo.util.RunningData;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 * @author zxl
 * @date 2019-12-18 15:46
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setCharacterEncoding("UTF-8");
        RunningData.flushAll();//清空
        RunningData.setSerial(String.valueOf(System.currentTimeMillis()));
        RunningData.setReqMethod(request.getRequestURI());
        LogUtil.saveLog(request,"1");

        if(handler instanceof HandlerMethod){
            IsSaveLog lm =  ((HandlerMethod) handler).getMethodAnnotation(IsSaveLog.class);
            System.out.println("lm is " + lm);
            if(lm.isSave()){
                return false;
            }
        }


        return true;
    }

}
