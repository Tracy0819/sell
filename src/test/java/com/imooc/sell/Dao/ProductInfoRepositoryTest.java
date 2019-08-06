package com.imooc.sell.Dao;

import com.imooc.sell.entity.ProductInfo;
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
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo =new ProductInfo();
        productInfo.setProductId("234567890-2");
        productInfo.setProductName("雪人麦克风");
        productInfo.setProductPrice(BigDecimal.valueOf(879.32));
        productInfo.setProductStock(500);
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);
        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfos = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfos.size());
    }
}