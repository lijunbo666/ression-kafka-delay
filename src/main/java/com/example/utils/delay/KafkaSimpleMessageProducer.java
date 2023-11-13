package com.example.utils.delay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 简单kafka消息生产器
 *
 * @author zhaozuowen
 */
@Slf4j
public class KafkaSimpleMessageProducer {

    private KafkaTemplate<Long, String> kafkaTemplate;

    public KafkaSimpleMessageProducer(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Async("threadPoolTaskExecutor")
    public <T> void sendSimpleMessage(String topic, T jsonSerializableObject) {
        sendSimpleMessage(topic, jsonSerializableObject, null);
    }


    public <T> void sendSimpleMessage(String topic, T jsonSerializableObject, SerializerFeature... features) {
        String body;
        if (features == null) {
            body = JSON.toJSONString(jsonSerializableObject);
        } else {
            body = JSON.toJSONString(jsonSerializableObject, features);
        }
        ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(topic, body);
        future.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
            @Override
            public void onFailure(@NonNull Throwable throwable) {

                log.error("Sending message to Kafka failed, topic: {}, body: {}", topic, body);
                log.error(throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(SendResult<Long, String> result) {

                log.info("Sending message to Kafka finished, topic: {}, body: {}, result: {}", topic, body, JSON.toJSONString(result));
            }
        });
    }
}
