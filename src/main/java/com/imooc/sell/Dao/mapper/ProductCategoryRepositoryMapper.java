package com.imooc.sell.Dao.mapper;

import com.imooc.sell.entity.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductCategoryRepositoryMapper {
    //通过map写入
    @Insert("insert into product_category" +
            "(category_name, category_type) " +
            "values(#{category_name ,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    //通过对象写入
    @Insert("insert into product_category" +
            "(category_name, category_type) " +
            "values(#{categoryName ,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    //查询
    @Select("select * from product_category where category_type = #{type}")
    @Results({
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
    })
    ProductCategory findByCategoryType(Integer type);


    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
    })
    List<ProductCategory> findBycategoryName(String categoryName);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{type}")
    int updateByCategoryType(@Param("categoryName") String categoryName,
                             @Param("type") Integer type);


    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);

    ProductCategory selectByCategoryType(Integer categoryType);
}
