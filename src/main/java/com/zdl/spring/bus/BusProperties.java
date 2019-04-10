package com.zdl.spring.bus;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by ZDLegend on 2019/4/10 16:54
 */
@ConfigurationProperties(prefix = "zld.spring.bus")
public class BusProperties {

    private String nodeName;

    private String topic;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
