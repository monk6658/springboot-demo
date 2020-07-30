package com.example.demo.controller;

import com.example.demo.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试
 * @author zxl
 * @date 2020/7/8 14:02
 */
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/")
    public String index() throws Exception {
        throw new Exception("扔出异常测试");
    }

    @RequestMapping("/indexs")
    public String indexs(){
        return ResultUtil.success("英文测sd试");
    }

}
