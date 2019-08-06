package com.imooc.sell.Service.Impl;

import com.imooc.sell.Service.SellerService;
import com.imooc.sell.entity.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {
    private static final String openid = "123456666";
    @Autowired
    private SellerService sellerService;
    @Test
    public void test(){
        SellerInfo result = sellerService.findSellerInfoByOpenId(openid);
        Assert.assertEquals("123456666",result.getOpenid());
    }

}