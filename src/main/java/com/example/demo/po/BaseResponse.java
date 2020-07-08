package com.example.demo.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * 外放接口统一返回
 * @author zxl
 * @date  2019-12-18 15:03
 */
@JsonSerialize
public class BaseResponse<T> implements Serializable {

    /** 请求成功返回码为：00 */
    public static final String successCode = "00";
    /** 请求成功返回信息为：成功 */
    public static final String successMsg = "成功";
    /** 请求失败返回码为：-1 */
    public static final String errorCode = "-1";
    /*** 请求失败信息 */
    public static final String errorMsg = "系统异常";
    /** 流水号 */
    private String serial;
    /** 返回码 */
    private String code;
    /** 返回描述 */
    private String msg;
    /** 返回数据 */
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(String serial, String code, String msg, T data) {
        this.serial = serial;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
