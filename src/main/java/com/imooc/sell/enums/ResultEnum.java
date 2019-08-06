package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 返回给前端提示的消息
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIT(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不正确"),
    ORDER_NOT_EXIT(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIT(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单中没有商品"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付不正确"),
    CART_EMPTY_ERROR(18,"购物车不能为空"),
    ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
    WECHAT_MP_ERROR(20,"微信公众账号方面有错误"),
    WECHAR_PAY_AMOUNT_ERROR(21,"微信支付订单金额不一致"),
    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),
    ORDER_FINISH_SUCCESS(23,"订单完结成功"),
    PRODUCT_STATE_ERROR(24, "商品状态不正确"),
    TYPE_CANNOT_DUPLICATE_ERROR(25,"类目类型不能重复"),
    LOGIN_FAIL(26,"登录失败,登录信息不正确"),
    LOGOUT_SUCCESS(27,"登出成功" ),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }



}
