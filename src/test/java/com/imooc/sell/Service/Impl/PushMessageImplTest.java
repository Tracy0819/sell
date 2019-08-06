package com.imooc.sell.Service.Impl;

import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Service.PushMessage;
import com.imooc.sell.dto.OrderDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageImplTest {
    @Autowired
    private PushMessage pushMessageService;
   @Autowired
   private OrderService orderService;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderService.findOne("1234");
        pushMessageService.orderStatus(orderDTO);

    }
}