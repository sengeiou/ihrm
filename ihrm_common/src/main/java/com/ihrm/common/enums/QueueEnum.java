package com.ihrm.common.enums;

import lombok.Getter;

/**
 * 消息队列枚举配置
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列 - 取消订单
     */
    QUEUE_ORDER_CANCEL("ihrm.order.direct", "ihrm.order.cancel", "ihrm.order.cancel"),
    /**
     * 消息通知队列 - 取消订单（延时）
     */
    QUEUE_ORDER_CANCEL_DELAY("ihrm.order.direct.delay", "ihrm.order.cancel.delay", "ihrm.order.cancel.delay");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
