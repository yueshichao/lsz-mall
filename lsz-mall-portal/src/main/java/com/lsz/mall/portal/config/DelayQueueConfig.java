package com.lsz.mall.portal.config;

import com.lsz.mall.base.util.CustomThreadFactory;
import com.lsz.mall.portal.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class DelayQueueConfig {

    @Autowired
    RedissonClient redisson;

    @Autowired
    OrderService orderService;

    final String orderDelayQueueName = "order-delay-queue";


    @Bean("orderDelayConsumerQueue")
    public RBlockingQueue<String> orderDelayConsumerQueue() {
        RBlockingQueue<String> blockingQueue = redisson.getBlockingQueue(orderDelayQueueName, new StringCodec());
        log.debug("orderDelayConsumerQueue construct success~");
        return blockingQueue;
    }

    @Bean
    public RDelayedQueue<String> orderDelayQueue() {
//        RBlockingQueue<String> consumerQueue = redisson.getBlockingQueue(orderDelayQueueName, new StringCodec());
        RBlockingQueue consumerQueue = orderDelayConsumerQueue();
        RDelayedQueue<String> delayedQueue = redisson.getDelayedQueue(consumerQueue);
        log.debug("orderDelayQueue construct success~");
        return delayedQueue;
    }

    @Bean("orderCancelPool")
    public ThreadPoolExecutor orderCancelPool() {
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("order-cancel-pool");
        ThreadPoolExecutor orderCancelPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), customThreadFactory);
        return orderCancelPool;
    }

}
