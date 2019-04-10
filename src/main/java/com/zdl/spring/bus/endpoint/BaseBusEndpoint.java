package com.zdl.spring.bus.endpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zdl.spring.bus.message.BusMessage;
import com.zdl.spring.bus.utils.ClassUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;

import static com.zdl.spring.bus.endpoint.EndpointManage.*;

/**
 * 消息总线端点基类
 * <p>
 * Created by ZDLegend on 2019/4/10 11:11
 */
public interface BaseBusEndpoint<T> {

    /**
     * 初始化（系统启动时调用）
     */
    void init();

    /**
     * 消息内容添加操作
     */
    void insert(List<T> list);

    /**
     * 消息内容修改操作, 默认调用资源添加操作方法
     */
    default void modify(List<T> list) {
        insert(list);
    }

    /**
     * 消息内容加载操作, 默认调用资源添加操作方法
     */
    default void load(List<T> list) {
        insert(list);
    }

    /**
     * 消息内容资源删除操作
     */
    void delete(List<T> list);

    @SuppressWarnings("unchecked")
    default void messageToEndPoint(BusMessage message) {

        int operation = message.getOperation();
        Consumer<List<T>> handle;
        switch (operation) {
            case OPERATION_ADD:
                handle = this::insert;
                break;
            case OPERATION_MODIFY:
                handle = this::modify;
                break;
            case OPERATION_LOAD:
                handle = this::load;
                break;
            case OPERATION_DELETE:
                handle = this::delete;
                break;
            default:
                handle = this::insert;
        }

        List<T> list;
        if (!CollectionUtils.isEmpty(message.getData()) && message.getData().get(0) instanceof JSONObject) {
            list = JSON.parseArray((new JSONArray((List<Object>) message.getData())).toJSONString(),
                    (Class<T>) ClassUtil.getGenericType(this.getClass()));
        } else {
            list = message.getData();
        }

        handle.accept(list);
    }
}
