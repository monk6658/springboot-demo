package com.example.demo.po;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 校验配置类使用
 * NotNull:对基本数据类型的对象做非空校验
 * NotBlank: 对字符串做非空校验
 * NotEmpty: 对集合类型
 * @author zxl
 * @date 2020/10/12 11:29
 */
@Getter
@Setter
public class MyTest {

    //使用自定义
    @NotBlank(message = "实体类校验")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
