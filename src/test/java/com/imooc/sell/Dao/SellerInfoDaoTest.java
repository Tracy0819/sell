package com.imooc.sell.Dao;

import com.imooc.sell.entity.SellerInfo;
import com.imooc.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.invoke.SwitchPoint;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {

    @Autowired SellerInfoDao repository;
    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("123456666");
        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotEquals(null,result);
    }
    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = repository.findByOpenid("123456666");
        Assert.assertEquals("123456666",sellerInfo.getOpenid());
    }
}