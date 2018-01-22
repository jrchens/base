<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<table id="wx_config_index_datagrid" class="easyui-datagrid"
       data-options="title: '配置管理-列表',
            url: 'http://local.com/wx/config/async-query',
            method: 'get',
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            toolbar: '#wx_config_query_form',
            onDblClickRow: function(index,row){
                location.href = 'http://local.com/wx/config/detail?cfgCode='+row.cfgCode;
            },
            onClickRow: function(index,row){
                var btn = $('#wx_config_index_clipboard_button');
                btn.attr('data-clipboard-text',row.cfgCode);
                btn.click();
            },
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                var pager = $('#wx_config_index_datagrid').datagrid('getPager');
                pager.pagination({
                    buttons:[
                        <%--{   iconCls:'ext-icon fa fa-plus',--%>
                            <%--handler:function(){--%>
                                <%--location.href = 'http://local.com/wx/config/create';--%>
                            <%--}--%>
                        <%--},--%>
                        {   iconCls:'ext-icon fa fa-pencil',
                            handler:function(){
                                var row = $('#wx_config_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = 'http://local.com/wx/config/edit?cfgCode='+row.cfgCode;
                            }
                        }
                        <%--{   iconCls:'ext-icon fa fa-trash',--%>
                            <%--handler:function(){--%>
                                <%--var thisButton = $(this);--%>
                                <%--var row = $('#wx_config_index_datagrid').datagrid('getSelected');--%>
                                <%--if(row == null){--%>
                                    <%--$.messager.alert('提示', '请先选择一行记录!', 'warning');--%>
                                    <%--return false;--%>
                                <%--}--%>

                                <%--$.messager.confirm('确认', '确认删除记录吗?', function(r) {--%>
                                    <%--if (r) {--%>
                                        <%--// var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);--%>
                                        <%--// $('#sys_group_datagrid').datagrid('deleteRow', index);--%>

                                        <%--$('#overlay').show();--%>
                                        <%--thisButton.linkbutton('disable');--%>

                                        <%--var reqData = {cfgCode:row.cfgCode};--%>
                                        <%--$.post('http://local.com/wx/config/async-remove',reqData,function(data,textStatus,jqXHR){--%>
                                            <%--if(data.success){--%>
                                                <%--$('#wx_config_index_datagrid').datagrid('reload');--%>
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
                                <%--var row = $('#wx_config_index_datagrid').datagrid('getSelected');--%>
                                <%--if(row == null){--%>
                                    <%--$.messager.alert('提示', '请先选择一行记录!', 'warning');--%>
                                    <%--return false;--%>
                                <%--}--%>
                                <%--location.href = 'http://local.com/wx/config/detail?cfgCode='+row.cfgCode;--%>
                            <%--}--%>
                        <%--}--%>
                    ]
                });
                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'cfgCode'">代码</th>
        <th data-options="field:'cfgName'">名称</th>
        <%--<th data-options="field:'cfgType'">类型</th>--%>
        <th data-options="field:'cfgValue'">值</th>
    </tr>
    </thead>
</table>

<div class="ext-div-line"></div>

<div style="display: none">
    <button id="wx_config_index_clipboard_button" class="btn" data-clipboard-text=""></button>
    <script type="text/javascript">
        var clipboard = new Clipboard('#wx_config_index_clipboard_button');
    </script>
</div>

<form:form id="wx_config_query_form" method="post"
           modelAttribute="config" cssStyle="padding: 5px; margin: 0px;"
           data-options="inline: true" action="http://local.com/wx/config/async-query">
    <table class="ext-data-table" style="width: 100%" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
            <td>名称</td>
            <td><form:input path="cfgName" cssClass="easyui-textbox" data-options="fit:true"></form:input></td>
            <td colspan="2" style="text-align: left"><a href="javascript:;" class="easyui-linkbutton" data-options="
                    width: 80,
                    iconCls:'ext-icon fa fa-search',
                    onClick: function(){
                        var reqData = $('#wx_config_query_form').serializeJSON();
                        $('#wx_config_index_datagrid').datagrid('reload',reqData);
                    }">查询</a></td>
        </tr>
        </tbody>
    </table>
</form:form>