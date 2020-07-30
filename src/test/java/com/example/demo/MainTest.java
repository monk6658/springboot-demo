package com.example.demo;

import java.io.*;

/**
 * main方法测试
 * @author zxl
 * @date 2020/7/23 9:25
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
        read();
        inReader();
        bufferReader();
    }


    /**
     * 文件流
     * @throws Exception
     */
    private static void read() throws Exception {
        //创建流对象
        String path = "D:\\c.txt";
        String newPath = "D:\\c1.txt";
        InputStream is = new FileInputStream(new File(path));
        OutputStream os = new FileOutputStream(newPath);

        //读取流对象内容
        byte[] bu = new byte[1024];
        int len = -1;
        while ((len = is.read(bu)) != -1){
            System.out.println(new String(bu,"gbk"));
            os.write(bu,0,len);
        }

//        os.flush();
        os.close();
        is.close();


    }
    /**
     * 字符流
     * @throws Exception
     */
    private static void inReader() throws Exception {
        //创建流对象
        String path = "D:\\c.txt";
        String newPath = "D:\\c1.txt";
        /*** FileReader 直接获取文件内容，会出现乱码。*/
//        Reader is = new FileReader(new File(path));
//        Writer os = new FileWriter(new File(newPath));
//        System.out.println(System.getProperty("file.encoding"));
//        System.setProperty("file.encoding","GBK");
//        System.out.println(System.getProperty("file.encoding"));

        InputStreamReader isr = new InputStreamReader(new FileInputStream(path),"gbk");
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(newPath),"gbk");

        int len = -1;
        //读取流对象内容
        char[] bu = new char[16];
        while ((len = isr.read(bu)) != -1){
            System.out.print(new String(bu,0,len));
            osw.write(bu,0,len);
        }

        System.out.println();
        osw.close();
        isr.close();
    }
    /**
     * 缓存字节流
     * @throws Exception
     */
    private static void bufferReader() throws Exception {
        //创建流对象
        String path = "D:\\c.txt";
        String newPath = "D:\\c2.txt";

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newPath));

        byte[] buffer = new byte[1024];
        int len = -1; // 文件结尾
        while((len = bis.read(buffer)) != -1){
            System.out.println(new String(buffer,"gbk"));
            bos.write(buffer,0,len);
        }

        bos.close();
        bis.close();
    }


}
