package com.zdl.spring.bus.demo.sender.controller;

import com.alibaba.fastjson.JSONObject;
import com.zdl.spring.bus.demo.sender.Sender;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ZDLegend on 2019/4/11 13:44
 */
@RestController
@RequestMapping("api/sender")
public class SenderController {

    private static volatile boolean flag = false;

    @PostMapping("{topic}")
    public SendResult<String, String> insert(@PathVariable String topic, @RequestBody JSONObject json) {
        return Sender.syncPublish(topic, json);
    }

    @GetMapping("start")
    public String start(@RequestParam(value = "5") int time) {
        flag = true;
        for (int i = 0; i < time; i++) {
            new Run().start();
        }
        return "";
    }

    @GetMapping("stop")
    public String stop() {
        flag = false;
        return "";
    }

    private static class Run extends Thread {
        @Override
        public void run() {
            long i = 0;
            while (flag) {
                String s = "";
                s = s + "1111";
                System.out.println(Thread.currentThread() + "  " + i + s);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }
}
