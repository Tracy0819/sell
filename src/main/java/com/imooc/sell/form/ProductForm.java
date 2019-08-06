package com.imooc.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 专门处理表单提过来的字段
 */
@Data
public class ProductForm {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private  String productDescription;

    private  String productIcon;

    /**
     * 类目编号
     */
    private  Integer categoryType;
}
