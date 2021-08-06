package com.lsz.mall.portal.config;

import com.lsz.mall.portal.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class TimeOutOrderConsumer {


    @Resource(name = "orderDelayConsumerQueue")
    RBlockingQueue<String> orderDelayConsumerQueue;
    @Resource(name = "orderCancelPool")
    ThreadPoolExecutor orderCancelPool;
    @Resource
    OrderService orderService;

    @PostConstruct
    public void consume() {
        orderDelayConsumerQueue.subscribeOnElements((orderId) -> {
            orderCancelPool.execute(() -> {
                log.info("orderId = {} 订单过期！", orderId);
                orderService.cancelOrder(orderId, true);
            });
        });
    }


}
