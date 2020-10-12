package com.example.demo.controller;

import com.example.demo.po.MyTest;
import com.example.demo.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 测试
 * @author zxl
 * @date 2020/7/8 14:02
 */
@Controller
@Validated
public class TestController {

    @ResponseBody
    @RequestMapping("/aa")
    public String index() throws Exception {
        throw new Exception("扔出异常测试");
    }

    @RequestMapping("/indexs")
    public String indexs(){
        return ResultUtil.success("英文测sd试");
    }

    @RequestMapping("/a")
    public String a(@Valid MyTest myTest){
        System.out.println(myTest.toString());
        return ResultUtil.success("英文测sd试");
    }


    /**
     * 需要在controller加上@Validated开启校验规则
     * 不如直接使用@RequestParam 设置参数必须，拦截抛出异常返回
     * @param a 参数
     * @return
     */
    @RequestMapping("/b")
    public String b(@NotBlank(message = "不为空") String a){
        System.out.println(a);
        return ResultUtil.success("英文测sd试");
    }

}
