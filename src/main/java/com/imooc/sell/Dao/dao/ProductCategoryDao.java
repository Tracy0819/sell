package com.imooc.sell.Dao.dao;

import com.imooc.sell.Dao.mapper.ProductCategoryRepositoryMapper;
import com.imooc.sell.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * mybatis 注解使用的Dao层 想在service里使用直接Autowire就行
 */
public class ProductCategoryDao {

    @Autowired
    ProductCategoryRepositoryMapper mapper;

    public int insertByMap(Map<String, Object> map) {
        return mapper.insertByMap(map);
    }
}
