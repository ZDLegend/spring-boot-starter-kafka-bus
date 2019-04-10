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

    public static <T> void insertPublish(String endPointId, String target, List<T> data) {
        busMessagePublish(OPERATION_ADD, endPointId, target, data);
    }

    public static <T> void modifyPublish(String endPointId, String target, List<T> data) {
        busMessagePublish(OPERATION_MODIFY, endPointId, target, data);
    }

    public static <T> void loadublish(String endPointId, String target, List<T> data) {
        busMessagePublish(OPERATION_LOAD, endPointId, target, data);
    }

    public static <T> void deletePublish(String endPointId, String target, List<T> data) {
        busMessagePublish(OPERATION_DELETE, endPointId, target, data);
    }

    private static <T> void busMessagePublish(int operation, String endPointId, String target, List<T> data) {
        BusMessage<T> message = new BusMessage<>();
        message.setData(data);
        message.setEndPointId(endPointId);
        message.setOperation(operation);
        message.setSource(busProperties.getNodeName());
        message.setTarget(target);
        publish(busProperties.getTopic(), message);
    }

    private static <T> ListenableFuture<SendResult<String, String>> publish(String topic, T object) {

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
