package com.imooc.sell.Service.Impl;

import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl service;

    @Test
    public void findOne() {
        ProductInfo productInfo= service.findOne("234567890-1");
        Assert.assertEquals("234567890-1",productInfo.getProductId());
    }

    @Test
    public void findAll() {
        List<ProductInfo> productInfos = service.findAll();
        Assert.assertNotEquals(0,productInfos.size());
    }

    @Test
    public void findAl1() {
        PageRequest request =new PageRequest(0,2);
        Page<ProductInfo> productInfos = service.findAll(request);
        //System.out.println(productInfos.getTotalElements());
        Assert.assertNotEquals(0,productInfos.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("234567890-3");
        productInfo.setProductName("雪人巧克力");
        productInfo.setProductPrice(BigDecimal.valueOf(12.32));
        productInfo.setProductStock(500);
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(3);
        ProductInfo result = service.save(productInfo);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void onSaleTest(){
        ProductInfo productInfo = service.onSale("1");
        Assert.assertEquals(ProductStatusEnum.UP,productInfo.getProductStatusEnum());
    }
    @Test
    public void offSaleTest(){
        ProductInfo productInfo = service.offSale("1");
        Assert.assertEquals(ProductStatusEnum.DOWN,productInfo.getProductStatusEnum());
    }
}