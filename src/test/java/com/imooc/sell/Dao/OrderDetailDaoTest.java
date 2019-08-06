package com.imooc.sell.Dao;

import com.imooc.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;
    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("22222344");
        orderDetail.setOrderId("123444");
        orderDetail.setProductId("234567890-1");
        orderDetail.setProductName("111");
        orderDetail.setProductPrice(new BigDecimal(23.4));
        orderDetail.setProductQuantity(111);
        orderDetail.setProductIcon("222.jpg");
        OrderDetail result = orderDetailDao.save(orderDetail);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByOrderId() {

        List<OrderDetail> result = orderDetailDao.findByOrderId("123444");
        Assert.assertNotEquals(0,result);
    }
}