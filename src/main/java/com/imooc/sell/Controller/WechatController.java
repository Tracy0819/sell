package com.imooc.sell.Controller;


import com.imooc.sell.config.ProjectUrl;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
//restController不会跳转 使用contoller
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrl projectUrlConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String retrunUrl){

        //1.配置 Config 类

        //2.调用方法 (配置项目地址）
        String url = projectUrlConfig.getWechatMpAuthorizeUrl()+"/sell/wechat/userInfo";
        System.out.println("..............."+url);
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(retrunUrl));
        //使用base的话 用户 都是无感知的 不会再弹窗
        log.info("微信网页授权 获取code  result={}",redirectUrl);
        //地址获取了之后 需要将其重定向
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try{
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("微信网页授权错误 {}", e);
            throw  new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        String result = "redirect:" + returnUrl +"?openId=" +openId;
        System.out.println(result);
        return "redirect:" + returnUrl +"?openid=" +openId; //之前写的是Openid，与前端写的api不一致。重定向的路径不对 所以反复跳转

    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl")  String returnUrl){
        String url = projectUrlConfig.getWechatOpenAuthorizeUrl() +"/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        return  "redirect:" +redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code){

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try{
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("微信公众id登录错误 {}", e);
            throw  new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        log.info("++++++++++++++++++++++  wxMpOAuth2AccessToken={}",wxMpOAuth2AccessToken);
        String returnUrl = "http://tracy0819.natapp1.cc/sell/seller/login";
        String openId = wxMpOAuth2AccessToken.getOpenId();
        String result = "redirect:" + returnUrl +"?openId=" +openId;
        System.out.println(result);
        return "redirect:" + returnUrl +"?openid=" +openId;
    }
}
