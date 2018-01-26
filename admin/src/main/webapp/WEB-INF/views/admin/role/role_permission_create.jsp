<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>

<table id="admin_role_role_permission_create_datagrid" class="easyui-datagrid"
       data-options="title: '权限列表',
            url: '${WEB_ROOT_CONTEXT}/admin/permission/async-query',
            method: 'get',
            queryParams: {exclusiveRoleName:'${role.roleName}'},
            sortName: 'category',
            sortOrder: 'desc',
            pagination: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            toolbar: [
                        {
                            iconCls:'ext-icon fa fa-floppy-o',
                            handler:function(){
                                var rows = $('#admin_role_role_permission_create_datagrid').datagrid('getChecked');
                                if(rows.length == 0){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                var permissions = [];
                                $.each(rows,function(idx,ele){
                                    permissions.push(ele.permission);
                                });

                                var form = $('#admin_role_role_permission_create_form');
                                $('#permissions',form).val(permissions.join(','));
                                form.submit();

                            }
                        },{
                            iconCls:'ext-icon fa fa-arrow-left',
                            handler:function(){
                                location.href = '${WEB_ROOT_CONTEXT}/admin/role/detail?id=${role.id}';
                            }
                        }
            ],
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                return data.data;
            }
       ">
    <thead>
    <tr>
        <th data-options="field:'_',checkbox:true">#</th>
        <th data-options="field:'permission'">权限名</th>
        <th data-options="field:'viewname'">显示名</th>
    </tr>
    </thead>
</table>

<form id="admin_role_role_permission_create_form" method="post" action="${WEB_ROOT_CONTEXT}/admin/role-permission-relation/save">
    <input type="hidden" id="permissions" name="permissions" value="">
    <input type="hidden" id="id" name="id" value="${role.id}">
</form>