<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="admin_user_detail_form" method="post"
           modelAttribute="userVO" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="用户管理-详情"
           data-options="inline: true" action="http://local.com/admin/user/async-remove">
    <form:hidden path="id"/>
    <form:hidden path="disabled"/>
    <form:hidden path="locked"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>用户名</td>
            <td><form:input path="username" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="username"/></td>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="viewname"/></td>
        </tr>


        <tr>
            <td>邮箱</td>
            <td><form:input path="email" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors path="email"/></td>
            <td colspan="2">&nbsp;</td>
        </tr>

        <tr>
            <td>禁用</td>
            <td>
                    <%--<form:input path="btinyint" cssClass="easyui-switchbutton" data-options=""/>--%>
                    <%--onText:'Yes',offText:'No',reversed:true--%>
                <span class="easyui-switchbutton" data-options="
                    readonly:true,
                    checked:${userVO.disabled},
                    onChange:function(checked){
                            var form = $('#admin_user_create_form');
                            $('#disabled',form).val(checked);
                        }"></span><form:errors path="disabled"/>
            </td>
            <td>锁定</td>
            <td>
                    <%--<form:input path="btinyint" cssClass="easyui-switchbutton" data-options=""/>--%>
                    <%--onText:'Yes',offText:'No',reversed:true--%>
                <span class="easyui-switchbutton" data-options="
                    readonly:true,
                    checked:${userVO.locked},
                    onChange:function(checked){
                            var form = $('#admin_user_create_form');
                            $('#locked',form).val(checked);
                        }"></span><form:errors path="locked"/>
            </td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = 'http://local.com/admin/user/edit?id=${userVO.id}';
                    }">编辑</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,
                    onClick: function(){
                        var thisButton = $(this);

                        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                            if (r) {
                                // var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);
                                // $('#sys_group_datagrid').datagrid('deleteRow', index);

                                $('#overlay').show();
                                thisButton.linkbutton('disable')
                                thisButton.linkbutton({text:'加载中...'});

                                var reqData = {id:${userVO.id}};
                                $.post('http://local.com/admin/user/async-remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = 'http://local.com/admin/user/index';
                                    }else{
                                        $.messager.show({msg:data.message});
                                    }
                                },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                    $('#overlay').hide();
                                    thisButton.linkbutton('enable');
                                    thisButton.linkbutton({text:'删除'});
                                });

                            }
                        });

                    }">删除</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                    onClick: function(){
                    location.href = 'http://local.com/admin/user/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>