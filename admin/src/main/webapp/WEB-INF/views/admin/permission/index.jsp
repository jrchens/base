<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<table id="admin_permission_index_datagrid" class="easyui-datagrid"
       data-options="title: '权限管理-列表',
            url: '${WEB_ROOT_CONTEXT}/admin/permission/async-query',
            method: 'get',
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            toolbar: '#admin_permission_query_form',
            onDblClickRow: function(index,row){
                location.href = '${WEB_ROOT_CONTEXT}/admin/permission/detail?id='+row.id;
            },
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                var pager = $('#admin_permission_index_datagrid').datagrid('getPager');
                pager.pagination({
                    buttons:[
                        {   iconCls:'ext-icon fa fa-plus',
                            handler:function(){
                                location.href = '${WEB_ROOT_CONTEXT}/admin/permission/create';
                            }
                        },
                        {   iconCls:'ext-icon fa fa-pencil',
                            handler:function(){
                                var row = $('#admin_permission_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = '${WEB_ROOT_CONTEXT}/admin/permission/edit?id='+row.id;
                            }
                        },
                        {   iconCls:'ext-icon fa fa-trash',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#admin_permission_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }

                                $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                                    if (r) {
                                        // var index = $('#sys_permission_datagrid').datagrid('getRowIndex', row);
                                        // $('#sys_permission_datagrid').datagrid('deleteRow', index);

                                        $('#overlay').show();
                                        thisButton.linkbutton('disable');

                                        var reqData = {id:row.id};
                                        $.post('${WEB_ROOT_CONTEXT}/admin/permission/async-remove',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#admin_permission_index_datagrid').datagrid('reload');
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
                        {   iconCls:'ext-icon fa fa-eye',
                            handler:function(){
                                var row = $('#admin_permission_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = '${WEB_ROOT_CONTEXT}/admin/permission/detail?id='+row.id;
                            }
                        }
                    ]
                });

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

<form:form id="admin_permission_query_form" method="post"
           modelAttribute="permission" cssStyle="padding: 5px; margin: 0px;"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/admin/permission/async-query">
    <table class="ext-data-table" style="width: 100%" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
            <td>类别</td>
            <td><form:input path="category" cssClass="easyui-combobox" data-options="fit:true,url:'${WEB_ROOT_CONTEXT}/resources/js/permission_category_data.json',method:'get',groupField:'group',groupField:'group'"></form:input></td>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox" data-options="fit:true"></form:input></td>
            <td colspan="2" style="text-align: left"><a href="javascript:;" class="easyui-linkbutton" data-options="
                    width: 80,
                    iconCls:'ext-icon fa fa-search',
                    onClick: function(){
                        var reqData = $('#admin_permission_query_form').serializeJSON();
                        $('#admin_permission_index_datagrid').datagrid('reload',reqData);
                    }">查询</a></td>
        </tr>
        </tbody>
    </table>
</form:form>