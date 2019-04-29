package com.zdl.spring.bus.message;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.zdl.spring.bus.endpoint.EndpointManage.CALLBACK_EXCEPTION;
import static com.zdl.spring.bus.endpoint.EndpointManage.CALLBACK_SUCCESS;

/**
 * 消息总线中的消息数据结构
 * <p>
 * Created by ZDLegend on 2019/4/10 11:28
 */
public class BusMessage<T> {

    /**
     * 消息id
     */
    private String id;

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
    private List<String> targets = new ArrayList<>();

    /**
     * 消息内容
     */
    private List<T> data;

    /**
     * 是否回调
     */
    private boolean callBack;

    private BusMessage() {
    }

    public static <T> BusMessage<T> instance(List<T> data) {
        BusMessage<T> message = new BusMessage<>();
        message.id = UUID.randomUUID().toString();
        message.data = data;
        message.callBack = false;
        return message;
    }

    public static BusMessage<String> callBackInstance(String id) {
        BusMessage<String> message = new BusMessage<>();
        message.id = id;
        message.callBack = true;
        return message;
    }

    public static BusMessage<String> callBackFailInstance(String id) {
        return callBackInstance(id).operation(CALLBACK_EXCEPTION);
    }

    public static BusMessage<String> callBackSuccessInstance(String id) {
        return callBackInstance(id).operation(CALLBACK_SUCCESS);
    }

    public BusMessage<T> source(String source) {
        this.source = source;
        return this;
    }

    public BusMessage<T> targets(List<String> targets) {
        this.targets = targets;
        return this;
    }

    public BusMessage<T> endPointId(String endPointId) {
        this.endPointId = endPointId;
        return this;
    }

    public BusMessage<T> operation(int operation) {
        this.operation = operation;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        if (CollectionUtils.isEmpty(targets)) {
            this.targets = new ArrayList<>();
        } else {
            this.targets = targets;
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isCallBack() {
        return callBack;
    }

    public void setCallBack(boolean callBack) {
        this.callBack = callBack;
    }
}
