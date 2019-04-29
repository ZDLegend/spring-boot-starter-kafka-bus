package com.zdl.spring.bus;

import com.zdl.spring.bus.kafka.KafkaOffsetInit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Created by ZDLegend on 2019/4/10 16:54
 */
@ConfigurationProperties(prefix = "zld.spring.bus")
public class BusProperties {

    /**
     * 节点名
     */
    private String nodeName;

    /**
     * kafka topic 需自定义
     */
    private String topic;

    /**
     * offset设置，默认使用kafka auto-offset-reset参数配置offset
     * 使用字符串'current',则直接从offset最后位开始消费
     * 实现接口类{@link KafkaOffsetInit}可自定义offset
     */
    private String offsetReset = "default";

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

    public String getOffsetReset() {
        return offsetReset;
    }

    public void setOffsetReset(String offsetReset) {
        this.offsetReset = offsetReset;
    }
}
