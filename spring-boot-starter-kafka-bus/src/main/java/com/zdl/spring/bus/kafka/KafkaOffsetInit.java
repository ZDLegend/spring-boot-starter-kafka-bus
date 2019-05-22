package com.zdl.spring.bus.kafka;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.kafka.support.TopicPartitionInitialOffset;

/**
 * Created by ZDLegend on 2019/4/18 11:49
 */
@NoRepositoryBean
public interface KafkaOffsetInit {
    TopicPartitionInitialOffset[] topicPartitionInitialOffset();
}
