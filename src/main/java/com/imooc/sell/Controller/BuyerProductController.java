package com.imooc.sell.Controller;


import com.imooc.sell.Service.ProductCategoryService;
import com.imooc.sell.Service.ProductInfoService;
import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ProductVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 买家商品相关
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productInfoService.findAll();
        //2.查询类目（一次性查询）（主要考虑性能）
        List<Integer> categoryTypeList = new ArrayList<>();
        //传统方法
        for(ProductInfo productInfo : productInfoList)
        {
            categoryTypeList.add(productInfo.getCategoryType());
        }
        //精简方法（java 8 lamda表达式）

       List<ProductCategory> productCategoriesLists =  productCategoryService.findByCategoryTypeIn(categoryTypeList);
        //3.数据拼装
        //先遍历类目
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoriesLists)
        {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for ( ProductInfo productInfo : productInfoList)
            {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType()))
                {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        return ResultVOUtil.success(productVOList);

    }
}
