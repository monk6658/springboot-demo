package com.example.demo.po;

import javax.validation.constraints.NotBlank;

/**
 * 校验配置类使用
 * NotNull:对基本数据类型的对象做非空校验
 * NotBlank: 对字符串做非空校验
 * NotEmpty: 对集合类型
 * @author zxl
 * @date 2020/10/12 11:29
 */
public class MyTest {

    //使用配置文件ValidationMessages.properties的类型，该文件编码必须为ascii
    @NotBlank(message = "{MyTest.id}")
    private String id;

    //使用自定义
    @NotBlank(message = "实体类校验")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
