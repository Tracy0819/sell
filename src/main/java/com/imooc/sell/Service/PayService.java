package com.imooc.sell.Service;

import com.imooc.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

public interface PayService {
    PayResponse create(OrderDTO orderDTO);

    PayResponse  notify(String notifyData);
}
