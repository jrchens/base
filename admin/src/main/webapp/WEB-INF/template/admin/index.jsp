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
    <title>${page_title}</title>

    <%@ include file="../../views/common/css.jsp" %>
    <%@ include file="../../views/common/js.jsp" %>

</head>


<body class="easyui-layout">

    <div id="overlay"></div>

    <div data-options="region:'north',border:true" style="height:42px;padding:10px">
        <tiles:insertAttribute name="top"></tiles:insertAttribute>
    </div>

    <div data-options="region:'west',split:true,title:' '" style="width:200px;padding:10px;">
        <tiles:insertAttribute name="left"></tiles:insertAttribute>
    </div>
    <%--<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>--%>
    <%--<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>--%>
    <div data-options="region:'center'" style="padding: 10px;">

        <c:if test="${not empty controller_error}">
            <div class="easyui-panel" data-options="closable:true" title="错误信息"
                 style="margin-bottom: 10px; color:red; padding-left: 5px; font-weight: bold;">${controller_error}</div>
        </c:if>

        <tiles:insertAttribute name="body"></tiles:insertAttribute>
    </div>
</body>

</html>
