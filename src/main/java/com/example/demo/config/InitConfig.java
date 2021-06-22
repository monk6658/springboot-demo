package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局基本参数
 * @Date 2020/7/8 10:00
 * @author zxl
 */
public class InitConfig {

    /*** 换行符 */
    public final static String NEW_LINE = "\r\n";

    /*** 编码配置 */
    public final static String CHARSET_UTF8 = "UTF-8";
    public final static String CHARSET_GBK = "GBK";

    /**
     * log对应信息
     */
    public final static Map<String, String> LOG_TYPE = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("1", "Pos Request");
            put("2", "Pos Response");
            put("3", "Channel Request");
            put("4", "Channel Response");

        }
    };

}
