<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<table id="sample_index_datagrid" class="easyui-datagrid"
       data-options="title: '样例管理-列表',
            url: 'http://local.com/sample/query',
            method: 'get',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                return data.data;
            },
            toolbar:[
                {   iconCls:'ext-icon fa fa-plus fa-lg',
                    handler:function(){
                        location.href = 'http://local.com/sample/create';
                    }
                },
                {   iconCls:'ext-icon fa fa-pencil fa-lg',
                    handler:function(){
                        var row = $('#sample_index_datagrid').datagrid('getSelected');
                        if(row == null){
                            $.messager.alert('Info', 'Please select a edit row!', 'warning');
                            return false;
                        }
                        location.href = 'http://local.com/sample/edit?id='+row.id;
                    }
                },
                {   iconCls:'ext-icon fa fa-trash fa-lg',
                    handler:function(){
                        var thisButton = $(this);
                        var row = $('#sample_index_datagrid').datagrid('getSelected');
                        if(row == null){
                            $.messager.alert('Info', 'Please select a delete row!', 'warning');
                            return false;
                        }

                        $.messager.confirm('Confirm', 'Are you sure to delete select row?', function(r) {
                            if (r) {
                                // var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);
                                // $('#sys_group_datagrid').datagrid('deleteRow', index);

                                $('#overlay').show();
                                thisButton.linkbutton('disable');

                                var reqData = {id:row.id};
                                $.post('http://local.com/sample/async-remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        $('#sample_index_datagrid').datagrid('reload');
                                    }else{
                                        $.messager.show({msg:data.message});
                                    }
                                },'json').aways(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                    $('#overlay').hide();
                                    thisButton.linkbutton('enable');
                                });

                            }
                        });
                    }
                },
                {   iconCls:'ext-icon fa fa-info fa-lg',
                    handler:function(){
                        var row = $('#sample_index_datagrid').datagrid('getSelected');
                        if(row == null){
                            $.messager.alert('Info', 'Please select a row!', 'warning');
                            return false;
                        }
                        location.href = 'http://local.com/sample/detail?id='+row.id;
                    }
                }
            ]
       ">
    <thead>
    <tr>
        <th data-options="field:'id'">Id</th>
        <th data-options="field:'bcode'">Code</th>
        <th data-options="field:'btitle'">Title</th>
        <th data-options="field:'bint'">Int</th>
        <th data-options="field:'bnum'">Decimal</th>
        <th data-options="field:'bdate',
                formatter: function(value,row,index){
                    return moment(value).format(moment.HTML5_FMT.DATE);
                }">Date
        </th>
        <th data-options="field:'bdatetime',
                formatter: function(value,row,index){
                    return moment(value).format(moment.HTML5_FMT.DATETIME_LOCAL_SECONDS);
                }">Create Time
        </th>
        <th data-options="field:'btinyint'">Boolean</th>

    </tr>
    </thead>
</table>

<%--<div id="toolbar" style="margin: 0px; padding: 5px;">--%>
    <%--<div id="toolbarQueryParams" style="margin: 5px; padding: 0px;">--%>
        <%--<div style="padding: 0px; margin: 0px;">--%>
            <%--<span>Bcode </span><input class="easyui-textbox" name="bcode">--%>
            <%--<span>Btitle </span><input class="easyui-textbox" name="btitle">--%>
            <%--<span><a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'ext-icon fa fa-search fa-lg'"></a></span>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div id="toolbarLinkbuttons" style="margin: 5px; padding: 0px;">--%>
        <%--<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'ext-icon fa fa-plus fa-lg'"></a>--%>
        <%--<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'ext-icon fa fa-pencil fa-lg'"></a>--%>
        <%--<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'ext-icon fa fa-trash fa-lg'"></a>--%>
        <%--<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'ext-icon fa fa-info fa-lg'"></a>--%>
    <%--</div>--%>
<%--</div>--%>