package com.imooc.sell.Service.Impl;

import com.imooc.sell.Converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell.Dao.OrderDetailDao;
import com.imooc.sell.Dao.OrderMasterDao;
import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Service.ProductInfoService;
import com.imooc.sell.Service.PushMessage;
import com.imooc.sell.dto.Cart;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.entity.OrderMaster;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private  ProductInfoService productInfoService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private PushMessage pushMessageService;

    @Autowired
    private  WebSocket webSocket;
    @Override
    @Transactional //事务注释 一旦发生异常就会回滚
    public OrderDTO create(OrderDTO orderDTO) {
        //1.查询商品（数量、价格等）
        String orderId = KeyUtil.genUniqueKey();//整个订单创建的时候就已经生成OrderId
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);//商品总价
        List<Cart> cartList = new ArrayList<>();
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()){
           ProductInfo productInfo =  productInfoService.findOne(orderDetail.getProductId());
           if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
           }
            //2.计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

           //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
           // orderDetail.setProductIcon(productInfo.getProductIcon()); 把每个字段都赋值写的太麻烦，可以用下面的简便的方法
            BeanUtils.copyProperties(productInfo,orderDetail);//将productInfo里的属性 拷贝到orderDetail里

            orderDetailDao.save(orderDetail);

            Cart cart = new Cart(orderDetail.getProductId(),orderDetail.getProductQuantity());//放入购物车
            cartList.add(cart);
        }


        //3.写入订单数据库（OrderMaster和OrderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //4.如果下单成功，扣库存
        productInfoService.decreaseStock(cartList);

        //发送websocket消息 这块代码是用户端下单才会触发代码
        webSocket.sendMessage(orderDTO.getOrderId());


        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster ==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }

        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIT);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage =   new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("【取消订单】 orderId={},orderState={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster); //里面是OrderMaster对象 需要在前面做一次转换
        if(updateResult == null){
            log.error("【订单取消时数据库更新失败】 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存 先判断订单是是否有商品
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【订单取消时订单内没有商品】 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //使用lamda表达式构造list
        List<Cart> cartList = orderDTO.getOrderDetailList().stream().map(e -> new Cart(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartList);

        //如果用户已支付 ，退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode()))
        {
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("完结订单-订单状态不正确 orderId={}" ,orderDTO.getOrderId());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult == null){
            log.error("【订单完结时数据库更新失败】 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模板消息 微信订单状态变化
        pushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("订单支付-订单状态不正确 orderId={}" ,orderDTO.getOrderId());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【支付状态不正确】 pauStatu={}", orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult == null){
            log.error("【订单支付时数据库更新失败】 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage =   new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }
}
