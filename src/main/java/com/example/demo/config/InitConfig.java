package com.example.demo.config;

import com.example.demo.util.LogUtil;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 全局基本参数
 * @Date 2020/7/8 10:00
 * @author zxl
 */
public class InitConfig {

    /*** 换行符 */
    public final static String NEW_LINE = "\r\n";

    /*** 配置文件 */
    private final static String SOCKET_XML = "config/sysParam.properties";

    /*** 编码配置 */
    public final static String CHARSET_UTF8 = "UTF-8";
    public final static String CHARSET_GBK = "GBK";

    /*** 配置文件中静态变量map */
    private static Map<String, String> map = null;
    public static Map<String, String> map() {
        if (map == null) {
            try {
                map = readConfig(SOCKET_XML);
            } catch (Exception e) {
                LogUtil.errInfoE(e);
                System.exit(0);
            }
        }
        return map;
    }



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

            put("5", "Notify Request");
            put("6", "Notify Response");

        }
    };

    /**
     * 读取配置文件内容
     * @param configPath 文件路径
     * @return: 文件对应map集合
     * @author: zxl
     * @date: 2020/7/8 10:28
     */
    private static Map<String, String> readConfig(String configPath) throws Exception {
        Map<String, String> list = new HashMap(50);
        InputStream in = null;
        Properties prop = new Properties();
        try {
            in = new BufferedInputStream(new FileInputStream(configPath));
            //加载属性列表
            prop.load(in);
            for (Object s : prop.keySet()) {
                list.put(s.toString(), prop.get(s).toString());
            }
            return list;
        } catch (Exception e) {
            throw e;
        } finally {
            in.close();
        }
    }

}
