package com.example.demo.util.http;

import com.example.demo.annotation.IsSaveLog;
import com.example.demo.config.InitConfig;
import com.example.demo.util.LogUtil;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;

/**
 * http 请求类
 * @author zxl
 * @date 2020/4/13 16:20
 */
@Component
public class HttpUtil {

    private final String reqType = "https";

    /*** 目标地址 */
    private URL url;

    /*** 通信连接超时时间*/
    private int connectionTimeout;

    /*** 通信读超时时间 */
    private int readTimeOut;

    /*** 通信结果 */
    private String result;

    /**
     * 获取通信结果
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置通信结果
     *
     * @param result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * http默认注入属性
     */
    public HttpUtil(){

    }

    /**
     * 构造函数
     *
     * @param url               目标地址
     * @param connectionTimeout HTTP连接超时时间
     * @param readTimeOut       HTTP读写超时时间
     */
    public HttpUtil(String url,int connectionTimeout, int readTimeOut) {
        try {
            this.url = new URL(url);
            this.connectionTimeout = connectionTimeout;
            this.readTimeOut = readTimeOut;
        } catch (MalformedURLException e) {
            LogUtil.errInfoE(e);
        }
    }

    /**
     * 构造函数
     *
     * @param url  目标地址
     */
    public HttpUtil(String url) {
        try {
            this.url = new URL(url);
            this.connectionTimeout = Integer.valueOf(InitConfig.map().get("http.connectionTimeout"));
            this.readTimeOut = Integer.valueOf(InitConfig.map().get("http.readTimeOut"));
        } catch (MalformedURLException e) {
            LogUtil.errInfoE(e);
        }
    }

    /**
     * 问号传参发送post请求
     * @param reqUrl 请求地址
     * @param data 请求参数
     * @return 返回请求结果
     */
    @IsSaveLog(isSave = false)
    public String sendPost(String reqUrl,String data) {
        try {
            // 连接超时时间，读超时时间（可自行判断，修改）
            HttpUtil hc = new HttpUtil(reqUrl);
            hc.sendPostEncode(data);
            return hc.getResult();
        }catch (Exception e){
            LogUtil.errInfoE(e);
            return null;
        }
    }

    /**
     * 发送post请求 问号传参(默认utf-8编码)
     * @param data 数据
     * @return http状态
     * @throws Exception
     */
    private int sendPostEncode(String data) throws Exception{
        HttpURLConnection httpURLConnection = createConnection(InitConfig.CHARSET_UTF8);
        return sendPost(httpURLConnection,data,InitConfig.CHARSET_UTF8);
    }

    /**
     * 发送post请求 问号传参
     * @param data 数据
     * @param encoding 编码
     * @return http状态
     * @throws Exception
     */
    private int sendPostEncode(String data,String encoding) throws Exception{
        HttpURLConnection httpURLConnection = createConnection(encoding);
        return sendPost(httpURLConnection,data,encoding);
    }

    /**
     * 发送post请求 json字符串
     * @param data 数据
     * @param encoding 编码
     * @return
     * @throws Exception
     */
    private int sendPostJson(String data,String encoding) throws Exception{
        HttpURLConnection httpURLConnection = createConnectionJson(encoding);
        return sendPost(httpURLConnection,data,encoding);
    }

    /**
     * 公共发送方法类
     * @param httpURLConnection 连接
     * @param data 数据
     * @param encoding 编码
     * @return http结果
     * @throws Exception
     */
    private int sendPost(HttpURLConnection httpURLConnection,String data,String encoding) throws Exception {
        if (null == httpURLConnection) {
            throw new Exception("Create httpURLConnection Failure");
        }
        this.requestServer(httpURLConnection, data, encoding);
        this.result = this.response(httpURLConnection, encoding);
        return httpURLConnection.getResponseCode();
    }


    /**
     * 发送信息到服务端 GET方式
     *
     * @param encoding
     * @return
     * @throws Exception
     */
    public int sendGet(String encoding) throws Exception {
        try {
            HttpURLConnection httpURLConnection = createConnectionGet(encoding);
            if (null == httpURLConnection) {
                throw new Exception("创建联接失败");
            }
            this.result = this.response(httpURLConnection, encoding);
            return httpURLConnection.getResponseCode();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * HTTP Post发送消息
     *
     * @param connection
     * @param message
     * @throws IOException
     */
    private void requestServer(final URLConnection connection, String message, String encoder) throws Exception {
        PrintStream out = null;
        try {
            connection.connect();
            out = new PrintStream(connection.getOutputStream(), false, encoder);
            out.print(message);
            out.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 显示Response消息
     *
     * @param connection url连接
     * @param encoding 编码
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private String response(final HttpURLConnection connection, String encoding) throws IOException {
        InputStream in = null;
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                in = connection.getInputStream();
                sb.append(new String(read(in), encoding));
            } else {
                in = connection.getErrorStream();
                sb.append(new String(read(in), encoding));
            }
            return sb.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != br) {
                br.close();
            }
            if (null != in) {
                in.close();
            }
            if (null != connection) {
                connection.disconnect();
            }
        }
    }

    /**
     * 读取流内容
     * @param in 输入流
     * @return 流字节数组
     * @throws IOException
     */
    private static byte[] read(InputStream in) throws IOException {
        byte[] buf = new byte[1024];
        int length = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            bout.write(buf, 0, length);
        }
        bout.flush();
        return bout.toByteArray();
    }

    /**
     * 创建连接
     *
     * @return
     * @throws ProtocolException
     */
    private HttpURLConnection createConnection(String encoding) throws ProtocolException {
        HttpURLConnection httpURLConnection = createConnect(encoding);
        if(httpURLConnection == null){
            return null;
        }
        // 可读
        httpURLConnection.setDoInput(true);
        // 可写
        httpURLConnection.setDoOutput(true);
        //post 发送方式
        httpURLConnection.setRequestMethod("POST");
        return httpURLConnection;
    }

    /**
     * 创建post连接
     * @return
     * @author zxl
     * @date 2020/7/8 11:14
     */
    private HttpURLConnection createConnectionJson(String encoding) throws ProtocolException {
        HttpURLConnection httpURLConnection = createConnection(encoding);
        if (null == httpURLConnection) {
            return null;
        }
        httpURLConnection.setRequestProperty("Content-type","application/json;charset=utf-8");
        return httpURLConnection;
    }

    /**
     * 创建连接
     *
     * @return
     * @throws ProtocolException
     */
    private HttpURLConnection createConnectionGet(String encoding) throws ProtocolException {
        HttpURLConnection httpURLConnection = createConnect(encoding);
        if(httpURLConnection == null){
            return null;
        }
        httpURLConnection.setRequestMethod("GET");
        return httpURLConnection;
    }

    /**
     * 公共创建连接方式
     * @param encoding 编码格式
     * @return
     */
    private HttpURLConnection createConnect(String encoding){
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            LogUtil.errInfoE(e);
            return null;
        }
        // 连接超时时间
        httpURLConnection.setConnectTimeout(this.connectionTimeout);
        // 读取结果超时时间
        httpURLConnection.setReadTimeout(this.readTimeOut);
        // 取消缓存
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + encoding);
        if (reqType.equalsIgnoreCase(url.getProtocol())) {
            HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
            //是否验证https证书，测试环境请设置false，生产环境建议优先尝试true，不行再false
            if (!true) {
                husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
                //解决由于服务器证书问题导致HTTPS无法访问的情况
                husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
            }
            return husn;
        }
        return httpURLConnection;
    }

}
