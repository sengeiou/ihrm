package com.ihrm.common.service;

public interface OrderService {

    /**
     * 取消订单
     * @param orderId
     */
    void cancelOrder(Long orderId);

    /**
     * 生成订单
     */
    void generateOrder();
}
