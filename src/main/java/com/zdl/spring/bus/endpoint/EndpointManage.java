package com.zdl.spring.bus.endpoint;

import com.alibaba.fastjson.JSON;
import com.zdl.spring.bus.BusProperties;
import com.zdl.spring.bus.message.BusMessage;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EndPoint管理及初始化
 * <p>
 * Created by ZDLegend on 2019/4/10 11:43
 */
public class EndpointManage {

    public static final int OPERATION_ADD = 0;
    public static final int OPERATION_MODIFY = 1;
    public static final int OPERATION_LOAD = 2;
    public static final int OPERATION_DELETE = 3;

    private static final Map<String, BaseBusEndpoint> endpointMap = new HashMap<>();

    private final List<BaseBusEndpoint> endpoints;

    private static BusProperties properties;

    public EndpointManage(List<BaseBusEndpoint> endpoints, BusProperties properties) {
        this.endpoints = endpoints;
        EndpointManage.properties = properties;
    }

    /**
     * 初始化所有EndPoint
     */
    public void initEndPointMap() {
        endpoints.stream()
                .filter(endPoint -> endPoint.getClass().getAnnotation(BusEndpoint.class) != null)
                .sorted(Comparator.comparingInt(endPoint -> endPoint.getClass().getAnnotation(BusEndpoint.class).order()))
                .forEach(endPoint -> {
                    BusEndpoint busEndPoint = endPoint.getClass().getAnnotation(BusEndpoint.class);
                    endpointMap.put(busEndPoint.value(), endPoint);
                    endPoint.init();
                });
    }

    /**
     * 从消息到端点类
     */
    public static void messageToEndPoint(String msg) {
        BusMessage busMessage = JSON.parseObject(msg, BusMessage.class);
        if (msg != null) {

            if(!StringUtils.isEmpty(busMessage.getTarget())
                    && !properties.getNodeName().equals(busMessage.getTarget())){
                return;
            }

            BaseBusEndpoint endPoint = endpointMap.get(busMessage.getEndPointId());
            if (endPoint != null) {
                endPoint.messageToEndPoint(busMessage);
            }
        }
    }
}
