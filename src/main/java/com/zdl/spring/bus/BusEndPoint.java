package com.zdl.spring.bus;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 消息总线端点
 *
 * Created by ZDLegend on 2019/4/10 11:11
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BusEndPoint {

    /**
     * 端点标识
     */
    String value();

    /**
     * 初始化顺序支持，按由小到大顺序，默认值99999
     */
    int order() default 99999;
}
