package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * 是否记录日志
 * @author zxl
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsSaveLog {

	/**
	 * 默认记录日志
	 * @return
	 */
	boolean isSave() default true;

}
