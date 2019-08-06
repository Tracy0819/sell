package com.imooc.sell.Dao;

import com.imooc.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory ,Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);


}
