package com.example.utils.delay;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.concurrent.TimeUnit;

/**
 * @Classname DelayQueueMessage
 * @Description
 * @Date 2022/9/14 10:15
 * @Created by wangchangjiu
 */
@Component
public interface DelayQueueMessage {

    <T> void addDelayQueue(T value, long delay, TimeUnit timeUnit, String queueCode);

    <T> T getDelayQueue(String queueCode);

}
