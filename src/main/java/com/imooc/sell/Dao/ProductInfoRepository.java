package com.imooc.sell.Dao;

import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo , String> {
    /**
     * 通过商品状态查询上架商品
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
