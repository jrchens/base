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

<%--<c:if test="${not empty create_error}">--%>
<%--<div class="easyui-panel" data-options="closable:true" title="错误信息"--%>
<%--style="margin-bottom: 10px; color:red; padding-left: 5px; font-weight: bold;">${create_error}</div>--%>
<%--</c:if>--%>
<%--<c:if test="${not empty save_error}">--%>
<%--<div class="easyui-panel" data-options="closable:true" title="错误信息"--%>
<%--style="margin-bottom: 10px; color:red; padding-left: 5px; font-weight: bold;">${save_error}</div>--%>
<%--</c:if>--%>

<form:form id="login_form" method="post"
           modelAttribute="loginUser" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="用户登陆"
           data-options="inline: true" action="http://local.com/login">

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>用户名</td>
            <td><form:input path="username" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="username"/></td>
        </tr>
        <tr>
            <td>密&#12288;码</td>
            <td><form:password path="password" cssClass="easyui-passwordbox"
                               data-options="required:true,fit:true"/><form:errors
                    path="password"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-user-circle fa-lg', width: 80,
                        onClick: function(){
                        var form = $('#login_form');
                        form.submit();
                        }">登陆</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-eraser fa-lg', width: 80,
                        onClick: function(){
                        var form = $('#login_form');
                        form[0].reset();
                        }">重置</a>

            </td>
        </tr>
        </tbody>
    </table>

</form:form>

</body>

</html>