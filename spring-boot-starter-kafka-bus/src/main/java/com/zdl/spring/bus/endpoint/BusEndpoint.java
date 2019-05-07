package com.zdl.spring.bus.endpoint;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 消息总线端点
 * <p>
 * Such classes are considered as candidates for auto-detection
 * when using annotation-based configuration and classpath scanning.
 * <p>被该注解注解的类会交给spring context管理
 * <p>该注解需要和{@link BaseBusEndpoint}interface搭配使用
 * <p>
 * Created by ZDLegend on 2019/4/10 11:11
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BusEndpoint {

    /**
     * 端点标识
     */
    String value();

    /**
     * 初始化顺序支持，按由小到大顺序，默认值99999
     */
    int order() default 99999;

    /**
     * 指定接收来源（为空则接受所有）
     */
    String[] accept() default {};

    /**
     * 是否支持回调
     */
    boolean callback() default false;
}
