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
                                <th>类目Id</th>
                                <th>名字</th>
                                <th>type</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th >操作</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list categoryList as category>
                    <tr>
                        <td>${category.categoryId}</td>
                        <td>${category.categoryName}</td>
                        <td>${category.categoryType}</td>
                        <td>${category.createTime}</td>
                        <td>${category.updateTime}</td>
                        <td>
                            <a href="index?categoryId=${category.categoryId}">修改</a>
                        </td>
                    </tr>
                    </#list>
                            </tbody>
                        </table>
                    </div>

                </div>

    </div>
        </div>



    </body>

</html>

