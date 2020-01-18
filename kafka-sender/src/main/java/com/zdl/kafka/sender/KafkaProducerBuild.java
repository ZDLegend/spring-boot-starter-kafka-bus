package com.zdl.kafka.sender;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * KAFKA生产者建立类
 *
 * Created by ZDLegend on 2019/8/2 16:05
 */
public class KafkaProducerBuild {

    private Producer<String, String> producer;
    private Gson gson;

    private KafkaProducerBuild() {
        //do nothing
    }

    public static KafkaProducerBuild build(String ip) {
        KafkaProducerBuild build = new KafkaProducerBuild();

        build.gson = new GsonBuilder().disableHtmlEscaping().create();

        Properties props = new Properties();

        // 填写kafka连接参数
        props.put("bootstrap.servers", ip);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        build.producer = new KafkaProducer<>(props);
        return build;
    }

    public CompletableFuture<RecordMetadata> send(String topic, int partition, Object o) {
        ProducerRecord<String, String> message = new ProducerRecord<>(topic, partition, null, gson.toJson(o));
        return send(message);
    }

    public CompletableFuture<RecordMetadata> send(String topic, Object o) {
        ProducerRecord<String, String> message = new ProducerRecord<>(topic, gson.toJson(o));
        return send(message);
    }

    private CompletableFuture<RecordMetadata> send(ProducerRecord<String, String> message) {
        producer.send(message);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return producer.send(message).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return null;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public void flush() {
        producer.flush();
    }
}
