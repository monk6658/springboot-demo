package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.po.BaseResponse;

/**
 * 全局返回工具类
 * @date  2020/6/28 16:22
 * @author zxl
 */
public class ResultUtil {


    /**
     * 成功返回
     * @param object 返回对象信息
     * @return
     */
    public static String success(Object object) {
        BaseResponse result = new BaseResponse();
        result.setSerial(RunningData.getSerial());
        result.setCode(BaseResponse.successCode);
        result.setMsg(BaseResponse.successMsg);
        result.setData(object);
        return JSON.toJSONString(result);
    }


    /**
     * 错误返回
     * @param code 错误码
     * @param msg 错误信息
     * @return
     */
    public static String error(String code, String msg){
        BaseResponse result = new BaseResponse();
        result.setSerial(RunningData.getSerial());
        result.setCode(code);
        result.setMsg(msg);
        return JSON.toJSONString(result);
    }

    /**
     * 错误返回
     * @param msg 错误信息
     * @return
     */
    public static String error(String msg){
        BaseResponse result = new BaseResponse();
        result.setSerial(RunningData.getSerial());
        result.setCode(BaseResponse.errorCode);
        result.setMsg(msg);
        return JSON.toJSONString(result);
    }



}
