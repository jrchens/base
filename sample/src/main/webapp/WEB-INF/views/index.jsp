<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="./common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-Hans">

<head>
    <meta charset="UTF-8">
    <title>用户登陆-${relogin}</title>

    <%@ include file="./common/css.jsp" %>
    <%@ include file="./common/js.jsp" %>

</head>

<body>

<shiro:principal />

<a href="http://local.com/logout">退出</a>

</body>

</html>