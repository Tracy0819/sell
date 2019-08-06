package com.imooc.sell.Service.Impl;

import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Service.PayService;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.utils.JsonUtil;
import com.imooc.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME="微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {

        PayRequest payRequest = new PayRequest();

        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】 发起支付 request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】 发起支付 response={}" ,JsonUtil.toJson(payResponse));

        return  payResponse;


    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData); //接收到异步通知
        log.info("[微信支付]异步通知 payResponse = {} ",payResponse);

        //异步通知需要校验： 1.验证签名

        //2.支付状态 前两点bastpay已经给做了

        //3.支付金额

        //4.支付的人（下单人 == 支付人）

        //修改订单的支付状态
        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        //判断订单是否存在
        if(orderDTO == null){
            log.error("微信支付 异步通知  订单不存再  orderId ={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        //判断金额是否一致 不要用equals比较
        // (0.10 0.1 这个在电脑中可能不相等 可以使用两个金额相减
        //if(orderDTO.getOrderAmount().compareTo( new BigDecimal(payResponse.getOrderAmount()))==0){
          if(!MathUtil.equales(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("微信支付异步通知 订单金额不一致  微信金额={} 系统金额 ={}",payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WECHAR_PAY_AMOUNT_ERROR);
        }
        //修改支付状态
        orderService.paid(orderDTO);

        return payResponse;
    }
}
