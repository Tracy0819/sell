<#--在freemarker里遍历数据 ibootstrap找格式-->
<html>
<head>
    <meta charset ="utf-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <#--<link rel="stylesheet" href="../../static/css/style.css">-->
    <link rel="stylesheet" href="/sell/css/style.css">
</head>
    <body>
    <div id="wrapper" class="toggled">
        <#--边栏-->
        <#include "../common/nav.ftl">
        <#--主要内容-->
        <div id ="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>订单Id</th>
                                <th>卖家姓名</th>
                                <th>手机号</th>
                                <th>卖家地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付方式</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list orderDTOPage.content as orderDTO>
                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.orderStatusEnums.getMessage()}</td>
                        <td>微信</td>
                        <td>${orderDTO.payStatusEnum.getMessage()}</td>
                        <td>${orderDTO.createTime}</td>
                        <td>
                            <a href="detail?orderId=${orderDTO.orderId}">详情</a>
                        </td>
                        <td>
                            <#if orderDTO.getOrderStatusEnums().message =="新订单">
                                <a href="cancel?orderId=${orderDTO.orderId}">取消</a>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-12 column">
                        <ul class="pagination pagination-lg pull-right">
                    <#if currentPage lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                    <#else >
                     <li><a href="/sell/seller/order/list?page=${currentPage -1}&size=${size}">上一页</a><li>
                    </#if>
                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage ==index>
                        <li class = "disabled"><a href="#">${index}</a><li>
                        <#else >
                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a><li>
                        </#if>
                    </#list>
                    <#if currentPage gte orderDTOPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                    <#else >
                     <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a><li>
                    </#if>
                        </li>
                        </ul>
                    </div>
                </div>

    </div>
        </div>
            <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">
                                提醒
                            </h4>
                        </div>
                        <div class="modal-body">
                            您有新的订单啦
                        </div>
                        <div class="modal-footer">
                            <button type="button" onclick="javascript:document.getElementById('notice').pause()" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                        </div>
                    </div>

                </div>

            </div>
<#--播放音乐-->
<audio id = "notice" loop="loop">
    <source src="/sell/mp3/simplelove.mp3" type="audio/mpeg">
</audio>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://tracy0819.natapp1.cc/sell/webSocket');
    } else {
        alert('该浏览器不支持websocket');
    }
    websocket.open = function (event) {
        console.log('建立连接');
    }
    websocket.onclose = function (event) {
        console.log('连接关闭');
    }
    websocket.onmessage = function (event) {
        console.log('收到消息：' + event.data);
        //弹窗提醒 播放音乐等
        //触发弹窗的话需要用到jquery 需要引入
        $('#myModal').modal('show');

        //直接使用html原生的播放音乐

        document.getElementById('notice').play();
    }
    websocket.onerror = function () {
        alert('websocket通信发生错误！');

    }
    window.onbeforeunload = function () {
        websocket.close();

    };
    </script>


    </body>

</html>

