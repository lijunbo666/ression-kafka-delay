package com.example.utils.delay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname DelayQueueMessageConsumer
 * @Description 延迟队列消费者
 * @Date 2022/9/14 10:28
 * @Created by wangchangjiu
 */
@Slf4j
public class DelayQueueMessageConsumer {

    private DelayQueueMessage delayQueueMessage;

    private KafkaSimpleMessageProducer kafkaSimpleMessageProducer;

    private String registerService;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public DelayQueueMessageConsumer(DelayQueueMessage delayQueueMessage,
                                     KafkaSimpleMessageProducer kafkaSimpleMessageProducer,
                                     String registerService){
        this.delayQueueMessage = delayQueueMessage;
        this.kafkaSimpleMessageProducer = kafkaSimpleMessageProducer;
        this.registerService = registerService;
    }



    @PostConstruct
    public void consumerAndSendKafka() {
        //线程池单线程
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            String QUEUE_CODE = String.format(Constant.QUEUE_CODE);
            while (true) {
                Object obj = delayQueueMessage.getDelayQueue(QUEUE_CODE);
                if(obj != null){
                    JSONObject jsonObject = JSON.parseObject((String) obj); // 将 JSON 数据转换为 JSONObject
                    RedisDelayMessage message = jsonObject.toJavaObject(RedisDelayMessage.class); // 将 JSONObject 转换为 RedisDelayMessage 对象
                    kafkaTemplate.send(message.getTopic(),message.getValue());
                    log.info("--延迟队列中间件...topic:{}, 获取延迟消息：{}, 发送kafka消息队列中--", message.getTopic(), message.getValue());
                }
                /*Thread.sleep(500);*/
            }
        });
        log.info("(Redis延迟队列启动成功)");
    }
}
