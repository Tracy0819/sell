package com.imooc.sell.handler;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.config.ProjectUrl;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.exception.SellerAuthorizeException;
import com.imooc.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrl projectUrlconfig;
    /**
     * 拦截登录异常
     */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException(){
        System.out.println("拦截器111111111111111111111111111");
        return new ModelAndView("redirect:"
                .concat("http://sell.springboot.cn")//借用的账号写的地址
                //.concat(projectUrlconfig.getWechatOpenAuthorizeUrl()) //自己的账号写的地址

                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlconfig.getSellUrl())
                .concat("/sell/seller/login"));
    }

    /**
     * 将前端返回错误时的异常进行处理
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
