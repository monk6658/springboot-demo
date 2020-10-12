package com.example.demo.handler;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义HandlerExceptionResolver对象处理异常
 * 必须实现HandlerExceptionResolver，可以返回错误信息
 * @author zxl
 * @date 2020/10/12 11:15
 */
public class GlobalException2 implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        //判断不同异常类型，做不同视图跳转

        if(e instanceof NullPointerException){
            mv.setViewName("error1");
        }
        if(e instanceof ArithmeticException){
            mv.setViewName("error2");
        }

        mv.addObject("errMsg",e.toString());

        return mv;
    }
}
