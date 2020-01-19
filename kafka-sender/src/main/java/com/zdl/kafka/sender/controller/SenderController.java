package com.zdl.kafka.sender.controller;

import com.zdl.kafka.sender.build.KafkaProducerBuild;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ZDLegend on 2019/4/11 13:44
 */
@RestController
@RequestMapping("api/sender")
public class SenderController {

    @Value("${zdl.kafka.ip:127.0.0.1}")
    private String kafkaIp;

    @PostMapping("{topic}")
    public Object insert(@PathVariable String topic, @RequestBody String s) {
        return KafkaProducerBuild.build(kafkaIp).send(topic, s).join();
    }
}
