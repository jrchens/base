<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<table id="wx_tag_index_datagrid" class="easyui-datagrid"
       data-options="title: '标签管理-列表',
            url: 'http://local.com/wx/tag/async-query',
            method: 'get',
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            toolbar: '#wx_tag_query_form',
            onDblClickRow: function(index,row){
                location.href = 'http://local.com/wx/tag/detail?id='+row.id;
            },
            onClickRow: function(index,row){
                var btn = $('#wx_tag_index_clipboard_button');
                btn.attr('data-clipboard-text',row.id);
                btn.click();
            },
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                var pager = $('#wx_tag_index_datagrid').datagrid('getPager');
                pager.pagination({
                    buttons:[
                        {   iconCls:'ext-icon fa fa-plus',
                            handler:function(){
                                location.href = 'http://local.com/wx/tag/create';
                            }
                        },
                        {   iconCls:'ext-icon fa fa-pencil',
                            handler:function(){
                                var row = $('#wx_tag_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = 'http://local.com/wx/tag/edit?id='+row.id;
                            }
                        },
                        {   iconCls:'ext-icon fa fa-trash',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#wx_tag_index_datagrid').datagrid('getSelected');
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
                                        $.post('http://local.com/wx/tag/async-delete',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#wx_tag_index_datagrid').datagrid('reload');
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
                                var row = $('#wx_tag_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = 'http://local.com/wx/tag/detail?id='+row.id;
                            }
                        }
                    ]
                });

                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'id'">标签编号</th>
        <th data-options="field:'name'">标签名</th>
    </tr>
    </thead>
</table>

<form:form id="wx_tag_query_form" method="post"
           modelAttribute="wxTag" cssStyle="padding: 5px; margin: 0px;"
           data-options="inline: true" action="http://local.com/wx/tag/async-query">
    <table class="ext-data-table" style="width: 100%" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
            <td>标签名</td>
            <td><form:input path="name" cssClass="easyui-textbox" data-options="fit:true"></form:input></td>
            <td style="text-align: left" colspan="2"><a href="javascript:;" class="easyui-linkbutton" data-options="
                    width: 80,
                    iconCls:'ext-icon fa fa-search',
                    onClick: function(){
                        var reqData = $('#wx_tag_query_form').serializeJSON();
                        $('#wx_tag_index_datagrid').datagrid('reload',reqData);
                    }">查询</a></td>
        </tr>
        </tbody>
    </table>
</form:form>



<div class="ext-div-line"></div>

<div class="ext-warning">
    点击表格行后，自动复制标签编号。
</div>

<div style="display: none">
    <button id="wx_tag_index_clipboard_button" class="btn" data-clipboard-text=""></button>
    <script type="text/javascript">
        var clipboard = new Clipboard('#wx_tag_index_clipboard_button');
    </script>
</div>