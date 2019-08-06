package com.imooc.sell.constant;

/**
 * redis常量
 */
public interface RedisConstant {
    /**
     * 设置一个token的前缀
     */
    String TOKEN_PREFIX ="token_%s";
    /**
     *  过期时间
     */
    Integer EXPIRE = 7200; //2h
    
    
}
