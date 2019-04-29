package com.zdl.spring.bus;

/**
 * Created by ZDLegend on 2019/4/10 16:25
 */
public class KafkaBusException extends RuntimeException {
    public KafkaBusException(String msg) {
        super(msg);
    }

    public KafkaBusException(String msg, Throwable t) {
        super(msg, t);
    }
}
