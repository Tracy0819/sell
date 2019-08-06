<html>
<head>
    <meta charset ="utf-8">
    <title>商品信息详情列表</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/sell/css/style.css">
</head>
<body>
<div id="wrapper" class="toggled">
<#--边栏-->
        <#include "../common/nav.ftl">
<div id ="page-content-wrapper">
<div class="container-fluid">
    <div class="row clearfix">
        <div class="col-md-4 column">
            <table class="table">
                <thead>
                <tr>
                    <th>订单ID</th>
                    <th>订单总金额</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.orderAmount}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>商品ID</th>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetailList as orderDetail>
                      <tr>
                          <td>${orderDetail.productId}</td>
                          <td>${orderDetail.productName}</td>
                          <td>${orderDetail.productPrice}</td>
                          <td>${orderDetail.productQuantity}</td>
                          <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                      </tr>
                </#list>

                </tbody>
            </table>
        </div>
        <#--操作订单-->
        <div class="row clearfix">
            <div class="col-md-12 column">
                <#if orderDTO.getOrderStatusEnums().message == "新订单">
                <a href ="/sell/seller/order/finish?orderId=${orderDTO.orderId} "type="button" class="btn btn-info btn-lg">完结订单</a>
                <a href ="/sell/seller/order/cancel?orderId=${orderDTO.orderId} "type="button" class="btn btn-info btn-lg">取消订单</a>
                </#if>
            </div>
        </div>
        <#--完结订单-->
    </div>
</div>
</div>
</body>
</html>