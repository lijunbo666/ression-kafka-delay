package com.example.delay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@SpringBootApplication
public class AopLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopLearnApplication.class, args);
    }

    /**
     * 异步线程池
     * @return
     */
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setMaxPoolSize(8);
        // 缓冲队列200：用来缓冲执行任务的队列
        threadPoolTaskExecutor.setQueueCapacity(10);
         /*
        线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，
        调用失败重新调用
          */
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return new ThreadPoolTaskExecutor();
    }


}
