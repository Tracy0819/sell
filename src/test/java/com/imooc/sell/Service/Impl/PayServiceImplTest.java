package com.imooc.sell.Service.Impl;

import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Service.PayService;
import com.imooc.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;
    @Test
    public void create() throws Exception{
        OrderDTO orderDTO = orderService.findOne("123156");
        payService.create(orderDTO);

    }
}