package com.imooc.sell.Service;

import com.imooc.sell.dto.OrderDTO;

/**
 * 消息推送
 */
public interface PushMessage {

    /**
     * 推送订单状态变更的消息
     */
    void orderStatus(OrderDTO orderDTO);
}
