package com.zdl.spring.bus.kafka;

import com.zdl.spring.bus.endpoint.EndpointManage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.BatchAcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

/**
 * 消息总线监听
 * <p>
 * Created by ZDLegend on 2019/4/10 16:14
 */
public class BusKafkaListener implements BatchAcknowledgingMessageListener<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(BusKafkaListener.class);

    @Override
    public void onMessage(List<ConsumerRecord<String, String>> list, Acknowledgment acknowledgment) {
        logger.debug("received message {}", list.size());
        list.stream()
                .map(ConsumerRecord::value)
                .forEach(EndpointManage::messageToEndPoint);
    }
}
