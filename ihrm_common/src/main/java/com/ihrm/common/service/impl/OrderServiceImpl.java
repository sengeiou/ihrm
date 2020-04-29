package com.ihrm.common.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ihrm.common.component.rabbitmq.CancelOrderSender;
import com.ihrm.common.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
//    @Autowired
//    private CancelOrderSender cancelOrderSender;

    @Transactional
    @Override
    public void generateOrder() {
        //todo 执行一系类下单操作
        String orderId = DateUtil.format(new Date(), "HHmmss"); // 用当前时间模拟为订单号
        log.info("process generateOrder orderId: {}",orderId);
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(new Long(orderId));
    }

    @Transactional
    @Override
    public void cancelOrder(Long orderId) {
        //todo 执行一系类取消订单操作
        log.info("process cancelOrder orderId:{}",orderId);
    }

    /**
     * 发送延迟消息
     * @param orderId
     */
    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间，假设为20秒
        long delayTimes = 20 * 1000L;
        //发送延迟消息
//        cancelOrderSender.sendMessage(orderId, delayTimes);
    }
}
