package com.zdl.spring.bus.kafka;

import com.zdl.spring.bus.BusProperties;
import com.zdl.spring.bus.KafkaBusException;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.TopicPartitionInitialOffset;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.springframework.kafka.support.TopicPartitionInitialOffset.SeekPosition.END;

/**
 * Created by ZDLegend on 2019/4/10 16:44
 */
@Configuration
@ConditionalOnBean(KafkaAutoConfiguration.class)
@EnableConfigurationProperties(BusProperties.class)
public class BusKafkaAutoConfiguration {

    private static final String OFFSET_CONFIG_DEFAULT = "default";
    private static final String OFFSET_CONFIG_CURRENT = "current";

    private final BusProperties properties;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public BusKafkaAutoConfiguration(BusProperties properties, KafkaTemplate<String, String> kafkaTemplate) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public Sender sender() {
        return new Sender(kafkaTemplate, properties);
    }

    @Bean
    public BusKafkaListener busKafkaListener() {
        return new BusKafkaListener();
    }

    @Bean
    @ConditionalOnMissingBean(KafkaOffsetInit.class)
    public ConcurrentMessageListenerContainer<String, String> busListenerContainer(BusKafkaListener listener,
                                                                                   ConsumerFactory<String, String> consumerFactory) {
        ContainerProperties containerProperties;
        if (OFFSET_CONFIG_DEFAULT.equals(properties.getOffsetReset())) {
            containerProperties = new ContainerProperties(properties.getTopic());
        } else if (OFFSET_CONFIG_CURRENT.equals(properties.getOffsetReset())) {
            containerProperties = topicPartitionInitialEndOffset(properties.getTopic());
        } else {
            throw new KafkaBusException("zld.spring.bus.offset-reset:" + properties.getOffsetReset() + " need a instance of KafkaOffsetInit.class");
        }

        return initListenerContainer(listener, containerProperties, consumerFactory);
    }

    @Bean
    @ConditionalOnBean(KafkaOffsetInit.class)
    public ConcurrentMessageListenerContainer<String, String> busListenerContainer(BusKafkaListener listener,
                                                                                   ConsumerFactory<String, String> consumerFactory,
                                                                                   KafkaOffsetInit offectInit) {
        ContainerProperties containerProperties = new ContainerProperties(offectInit.topicPartitionInitialOffset());
        return initListenerContainer(listener, containerProperties, consumerFactory);
    }

    public ConcurrentMessageListenerContainer<String, String> initListenerContainer(BusKafkaListener listener,
                                                                                    ContainerProperties containerProperties,
                                                                                    ConsumerFactory<String, String> consumerFactory) {
        containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL);
        ConcurrentMessageListenerContainer<String, String> container
                = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
        var group = "bus_group_" + properties.getNodeName();
        container.getContainerProperties().setGroupId(group);
        container.getContainerProperties().setMessageListener(listener);
        return container;
    }

    private ContainerProperties topicPartitionInitialEndOffset(String topic) {
        List<PartitionInfo> partitionInfos = kafkaTemplate.partitionsFor(topic);
        if (!CollectionUtils.isEmpty(partitionInfos)) {
            TopicPartitionInitialOffset[] offsets = partitionInfos.stream()
                    .map(pi -> new TopicPartitionInitialOffset(topic, pi.partition(), END))
                    .toArray(TopicPartitionInitialOffset[]::new);
            return new ContainerProperties(offsets);
        } else {
            return new ContainerProperties(topic);
        }
    }
}
