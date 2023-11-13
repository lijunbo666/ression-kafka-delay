package com.example.delay.configuration;

import com.example.delay.log.LogAnnotation;
import com.example.delay.log.LogAnnotationAdvisor;
import com.example.delay.log.LogAnnotationInterceptor;
import com.example.delay.log.LogAnnotationPointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dewey
 * @date 2019-11-05 11:12
 */
@Configuration
public class LogAnnotationInterceptorConfig {
    @Bean
    public LogAnnotationAdvisor LogAdvisor(){
        LogAnnotationAdvisor advisor = new LogAnnotationAdvisor();

        AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(LogAnnotation.class);

        advisor.setPointcut(new LogAnnotationPointcut());
        advisor.setPointcut(pointcut);
        advisor.setAdvice(new LogAnnotationInterceptor());
        return advisor;
    }

}
