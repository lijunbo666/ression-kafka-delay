package com.example.utils.delay;

import com.alibaba.fastjson.JSON;
import com.google.gson.annotations.JsonAdapter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Classname DelayQueueMessageProducer
 * @Description 延迟队列生产者
 * @Date 2022/9/14 10:12
 * @Created by wangchangjiu
 */
@Slf4j
@Component
public class DelayQueueMessageProducer {
    //接口
    /*private DelayQueueMessage delayQueueMessage;*/
    /*private  RedissonDelayQueue  redissonDelayQueue;*/
    private String registerService;
    @Autowired
    private RedissonClient redissonClient;


    public DelayQueueMessageProducer(RedissonDelayQueue redissonDelayQueue, String registerService) {
        /*this.redissonDelayQueue = redissonDelayQueue;*/
        this.registerService = registerService;
    }

    public DelayQueueMessageProducer() {

    }
    @Async("threadPoolTaskExecutor")
    public <T> void sendMessage(String topic, T jsonSerializableObject, long delay, TimeUnit timeUnit) {
        RedisDelayMessage message = new RedisDelayMessage(topic, jsonSerializableObject);
        RedissonDelayQueue redissonDelayQueue=new RedissonDelayQueue(redissonClient);
        redissonDelayQueue.addDelayQueue(JSON.toJSON(message).toString(), delay, timeUnit, Constant.QUEUE_CODE);
        log.info("添加消息：{} 进入 topic ：{} 延迟队列，delay:{} ,timeUnit:{}", JSON.toJSONString(message), topic, delay, timeUnit);
    }
}
