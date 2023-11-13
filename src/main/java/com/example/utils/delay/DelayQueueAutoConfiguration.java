package com.example.utils.delay;

import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Classname DelayQueueAutoConfig
 * @Description
 * @Date 2022/9/14 10:20
 * @Created by wangchangjiu
 */
@Configuration
@ConditionalOnProperty(name = "delay.redisson.enable")
public class DelayQueueAutoConfiguration {
    /*private DelayQueueMessage delayQueueMessage;*/
    private String registerService;
    private  RedissonDelayQueue  redissonDelayQueue;
    @Bean
    @ConditionalOnMissingBean(DelayQueueMessageProducer.class)
    public DelayQueueMessageProducer delayQueueMessageProducer() {
        registerService="order";
        return new DelayQueueMessageProducer(redissonDelayQueue,registerService); // 假设你的实现类名为 DelayQueueMessageProducer
    }
    @Bean
    @ConditionalOnMissingBean(RedissonDelayProperties.class)
    public RedissonDelayProperties redissonDelayProperties() {
        //
        return new RedissonDelayProperties();
    }

    @Bean
/*    @ConditionalOnMissingBean(StringRedisTemplate.class)
    @ConditionalOnBean({ RedisConnectionFactory.class })*/
    public StringRedisTemplate stringRedisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
/*    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    @ConditionalOnBean({ RedissonClient.class })*/
    public RedissonConnectionFactory redissonConnectionFactory(@Autowired RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean
/*    @ConditionalOnMissingBean(DelayQueueMessage.class)
    @ConditionalOnBean({ RedissonClient.class })*/
    public DelayQueueMessage delayQueueMessage(@Autowired RedissonClient redisson){
        return new RedissonDelayQueue(redisson);
    }


    @Bean
  /*  @ConditionalOnMissingBean(DelayQueueMessageProducer.class)
    @ConditionalOnBean({ DelayQueueMessage.class, RedissonDelayProperties.class })*/
    public DelayQueueMessageProducer delayQueueMessageProducer(@Autowired RedissonDelayQueue redissonDelayQueue,
                                                               @Autowired RedissonDelayProperties properties){
        return new DelayQueueMessageProducer(redissonDelayQueue, properties.getRegisterService());
    }

    @Bean
/*    @ConditionalOnMissingBean(KafkaSimpleMessageProducer.class)
    @ConditionalOnBean({ KafkaTemplate.class })*/
    public KafkaSimpleMessageProducer kafkaSimpleMessageProducer(@Autowired KafkaTemplate<Long, String> kafkaTemplate){
        return new KafkaSimpleMessageProducer(kafkaTemplate);
    }

    @Bean
/*    @ConditionalOnMissingBean(DelayQueueMessageConsumer.class)
    @ConditionalOnBean({DelayQueueMessage.class, KafkaSimpleMessageProducer.class})*/
    public DelayQueueMessageConsumer delayQueueMessageConsumer(@Autowired DelayQueueMessage delayQueueMessage,
                                                               @Autowired KafkaSimpleMessageProducer kafkaSimpleMessageProducer,
                                                               @Autowired RedissonDelayProperties properties) {
        return new DelayQueueMessageConsumer(delayQueueMessage, kafkaSimpleMessageProducer, properties.getRegisterService());
    }

}
