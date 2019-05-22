package com.zdl.spring.bus.kafka;

import com.alibaba.fastjson.JSON;
import com.zdl.spring.bus.BusProperties;
import com.zdl.spring.bus.KafkaBusException;
import com.zdl.spring.bus.message.BusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.zdl.spring.bus.endpoint.EndpointManage.*;

/**
 * kafka消息发送
 * <p>
 * Created by ZDLegend on 2019/4/10 16:18
 */
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    private static KafkaTemplate<String, String> kafkaTemplate;

    private static BusProperties busProperties;

    public Sender(KafkaTemplate<String, String> kafkaTemplate, BusProperties busProperties) {
        Sender.kafkaTemplate = kafkaTemplate;
        Sender.busProperties = busProperties;
    }

    public static <T> String insertPublish(String endPointId, List<String> targets, List<T> data) {
        return busMessagePublish(OPERATION_ADD, endPointId, targets, data);
    }

    public static <T> String modifyPublish(String endPointId, List<String> targets, List<T> data) {
        return busMessagePublish(OPERATION_MODIFY, endPointId, targets, data);
    }

    public static <T> String loadublish(String endPointId, List<String> targets, List<T> data) {
        return busMessagePublish(OPERATION_LOAD, endPointId, targets, data);
    }

    public static <T> String deletePublish(String endPointId, List<String> targets, List<T> data) {
        return busMessagePublish(OPERATION_DELETE, endPointId, targets, data);
    }

    private static <T> String busMessagePublish(int operation, String endPointId, List<String> targets, List<T> data) {
        BusMessage<T> message = BusMessage.instance(data).operation(operation).endPointId(endPointId)
                .targets(targets);
        syncPublish(busProperties.getTopic(), message);
        return message.getId();
    }

    public static ListenableFuture<SendResult<String, String>> callbackPublish(BusMessage<Throwable> msg) {
        return publish(busProperties.getTopic(), msg);
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

    public static BusProperties getBusProperties() {
        return busProperties;
    }
}
