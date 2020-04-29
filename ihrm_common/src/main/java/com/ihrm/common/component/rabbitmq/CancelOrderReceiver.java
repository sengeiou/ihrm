package com.ihrm.common.component.rabbitmq;


import com.ihrm.common.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的处理者
 */
@Component
@Slf4j
@RabbitListener(queues = "ihrm.order.cancel") // 监听的消息队列
public class CancelOrderReceiver {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void handle(Long orderId){
        log.info("======================== 收到延迟消息了 ========================");
        log.info("receive delay message orderId:{}",orderId);
        orderService.cancelOrder(orderId);
    }
}
