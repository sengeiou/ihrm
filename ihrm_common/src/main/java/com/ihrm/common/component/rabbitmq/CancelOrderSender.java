package com.ihrm.common.component.rabbitmq;

import com.ihrm.common.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelOrderSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId, long delayTimes) {
        this.amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_CANCEL_DELAY.getExchange(), QueueEnum.QUEUE_ORDER_CANCEL_DELAY.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        log.info("发送延迟消息 orderId: {}", orderId);
    }
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    /**
//     * 发送消息
//     * @param uuid
//     * @param message  消息
//     */
//    public void send(String uuid,Object message) {
//        CorrelationData correlationId = new CorrelationData(uuid);
//        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTINGKEY2,
//                message, correlationId);
//    }

}
