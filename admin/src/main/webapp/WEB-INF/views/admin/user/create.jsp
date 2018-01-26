<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="admin_user_create_form" method="post"
           modelAttribute="user" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="用户管理-新增"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/admin/user/save">
    <form:hidden path="disabled"/>
    <form:hidden path="locked"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>用户名</td>
            <td><form:input path="username" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="username"/></td>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="viewname"/></td>
        </tr>


        <tr>
            <td>邮箱</td>
            <td><form:input path="email" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors path="email"/></td>
            <td>密码</td>
            <td><form:input path="password" cssClass="easyui-passwordbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="password"/></td>
        </tr>

        <tr>
            <td>禁用</td>
            <td>
                    <%--<form:input path="btinyint" cssClass="easyui-switchbutton" data-options=""/>--%>
                    <%--onText:'Yes',offText:'No',reversed:true--%>
                <span class="easyui-switchbutton" data-options="onChange:function(checked){
                            var form = $('#admin_user_create_form');
                            $('#disabled',form).val(checked);
                        }"></span><form:errors path="disabled"/>
            </td>
            <td>锁定</td>
            <td>
                    <%--<form:input path="btinyint" cssClass="easyui-switchbutton" data-options=""/>--%>
                    <%--onText:'Yes',offText:'No',reversed:true--%>
                <span class="easyui-switchbutton" data-options="onChange:function(checked){
                            var form = $('#admin_user_create_form');
                            $('#locked',form).val(checked);
                        }"></span><form:errors path="locked"/>
            </td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-floppy-o ', width: 80,
                        onClick: function(){
                        $('#overlay').show();
                        var thisButton = $(this);
                        thisButton.linkbutton('disable');
                        thisButton.linkbutton({text:'加载中...'});
                        var form = $('#admin_user_create_form');
                        var username = $('#username',form).textbox('getValue');
                        var password = $('#password',form).passwordbox('getValue');
                        password = CryptoJS.MD5(username.concat(':').concat(CryptoJS.MD5(password).toString())).toString();
                        $('#password',form).passwordbox('setValue',password);
                        form.submit();
                        }">保存</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                        onClick: function(){
                        location.href = '${WEB_ROOT_CONTEXT}/admin/user/index';
                        }">返回</a>

            </td>
        </tr>
        </tbody>
    </table>

</form:form>