<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="admin_permission_create_form" method="post"
           modelAttribute="permission" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="权限管理-新增"
           data-options="inline: true" action="http://local.com/admin/permission/save">

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>类别</td>
            <td><form:input path="category" cssClass="easyui-combobox"
                            data-options="required:true,fit:true,url:'http://local.com/resources/js/permission_category_data.json',method:'get',groupField:'group',groupField:'group'"/><form:errors
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
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-floppy-o ', width: 80,
                        onClick: function(){
                        $('#overlay').show();
                        var thisButton = $(this);
                        thisButton.linkbutton('disable');
                        thisButton.linkbutton({text:'加载中...'});
                        var form = $('#admin_permission_create_form');
                        form.submit();
                        }">保存</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                        onClick: function(){
                        location.href = 'http://local.com/admin/permission/index';
                        }">返回</a>

            </td>
        </tr>
        </tbody>
    </table>

</form:form>