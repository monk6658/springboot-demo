package com.example.demo.util;

import io.netty.util.concurrent.FastThreadLocal;

/**
 * 当前线程共享变量
 * @date 2020-07-08
 * @author zxl
 */
public class RunningData {

    /*** 系统凭证号*/
    private static FastThreadLocal<String> serialNum = new FastThreadLocal<String>();
    /*** 请求方法名*/
    private static FastThreadLocal<String> reqMethod = new FastThreadLocal<String>();

    public static String getReqMethod() {
        return reqMethod.get();
    }

    public static void setReqMethod(String methodName) {
        reqMethod.set(methodName);
    }

    public static String getSerial() {
        return serialNum.get();
    }

    public static void setSerial(String serial) {
        serialNum.set(serial);
    }

    /*** （每条线程进来前，必须清除不然串线程）清除缓存*/
    public static void flushAll() {
        FastThreadLocal.removeAll();
    }
}
