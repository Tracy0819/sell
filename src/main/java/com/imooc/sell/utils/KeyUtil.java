package com.imooc.sell.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一主键
     * 格式：时间+随机数 synchronized关键字 多线程并发 防止重复
     */

    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;// 生成6位随机数
        return   System.currentTimeMillis()+ String.valueOf(number);
    }
}
