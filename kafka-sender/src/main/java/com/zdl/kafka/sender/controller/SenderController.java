package com.zdl.kafka.sender.controller;

import com.alibaba.fastjson.JSONObject;
import com.zdl.spring.bus.kafka.Sender;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ZDLegend on 2019/4/11 13:44
 */
@RestController
@RequestMapping("api/sender")
public class SenderController {
    @PostMapping("{topic}")
    public SendResult<String, String> insert(@PathVariable String topic, @RequestBody JSONObject json) {
        return Sender.syncPublish(topic, json);
    }
}
