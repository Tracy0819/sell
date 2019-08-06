package com.imooc.sell.config;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")

public class ProjectUrl {
    /**
     * 微信公众平台的授权url
     */
    public String wechatMpAuthorizeUrl;
    /**
     *  微信开放平台的url
     */
    public String wechatOpenAuthorizeUrl;
    /**
     *  配置本项目的url
     */
    public String sellUrl;









}
