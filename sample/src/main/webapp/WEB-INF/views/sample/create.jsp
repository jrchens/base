<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-Hans">

<head>
    <meta charset="UTF-8">
    <title>样例管理-新增</title>

    <%@ include file="../common/css.jsp" %>
    <%@ include file="../common/js.jsp" %>

</head>

<body>
<div id="overlay"></div>
<c:if test="${not empty create_error}">
    <div class="easyui-panel" data-options="closable:true" title="错误信息"
         style="margin-bottom: 10px; color:red; padding-left: 5px; font-weight: bold;">${create_error}</div>
</c:if>
<c:if test="${not empty save_error}">
    <div class="easyui-panel" data-options="closable:true" title="错误信息"
         style="margin-bottom: 10px; color:red; padding-left: 5px; font-weight: bold;">${save_error}</div>
</c:if>

<form:form id="sample_create_form" method="post"
           modelAttribute="sample" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="样例管理-新增"
           data-options="inline: true" action="http://local.com/sample/save">
    <form:hidden path="btinyint"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>Code</td>
            <td><form:input path="bcode" cssClass="easyui-textbox" data-options="required:true,fit:true"/><form:errors
                    path="bcode"/></td>
            <td>Title</td>
            <td><form:input path="btitle" cssClass="easyui-textbox" data-options="required:true,fit:true"/><form:errors
                    path="btitle"/></td>
        </tr>

        <tr>
            <td>Int</td>
            <td><form:input path="bint" cssClass="easyui-numberbox"
                            data-options="required:true,fit:true,min:0"/><form:errors path="bint"/></td>
            <td>Decimal</td>
            <td><form:input path="bnum" cssClass="easyui-numberbox"
                            data-options="required:true,fit:true,min:0,precision:2"/><form:errors path="bnum"/></td>
        </tr>

        <tr>
            <td>Date</td>
            <td><form:input path="bdate" cssClass="easyui-datebox"
                            data-options="required:true,editable:false"/><form:errors path="bdate"/>
                <span style="margin-left: 24px; margin-right: 24px;">Datetime</span>
                <form:input path="bdatetime" cssClass="easyui-datetimebox"
                            data-options="required:true,editable:false,width:160"/><form:errors path="bdatetime"/>
            </td>
            <td>Boolean</td>
            <td>
                    <%--<form:input path="btinyint" cssClass="easyui-switchbutton" data-options=""/>--%>
                    <%--onText:'Yes',offText:'No',reversed:true--%>
                <span class="easyui-switchbutton" data-options="onChange:function(checked){
                            var form = $('#sample_create_form');
                            $('#btinyint',form).val(checked);
                        }"></span><form:errors path="btinyint"/>
            </td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-floppy-o fa-lg', width: 80,
                        onClick: function(){
                        $('#overlay').show();
                        var thisButton = $(this);
                        thisButton.linkbutton('disable');
                        thisButton.linkbutton({text:'加载中...'});
                        var form = $('#sample_create_form');
                        form.submit();
                        }">保存</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left fa-lg', width: 80,
                        onClick: function(){
                        location.href = 'http://local.com/sample/index';
                        }">返回</a>

            </td>
        </tr>
        </tbody>
    </table>

</form:form>

</body>

</html>