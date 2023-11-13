package com.example.utils.delay;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Classname RedissonDelayProperties
 * @Description
 * @Date 2022/9/15 9:51
 * @Created by wangchangjiu
 */
@Data
@Configuration
@Component
public class RedissonDelayProperties {

    @Value("${delay.redisson.registerService}")
    private String registerService;

}
