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
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>名字</label>
                            <input name="categoryName" type="text" class="form-control" value="${(productCategory.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input name="categoryType" type="number" class="form-control" value="${(productCategory.categoryType)!''}"/>
                        </div>
                        <input hidden type="text" name="productId" value="${(productCategory.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>

                </div>
            </div>
        </div>
    </div>
</div>



</body>

</html>

