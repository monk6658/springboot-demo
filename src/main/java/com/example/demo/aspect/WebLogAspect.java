package com.example.demo.aspect;

import com.example.demo.annotation.IsSaveLog;
import com.example.demo.util.LogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * web 切面(必须有@Component 不然不会走切点 )
 * aop只会切 spring管理的类
 * @author zxl
 * @date 2020/7/10 11:07
 */
@Aspect
@Component
public class WebLogAspect {

    /**
     * 切入点描述 这个是controller包的切入点
     */
    @Pointcut("execution(public * com.example.demo.controller..*.*(..))")
    public void controllerLog(){}//签名，可以理解成这个切入点的一个名称

    /**
     * 切入自定义注解内容
     */
    @Pointcut(value = "@annotation(com.example.demo.annotation.IsSaveLog)")
    public void httpLog(){ }


    /**
     * 在切入点的方法run之前要干的
     * @param joinPoint
     */
    @Before("controllerLog()")
    public void logBeforeController(JoinPoint joinPoint) {
        System.out.println("AOP before ："+"即进入方法前");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 后置切入，不影响主业务
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        LogUtil.saveLog(ret.toString(), "2");
    }


    /**
     * 切入http日志记录,并记录
     */
    @Before("httpLog() && @annotation(isSaveLog)")
    public void beforeHttpLog(JoinPoint joinPoint, IsSaveLog isSaveLog){
        if(isSaveLog.isSave()){
            System.out.println(isSaveLog.isSave());
            System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
            System.out.println("目标方法所属类的简单类名:" +        joinPoint.getSignature().getDeclaringType().getSimpleName());
            System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
            System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
            //获取传入目标方法的参数
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                System.out.println("第" + (i+1) + "个参数为:" + args[i]);
            }
            System.out.println("被代理的对象:" + joinPoint.getTarget());
            System.out.println("代理对象自己:" + joinPoint.getThis());
        }

    }

    /**
     * 后置增强、资源释放，不影响主业务
     * @param isSaveLog
     * @param rvt
     */
    @AfterReturning(returning="rvt",pointcut = "httpLog() && @annotation(isSaveLog)")
    public void afterHttpLog(IsSaveLog isSaveLog,Object rvt){

        // 返回值
        System.out.println(rvt.toString());
    }

}
