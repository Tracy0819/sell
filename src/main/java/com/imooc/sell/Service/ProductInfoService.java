package com.imooc.sell.Service;

import com.imooc.sell.dto.Cart;
import com.imooc.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表（客户端
     */
    List<ProductInfo> findAll();

    /**
     * pageable 表示分页（管理端）
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /**
     * 加 / 减库存
     */
    void increaseStock(List<Cart> cartList);
    void decreaseStock(List<Cart> cartList);


    /**
     * 商品上下架
     */
    ProductInfo onSale(String productId);
    ProductInfo offSale(String productId);
}
