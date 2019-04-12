package com.zdl.spring.bus;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Created by ZDLegend on 2019/4/10 16:54
 */
@ConfigurationProperties(prefix = "zld.spring.bus")
public class BusProperties {

    private String nodeName;

    private String topic;

    public String getNodeName() {
        if (StringUtils.isEmpty(nodeName)) {
            throw new KafkaBusException("The node name is empty, please check if the configuration item 'zdl.spring.bus.nodeName' is configured.");
        }
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTopic() {
        if (StringUtils.isEmpty(topic)) {
            throw new KafkaBusException("The topic is empty, please check if the configuration item 'zdl.spring.bus.topic' is configured.");
        }
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
