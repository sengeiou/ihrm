package com.ihrm.common.config;

import com.ihrm.common.enums.QueueEnum;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import org.springframework.amqp.core.*;



/**
 * 消息队列配置
 * <p>
 * RabbitMQ的消息模型:
 * P --> X --> Q --> C
 * <p>
 * P	生产者	Producer	消息的发送者，可以将消息发送到交换机
 * C	消费者	Consumer	消息的接收者，从队列中获取消息进行消费
 * X	交换机	Exchange	接收生产者发送的消息，并根据路由键发送给指定队列
 * Q	队列	Queue	    存储从交换机发来的消息
 */
@Configuration
public class RabbitConfig {
    /**
     * 订单消息实际消费队列所绑定的交换机
     */
    @Bean
    DirectExchange orderDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单延迟队列所绑定的交换机
     */
    @Bean
    DirectExchange orderDelayDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL_DELAY.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单实际消费队列
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
    }

    /**
     * 订单延迟队列（死信队列）
     */
    @Bean
    public Queue orderDelayQueue() {
        return (Queue) QueueBuilder
                .durable(QueueEnum.QUEUE_ORDER_CANCEL_DELAY.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())//到期后转发的路由键
                .build();
    }

    /**
     * 将订单队列绑定到交换机
     */
    @Bean
    Binding orderBinding(DirectExchange orderDirect, Queue orderQueue) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }

    /**
     * 将订单延迟队列绑定到交换机
     */
    @Bean
    Binding orderDelayBinding(DirectExchange orderDelayDirect, Queue orderDelayQueue) {
        return BindingBuilder
                .bind(orderDelayQueue)
                .to(orderDelayDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL_DELAY.getRouteKey());
    }

}
