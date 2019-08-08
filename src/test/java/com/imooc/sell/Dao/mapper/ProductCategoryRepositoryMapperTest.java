package com.imooc.sell.Dao.mapper;

import com.imooc.sell.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductCategoryRepositoryMapperTest {

    @Autowired
    private ProductCategoryRepositoryMapper mapper;
    @Test
    public void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name","师兄最不爱");
        map.put("category_type",1123);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("究竟爱不爱");
        productCategory.setCategoryType(1124);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findyByCategoryType() {
        ProductCategory productCategory = new ProductCategory();
        productCategory = mapper.findByCategoryType(9988);
        System.out.println(productCategory.getCategoryName());
        Assert.assertEquals("9988",productCategory.getCategoryType().toString());
    }

    @Test
    public void findBycategoryName() {
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList = mapper.findBycategoryName("3333");
        Assert.assertEquals(3, productCategoryList.size());
    }

    @Test
    public void updateByCategoryType(){
        int result = mapper.updateByCategoryType("我也能学会mybatis", 3333);
        Assert.assertEquals(1,result);
    }

    @Test
    public void deleteByCategoryType(){
       int result =  mapper.deleteByCategoryType(3333);
        Assert.assertEquals(1,result);
    }

    @Test
    public void selectByCategoryType() {
        ProductCategory productCategory = mapper.selectByCategoryType(27);
        Assert.assertEquals("27",productCategory.getCategoryType().toString());
    }

}