package com.zdl.spring.bus;

import java.util.List;

/**
 * 消息总线中的消息数据结构
 *
 * Created by ZDLegend on 2019/4/10 11:28
 */
public class BusMessage<T> {

    /**
     * 消息操作类型
     */
    private int operation;

    /**
     * 端点标识
     */
    private String endPointId;

    /**
     * 消息来源
     */
    private String source;

    /**
     * 消息目标
     */
    private String target;

    /**
     * 消息内容
     */
    private List<T> data;

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getEndPointId() {
        return endPointId;
    }

    public void setEndPointId(String endPointId) {
        this.endPointId = endPointId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
