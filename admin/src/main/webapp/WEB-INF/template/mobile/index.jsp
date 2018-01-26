<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../views/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="zh-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>${PAGE_CONTEXT_TITLE}</title>
    <script src="http://local.com/resources/jquery/dist/jquery.min.js"></script>
    <link rel="stylesheet" href="http://local.com/resources/weui/1.1.2/weui.min.css"/>

    <script src="http://local.com/resources/js/zepto.min.js"></script>
    <script src="http://local.com/resources/js/jweixin-1.0.0.js"></script>
    <script src="http://local.com/resources/weuijs/1.0.0/weui.min.js"></script>

</head>

<body>

<div class="page">
        <%--<script type="text/javascript">--%>
        <%--var $toast = $('#toast');--%>
        <%--var $loadingToast = $('#loadingToast');--%>
        <%--</script>--%>

        <tiles:insertAttribute name="common"></tiles:insertAttribute>

        <tiles:insertAttribute name="body"></tiles:insertAttribute>


</body>

</html>
