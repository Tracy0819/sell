package com.imooc.sell.Controller;

import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.Service.SellerService;
import com.imooc.sell.config.ProjectUrl;
import com.imooc.sell.entity.SellerInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户相关操作
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;//往redis里写一个String类型的值

    @Autowired
    private ProjectUrl projectUrl;
    /**
     * 登录
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1.openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenId(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        //如果有就返回成功 没有就失败
        //设置token至redis 设置自己的uuid作为token和过期时间（将其配置到const的常量中去）
        //redisTemplate.opsForValue().set("abc", "adsfasdf");

        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(
                RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);
        //设置token至cookie (校验的时候从前端cookie拿到 去后端redis校验
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
        //跳转的时候最好用完整的http地址 最好不要用相对的http地址
        System.out.println("+++++++"+projectUrl.getSellUrl()+"22222222222222222"+projectUrl.getWechatMpAuthorizeUrl()+"3333"+projectUrl.getWechatOpenAuthorizeUrl());
        return new ModelAndView("redirect:"+ projectUrl.getSellUrl() +"/sell/seller/order/list");



    }


    /**
     * 登出
     */

    //其实就是把redis和cookie清掉
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map) {
        //1.从cookies里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(
                    RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3.清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
}
