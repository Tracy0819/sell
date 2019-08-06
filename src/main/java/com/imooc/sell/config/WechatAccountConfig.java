package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    /**
     * 公众平台id和secret
     */
    private String mpAppId;
    private String mpAppSecret;

   /**
    *  商户号
    */
   private String mchId;
    
    /**
     *  商户秘钥
     */
    private String mchKey;

    /**
     *  商户证书路径
     */
    private String keyPath;

    /**
     *  异步调用路径
     */
    private String notifyUrl;

    /**
     *开放平台id
     */
    private String openAppId;
    /**
     *  开放平台secret
     */
    private String openAppSecret;

    /**
     * 模板id
     */
    private Map<String,String> templateId;






}
