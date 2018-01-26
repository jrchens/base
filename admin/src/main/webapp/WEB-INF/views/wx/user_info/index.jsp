<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<table id="wx_user_info_index_datagrid" class="easyui-datagrid"
       data-options="title: '关注用户管理-列表',
            url: '${WEB_ROOT_CONTEXT}/wx/user-info/async-query',
            method: 'get',
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            toolbar: '#wx_user_info_query_form',
            onDblClickRow: function(index,row){
                location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/detail?id='+row.id;
            },
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                var pager = $('#wx_user_info_index_datagrid').datagrid('getPager');
                pager.pagination({
                    buttons:[
                        <%--{   iconCls:'ext-icon fa fa-plus',--%>
                            <%--handler:function(){--%>
                                <%--location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/create';--%>
                            <%--}--%>
                        <%--},--%>
                        {   iconCls:'ext-icon fa fa-pencil',
                            handler:function(){
                                var row = $('#wx_user_info_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/edit?id='+row.id;
                            }
                        },
                        <%--{   iconCls:'ext-icon fa fa-trash',--%>
                            <%--handler:function(){--%>
                                <%--var thisButton = $(this);--%>
                                <%--var row = $('#wx_user_info_index_datagrid').datagrid('getSelected');--%>
                                <%--if(row == null){--%>
                                    <%--$.messager.alert('提示', '请先选择一行记录!', 'warning');--%>
                                    <%--return false;--%>
                                <%--}--%>

                                <%--$.messager.confirm('确认', '确认删除记录吗?', function(r) {--%>
                                    <%--if (r) {--%>

                                        <%--$('#overlay').show();--%>
                                        <%--thisButton.linkbutton('disable');--%>

                                        <%--var reqData = {id:row.id};--%>
                                        <%--$.post('${WEB_ROOT_CONTEXT}/wx/user-info/async-remove',reqData,function(data,textStatus,jqXHR){--%>
                                            <%--if(data.success){--%>
                                                <%--$('#wx_user_info_index_datagrid').datagrid('reload');--%>
                                            <%--}else{--%>
                                                <%--$.messager.show({msg:data.message});--%>
                                            <%--}--%>
                                        <%--},'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){--%>
                                            <%--$('#overlay').hide();--%>
                                            <%--thisButton.linkbutton('enable');--%>
                                        <%--});--%>

                                    <%--}--%>
                                <%--});--%>
                            <%--}--%>
                        <%--},--%>
                        <%--{   iconCls:'ext-icon fa fa-eye',--%>
                            <%--handler:function(){--%>
                                <%--var row = $('#wx_user_info_index_datagrid').datagrid('getSelected');--%>
                                <%--if(row == null){--%>
                                    <%--$.messager.alert('提示', '请先选择一行记录!', 'warning');--%>
                                    <%--return false;--%>
                                <%--}--%>
                                <%--location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/detail?id='+row.id;--%>
                            <%--}--%>
                        <%--}--%>
                        {   iconCls:'ext-icon fa fa-cloud-download',
                            handler:function(){
                                location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/download';
                            }
                        }
                    ]
                });

                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'viewname'">显示名</th>
        <th data-options="field:'mobile'">手机号</th>
        <th data-options="field:'nickname'">微信昵称</th>
        <th data-options="field:'sex',formatter:function(value,row,index){
        <%--值为1时是男性，值为2时是女性，值为0时是未知--%>
            if(1 == value){
                return '男';
            }else if(2 == value){
                return '女';
            }else{
                return '未知';
            }
        }">性别</th>
        <th data-options="field:'enable'">启用</th>
    </tr>
    </thead>
</table>

<form:form id="wx_user_info_query_form" method="post"
           modelAttribute="wxUserInfo" cssStyle="padding: 5px; margin: 0px;"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/wx/user-info/async-query">
    <table class="ext-data-table" style="width: 100%" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox" data-options="fit:true"></form:input></td>
            <td>手机号</td>
            <td><form:input path="mobile" cssClass="easyui-textbox" data-options="fit:true"></form:input></td>
            <td colspan="2" style="text-align: left"><a href="javascript:;" class="easyui-linkbutton" data-options="
                    width: 80,
                    iconCls:'ext-icon fa fa-search',
                    onClick: function(){
                        var reqData = $('#wx_user_info_query_form').serializeJSON();
                        $('#wx_user_info_index_datagrid').datagrid('reload',reqData);
                    }">查询</a></td>
        </tr>
        </tbody>
    </table>
</form:form>