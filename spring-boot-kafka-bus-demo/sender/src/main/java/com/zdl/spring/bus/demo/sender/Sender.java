package com.zdl.spring.bus.demo.sender;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;


/**
 * kafka消息发送
 * <p>
 * Created by ZDLegend on 2019/4/10 16:18
 */
@Configuration
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    private static KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Sender(KafkaTemplate<String, String> kafkaTemplate) {
        Sender.kafkaTemplate = kafkaTemplate;
    }


    public static <T> ListenableFuture<SendResult<String, String>> publish(String topic, T object) {

        if (object == null) {
            throw new KafkaBusException("kafka sender object is null");
        }

        String msg;
        if (object instanceof String) {
            msg = (String) object;
        } else {
            msg = JSON.toJSONString(object);
        }

        logger.debug("kafka sender data, topic:{}, \ndata:{}", topic, msg.length() > 1000 ? msg.subSequence(0, 1000) : msg);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, msg);
        future.addCallback(
                suc -> logger.debug("kafka sender data success, topic:{}", topic),
                e -> {
                    throw new KafkaBusException("kafka sender data error, topic:" + topic, e);
                }
        );

        return future;
    }

    public static <T> SendResult<String, String> syncPublish(String topic, T object) {
        try {
            return publish(topic, object).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new KafkaBusException("kafka sender data InterruptedException, topic:" + topic, e);
        } catch (ExecutionException e) {
            throw new KafkaBusException("kafka sender data ExecutionException, topic:" + topic, e);
        }
    }
}
