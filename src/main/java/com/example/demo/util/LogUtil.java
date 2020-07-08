package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.InitConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Map;

/**
 * 日志工具类
 * @author  zxl
 * @date 2019-9-19 下午4:04:35
 */
public class LogUtil {
    /*** 错误日志 */
    private final static Logger SYS_ERROR = LogManager.getLogger("errorPack");
    /*** 正常日志 */
    private final static Logger HTTP_NORMAL = LogManager.getLogger("normal");


    /**
     * 保存正常日志
     * @param jsonObject 日志对象
     * @param type 日志类型
     */
    public static void saveLog(JSONObject jsonObject,String type){
        StringBuffer logMsg = commonLog(type);
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            logMsg.append("[" + entry.getKey() + "]：" + entry.getValue()).append(InitConfig.NEW_LINE);
        }
        HTTP_NORMAL.info(logMsg.toString());
    }

    /**
     * 保存请求日志
     * @author zxl
     * @time 2020/3/5 16:51
     */
    public static void saveLog(final HttpServletRequest request, String type) {
        StringBuffer logMsg = commonLog(type);
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                logMsg.append("[" + en + "]：" + request.getParameter(en)).append(InitConfig.NEW_LINE);
            }
        }
        HTTP_NORMAL.info(logMsg.toString());
    }

    /**
     * 保存正常日志
     * @param info 日志对象
     * @param type 日志类型
     */
    public static void saveLog(String info,String type){
        StringBuffer logMsg = commonLog(type);
        logMsg.append(info).append(InitConfig.NEW_LINE);
        HTTP_NORMAL.info(logMsg.toString());
    }


    /**
     * 公共日志记录
     * @param type 日志类型
     * @return
     */
    private static StringBuffer commonLog(String type){
        StringBuffer logMsg = new StringBuffer();
        logMsg.append("[凭证编号]：").append(RunningData.getSerial()).append(InitConfig.NEW_LINE);
        logMsg.append("[日志类型]：").append(InitConfig.LOG_TYPE.get(type)).append(InitConfig.NEW_LINE);
        logMsg.append("[请求方法]：").append(RunningData.getReqMethod()).append(InitConfig.NEW_LINE);
        logMsg.append("[完整报文]：").append(InitConfig.NEW_LINE);
        return logMsg;
    }









    /**
     * 记录错误日志
     * @param e
     * @author zxl
     */
    public static void errInfoE(Exception e) {
        StringBuffer logMsg = new StringBuffer();
        logMsg.append("[Serial]:[").append(RunningData.getSerial()).append("]").append(InitConfig.NEW_LINE);
        logMsg.append("[Exp]:").append(changeExtInfo(e));
        SYS_ERROR.error(logMsg);
    }

    /**
     * 记录错误日志
     * @param e
     * @author zxl
     */
    public static void errInfoE(String e) {
        StringBuffer logMsg = new StringBuffer();
        logMsg.append("[凭证编号]").append(RunningData.getSerial()).append(InitConfig.NEW_LINE);
        logMsg.append("[异常]").append(e).append(InitConfig.NEW_LINE);
        SYS_ERROR.info(logMsg);
    }

    /**
     * 记录错误日志
     * @param info 错误信息
     * @param e 错误内容
     * @author zxl
     */
    public static void errInfoE(String info,Exception e) {
        StringBuffer logMsg = new StringBuffer();
        logMsg.append("[凭证编号]").append(RunningData.getSerial()).append(InitConfig.NEW_LINE);
        logMsg.append("[异常]").append(info).append(InitConfig.NEW_LINE);
        logMsg.append(changeExtInfo(e));
        SYS_ERROR.info(logMsg);
    }

    /**
     * 记录错误日志
     * @param e
     * @author zxl
     */
    public static void errInfoS(Throwable e) {
        StringBuffer logMsg = new StringBuffer();
        logMsg.append("[Serial]:[").append(RunningData.getSerial());
        logMsg.append("]").append(InitConfig.NEW_LINE);
        logMsg.append("[Exp]:").append(changeExtInfo(e));
        SYS_ERROR.info(logMsg);
    }

    /**
     * 处理异常信息
     * @param e
     * @return
     */
    private static String changeExtInfo(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 处理异常信息
     * @param e
     * @return
     */
    private static String changeExtInfo(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }



}
