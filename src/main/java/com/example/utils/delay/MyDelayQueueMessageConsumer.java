package com.example.utils.delay;

public class MyDelayQueueMessageConsumer extends DelayQueueMessageConsumer{
    public MyDelayQueueMessageConsumer(DelayQueueMessage delayQueueMessage, KafkaSimpleMessageProducer kafkaSimpleMessageProducer, String registerService) {
        super(delayQueueMessage, kafkaSimpleMessageProducer, registerService);
    }

}
