package com.imooc.sell.Controller;

import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Service.PayService;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        //1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        //2.发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", "http%3a%2f%2fsell.com%2f%23%2forder%2f" + orderId);

        return new ModelAndView("pay/create", map);

    }

    //接收微信异步通知
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifuData ){
        payService.notify(notifuData);

        //返回给微信处理结果 使用模版引擎 （直接用String也可以）
        return  new ModelAndView("pay/success");

    }
}
