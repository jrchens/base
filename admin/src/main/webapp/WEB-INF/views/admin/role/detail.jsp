<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="admin_role_detail_form" method="post"
           modelAttribute="role" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="角色管理-详情"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/admin/role/async-remove">
    <form:hidden path="id"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>角色名</td>
            <td><form:input path="roleName" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="roleName"/></td>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="viewname"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = '${WEB_ROOT_CONTEXT}/admin/role/edit?id=${role.id}';
                    }">编辑</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,
                    onClick: function(){
                        var thisButton = $(this);

                        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                            if (r) {
                                // var index = $('#sys_role_datagrid').datagrid('getRowIndex', row);
                                // $('#sys_role_datagrid').datagrid('deleteRow', index);

                                $('#overlay').show();
                                thisButton.linkbutton('disable')
                                thisButton.linkbutton({text:'加载中...'});

                                var reqData = {id:'${role.id}'};
                                $.post('${WEB_ROOT_CONTEXT}/admin/role/async-remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = '${WEB_ROOT_CONTEXT}/admin/role/index';
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
                    location.href = '${WEB_ROOT_CONTEXT}/admin/role/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>

<div class="ext-div-line"></div>
<table id="admin_role_detail_user_datagrid" class="easyui-datagrid"
       data-options="title: '用户列表',
            url: '${WEB_ROOT_CONTEXT}/admin/user-role-relation/async-user-query',
            method: 'get',
            queryParams: {roleName:'${role.roleName}'},
            cls: 'ext-datagrid-float-left',
            width:450,
            pagination: true,
            sortName: 'rel.id',
            sortOrder: 'desc',
            singleSelect: true,
            rownumbers: true,
            minHeight: 300,
            striped: true,
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'username'">用户名</th>
        <th data-options="field:'viewname'">显示名</th>
        <th data-options="field:'def'">默认</th>
    </tr>
    </thead>
</table>

<table id="admin_role_detail_permission_datagrid" class="easyui-datagrid"
       data-options="title: '权限列表',
            url: '${WEB_ROOT_CONTEXT}/admin/role-permission-relation/async-permission-query',
            method: 'get',
            queryParams: {roleName:'${role.roleName}'},
            cls: 'ext-datagrid-float-left',
            width:450,
            pagination: true,
            sortName: 'rel.id',
            sortOrder: 'desc',
            singleSelect: true,
            rownumbers: true,
            minHeight: 300,
            striped: true,
            toolbar: [
                        {   iconCls:'ext-icon fa fa-plus',
                            handler:function(){
                                location.href = '${WEB_ROOT_CONTEXT}/admin/role-permission-relation/create?id=${role.id}';
                            }
                        },
                        {
                            id: 'admin_role_detail_permission_datagrid_delete',
                            iconCls:'ext-icon fa fa-trash',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#admin_role_detail_permission_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }

                                $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                                    if (r) {
                                        // var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);
                                        // $('#sys_group_datagrid').datagrid('deleteRow', index);

                                        $('#overlay').show();
                                        thisButton.linkbutton('disable');

                                        var reqData = {id:row.id};
                                        $.post('${WEB_ROOT_CONTEXT}/admin/role-permission-relation/async-delete',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#admin_role_detail_permission_datagrid').datagrid('reload');
                                            }else{
                                                $.messager.show({msg:data.message});
                                            }
                                        },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                            $('#overlay').hide();
                                            thisButton.linkbutton('enable');
                                        });

                                    }
                                });
                            }
                        }
            ],
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'permission'">权限名</th>
        <th data-options="field:'viewname'">显示名</th>
    </tr>
    </thead>
</table>