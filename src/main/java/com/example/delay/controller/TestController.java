package com.example.delay.controller;

import com.example.delay.configuration.BaseProperties;
import com.example.delay.service.BaseService;
import com.example.utils.delay.DelayQueueMessageProducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author dewey
 * @date 2019-11-02 19:32
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private BaseService baseService;
    @Autowired
    private BaseProperties baseProperties;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private DelayQueueMessageProducer delayQueueMessageProducer;
    //全局计数器
    public Integer msgCount=0;

    @RequestMapping("/send")
    public void send() {
        for (int i = 1; i <= 100000; i++) {
            /**
             * 模拟1秒
             */
            LocalDateTime now = LocalDateTime.now();
            /*System.out.println("当前时间: " + now);*/
            // 如果你想以特定的格式打印时间，你可以使用 DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = now.format(formatter);
            System.out.println("格式化后的当前时间: " + formatDateTime);
            delayQueueMessageProducer.sendMessage("delay-order-test", "kafka时间是" + formatDateTime, 10, TimeUnit.SECONDS);
            /*kafkaTemplate.send("test", "数值是"+i);*/
        }
    }

    /**
     * 监听过期订单方法
     *
     * @param messages
     * @param ack
     */
    @KafkaListener(topics = "delay-order-test", groupId = "delay-order-test")
    public void delayOrder(List<String> messages, Acknowledgment ack) {
        //redis中计数

        try {
            //字符串消息格式
            for (String message : messages) {
                log.info("kafka消息是 {} ", message);
                msgCount++;
            }
            //立即提交
            ack.acknowledge();
            log.info("kafka接收总记录是 {} 条", msgCount);
        } catch (Exception e) {
            //处理失败，不立即通知
            e.printStackTrace();
        }

    }


}
