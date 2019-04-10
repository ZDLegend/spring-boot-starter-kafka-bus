package com.zdl.spring.bus;

import com.alibaba.fastjson.JSON;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EndPoint管理及初始化
 *
 * Created by ZDLegend on 2019/4/10 11:43
 */
public class EndPointManage {

    static final int OPERATION_ADD = 0;
    static final int OPERATION_MODIFY = 1;
    static final int OPERATION_LOAD = 2;
    static final int OPERATION_DELETE = 3;

    private static final Map<String, BaseBusEndPoint> endPointMap = new HashMap<>();

    private final List<BaseBusEndPoint> endPoints;

    public EndPointManage(List<BaseBusEndPoint> endPoints){
        this.endPoints = endPoints;
    }

    /**
     * 初始化所有EndPoint
     */
    @PostConstruct
    public void initEndPointMap(){
        endPoints.stream()
                .filter(endPoint -> endPoint.getClass().getAnnotation(BusEndPoint.class) != null)
                .sorted(Comparator.comparingInt(endPoint -> endPoint.getClass().getAnnotation(BusEndPoint.class).order()))
                .forEach(endPoint -> {
                    BusEndPoint busEndPoint = endPoint.getClass().getAnnotation(BusEndPoint.class);
                    endPointMap.put(busEndPoint.value(), endPoint);
                    endPoint.init();
                });
    }

    /**
     * 从消息到端点类
     */
    public static void messageToEndPoint(String msg){
        BusMessage busMessage = JSON.parseObject(msg, BusMessage.class);
        if(msg != null) {
            BaseBusEndPoint endPoint = endPointMap.get(busMessage.getEndPointId());
            if(endPoint != null) {
                endPoint.messageToEndPoint(busMessage);
            }
        }
    }
}
