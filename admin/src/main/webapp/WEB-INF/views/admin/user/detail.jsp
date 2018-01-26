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
           modelAttribute="user" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="用户管理-详情"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/admin/user/async-remove">
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
                    checked:${user.disabled},
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
                    checked:${user.locked},
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
                        location.href = '${WEB_ROOT_CONTEXT}/admin/user/edit?id=${user.id}';
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

                                var reqData = {id:'${user.id}'};
                                $.post('${WEB_ROOT_CONTEXT}/admin/user/async-remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = '${WEB_ROOT_CONTEXT}/admin/user/index';
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
                    location.href = '${WEB_ROOT_CONTEXT}/admin/user/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>
<div class="ext-div-line"></div>
<table id="admin_user_detail_group_datagrid" class="easyui-datagrid"
       data-options="title: '群组列表',
            url: '${WEB_ROOT_CONTEXT}/admin/user-group-relation/async-group-query',
            method: 'get',
            queryParams: {username:'${user.username}'},
            cls: 'ext-datagrid-float-left',
            width:450,
            pagination: true,
            sortName: 'rel.id',
            sortOrder: 'desc',
            singleSelect: true,
            rownumbers: true,
            minHeight: 300,
            striped: true,
            onClickRow: function(index,row){
                if(row.def){
                    $('#admin_user_detail_group_datagrid_delete').linkbutton('disable');
                    $('#admin_user_detail_group_datagrid_tag').linkbutton('disable');
                }else{
                    $('#admin_user_detail_group_datagrid_delete').linkbutton('enable');
                    $('#admin_user_detail_group_datagrid_tag').linkbutton('enable');
                }
            },
            toolbar: [
                        {   iconCls:'ext-icon fa fa-plus',
                            handler:function(){
                                location.href = '${WEB_ROOT_CONTEXT}/admin/user-group-relation/create?id=${user.id}';
                            }
                        },
                        {
                            id: 'admin_user_detail_group_datagrid_delete',
                            iconCls:'ext-icon fa fa-trash',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#admin_user_detail_group_datagrid').datagrid('getSelected');
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
                                        $.post('${WEB_ROOT_CONTEXT}/admin/user-group-relation/async-delete',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#admin_user_detail_group_datagrid').datagrid('reload');
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
                        },
                        {
                            id: 'admin_user_detail_group_datagrid_tag',
                            iconCls:'ext-icon fa fa-tag',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#admin_user_detail_group_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }

                                        // var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);
                                        // $('#sys_group_datagrid').datagrid('deleteRow', index);

                                        $('#overlay').show();
                                        thisButton.linkbutton('disable');

                                        var reqData = {id:row.id};
                                        $.post('${WEB_ROOT_CONTEXT}/admin/user-group-relation/async-update',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#admin_user_detail_group_datagrid').datagrid('reload');
                                            }else{
                                                $.messager.show({msg:data.message});
                                            }
                                        },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                            $('#overlay').hide();
                                            thisButton.linkbutton('enable');
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
        <th data-options="field:'groupName'">群组名</th>
        <th data-options="field:'viewname'">显示名</th>
        <th data-options="field:'def'">默认组</th>
    </tr>
    </thead>
</table>



<table id="admin_user_detail_role_datagrid" class="easyui-datagrid"
       data-options="title: '角色列表',
            url: '${WEB_ROOT_CONTEXT}/admin/user-role-relation/async-role-query',
            method: 'get',
            queryParams: {username:'${user.username}'},
            cls: 'ext-datagrid-float-left',
            width:450,
            sortName: 'rel.id',
            sortOrder: 'desc',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 300,
            striped: true,
            onClickRow: function(index,row){
                if(row.def){
                    $('#admin_user_detail_role_datagrid_delete').linkbutton('disable');
                    $('#admin_user_detail_role_datagrid_tag').linkbutton('disable');
                }else{
                    $('#admin_user_detail_role_datagrid_delete').linkbutton('enable');
                    $('#admin_user_detail_role_datagrid_tag').linkbutton('enable');
                }
            },
            toolbar: [
                        {   iconCls:'ext-icon fa fa-plus',
                            handler:function(){
                                location.href = '${WEB_ROOT_CONTEXT}/admin/user-role-relation/create?id=${user.id}';
                            }
                        },
                        {
                            id: 'admin_user_detail_role_datagrid_delete',
                            iconCls:'ext-icon fa fa-trash',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#admin_user_detail_role_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }

                                $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                                    if (r) {
                                        // var index = $('#sys_role_datagrid').datagrid('getRowIndex', row);
                                        // $('#sys_role_datagrid').datagrid('deleteRow', index);

                                        $('#overlay').show();
                                        thisButton.linkbutton('disable');

                                        var reqData = {id:row.id};
                                        $.post('${WEB_ROOT_CONTEXT}/admin/user-role-relation/async-delete',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#admin_user_detail_role_datagrid').datagrid('reload');
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
                        },
                        {
                            id: 'admin_user_detail_role_datagrid_tag',
                            iconCls:'ext-icon fa fa-tag',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#admin_user_detail_role_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }

                                        // var index = $('#sys_role_datagrid').datagrid('getRowIndex', row);
                                        // $('#sys_role_datagrid').datagrid('deleteRow', index);

                                        $('#overlay').show();
                                        thisButton.linkbutton('disable');

                                        var reqData = {id:row.id};
                                        $.post('${WEB_ROOT_CONTEXT}/admin/user-role-relation/async-update',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#admin_user_detail_role_datagrid').datagrid('reload');
                                            }else{
                                                $.messager.show({msg:data.message});
                                            }
                                        },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                            $('#overlay').hide();
                                            thisButton.linkbutton('enable');
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
        <th data-options="field:'roleName'">角色名</th>
        <th data-options="field:'viewname'">显示名</th>
        <th data-options="field:'def'">默认</th>
    </tr>
    </thead>
</table>