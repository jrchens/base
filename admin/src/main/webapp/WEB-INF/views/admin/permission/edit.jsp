<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="admin_permission_edit_form" method="post"
           modelAttribute="permission" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="权限管理-编辑"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/admin/permission/update">
    <form:hidden path="id"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>类别</td>
            <td><form:input path="category" cssClass="easyui-combobox"
                            data-options="required:true,fit:true,url:'${WEB_ROOT_CONTEXT}/resources/js/permission_category_data.json',method:'get',groupField:'group',groupField:'group'"/><form:errors
                    path="category"/></td>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="viewname"/></td>
        </tr>

        <tr>
            <td>权限名</td>
            <td colspan="2"><form:input path="permission" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="permission"/></td>
            <td>&nbsp;</td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                    $('#overlay').show();
                    var thisButton = $(this);
                    thisButton.linkbutton('disable');
                    thisButton.linkbutton({text:'加载中...'});
                    var form = $('#admin_permission_edit_form');
                    form.submit();
                    }">更新</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                    onClick: function(){
                    location.href = '${WEB_ROOT_CONTEXT}/admin/permission/index';
                    }">返回</a>
            </td>
        </tr>
        </tbody>
    </table>

</form:form>