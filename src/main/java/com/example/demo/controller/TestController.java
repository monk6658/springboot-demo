package com.example.demo.controller;

import com.example.demo.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * @author zxl
 * @date 2020/7/8 14:02
 */
@RestController
public class TestController {

    @RequestMapping("/")
    public String index(){
        return ResultUtil.success("英文测试");
    }


}
