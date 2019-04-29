package com.zdl.spring.bus.kafka;

import org.springframework.kafka.support.TopicPartitionInitialOffset;
import org.springframework.stereotype.Component;

/**
 * Created by ZDLegend on 2019/4/18 11:49
 */
@Component
public interface KafkaOffsetInit {
    TopicPartitionInitialOffset[] topicPartitionInitialOffset();
}
