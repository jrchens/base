<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>

<table id="admin_user_user_group_create_datagrid" class="easyui-datagrid"
       data-options="title: '群组列表',
            url: '${WEB_ROOT_CONTEXT}/admin/group/async-query',
            method: 'get',
            queryParams: {exclusiveGroupNames:'${exclusiveGroupNames}'},
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            toolbar: [
                        {
                            iconCls:'ext-icon fa fa-floppy-o',
                            handler:function(){
                                var rows = $('#admin_user_user_group_create_datagrid').datagrid('getChecked');
                                if(rows.length == 0){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                var groupNames = [];
                                $.each(rows,function(idx,ele){
                                    groupNames.push(ele.groupName);
                                });

                                var form = $('#admin_user_user_group_create_form');
                                $('#groupNames',form).val(groupNames.join(','));
                                form.submit();

                            }
                        },{
                            iconCls:'ext-icon fa fa-arrow-left',
                            handler:function(){
                                location.href = '${WEB_ROOT_CONTEXT}/admin/user/detail?id=${user.id}';
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
        <th data-options="field:'groupName'">群组名</th>
        <th data-options="field:'viewname'">显示名</th>
    </tr>
    </thead>
</table>

<form id="admin_user_user_group_create_form" method="post" action="${WEB_ROOT_CONTEXT}/admin/user-group-relation/save">
    <input type="hidden" id="groupNames" name="groupNames" value="">
    <input type="hidden" id="id" name="id" value="${user.id}">
</form>