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
                                <th>商品Id</th>
                                <th>名称</th>
                                <th>图片</th>
                                <th>单价</th>
                                <th>库存</th>
                                <th>描述</th>
                                <th>类目</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list productInfo.content as productInfo>
                    <tr>
                        <td>${productInfo.productId}</td>
                        <td>${productInfo.productName}</td>
                        <td><img height="100" width="100" src=" ${productInfo.productIcon}"</td>
                        <td>${productInfo.productPrice}</td>
                        <td>${productInfo.productStock}</td>
                        <td>${productInfo.productDescription}</td>
                        <td>${productInfo.categoryType}</td>
                        <td>${productInfo.createTime}</td>
                        <td>${productInfo.updateTime}</td>
                        <td>
                            <a href="index?productId=${productInfo.productId}">修改</a>
                        </td>
                        <td>
                            <#if productInfo.productStatusEnum.getMessage() =="在架">
                                <a href="off_sale?productId=${productInfo.productId}">下架</a>
                            <#else>
                                <a href="on_sale?productId=${productInfo.productId}">上架</a>

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
                    <#list 1..productInfo.getTotalPages() as index>
                        <#if currentPage ==index>
                        <li class = "disabled"><a href="#">${index}</a><li>
                        <#else >
                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a><li>
                        </#if>
                    </#list>
                    <#if currentPage gte productInfo.getTotalPages()>
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



    </body>

</html>

