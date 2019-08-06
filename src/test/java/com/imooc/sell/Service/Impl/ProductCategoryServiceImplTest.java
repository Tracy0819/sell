package com.imooc.sell.Service.Impl;

import com.imooc.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl service;

    @Test
    public void findOne() {
        ProductCategory productCategory = service.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoriesList = service.findAll();
        Assert.assertNotEquals(0,productCategoriesList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategories = service.findByCategoryTypeIn(Arrays.asList(2,3,4));
        Assert.assertNotEquals(0,productCategories.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory productCategory = new ProductCategory("单元测试2",6);
        ProductCategory result = service.save(productCategory);
        Assert.assertNotEquals(null,result);
    }
}