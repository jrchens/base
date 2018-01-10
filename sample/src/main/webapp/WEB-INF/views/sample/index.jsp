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
            toolbar: '#sample_query_form',
            onDblClickRow: function(index,row){
                location.href = 'http://local.com/sample/detail?id='+row.id;
            },
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                var pager = $('#sample_index_datagrid').datagrid('getPager');
                pager.pagination({
                    buttons:[
                        {   iconCls:'ext-icon fa fa-plus',
                            handler:function(){
                                location.href = 'http://local.com/sample/create';
                            }
                        },
                        {   iconCls:'ext-icon fa fa-pencil',
                            handler:function(){
                                var row = $('#sample_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = 'http://local.com/sample/edit?id='+row.id;
                            }
                        },
                        {   iconCls:'ext-icon fa fa-trash',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#sample_index_datagrid').datagrid('getSelected');
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
                                        $.post('http://local.com/sample/remove',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                $('#sample_index_datagrid').datagrid('reload');
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
                                var row = $('#sample_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }
                                location.href = 'http://local.com/sample/detail?id='+row.id;
                            }
                        }
                    ]
                });

                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'id'">序号</th>
        <th data-options="field:'bcode'">代码</th>
        <th data-options="field:'btitle',width:200">标题</th>
        <th data-options="field:'bint'">整数</th>
        <th data-options="field:'bnum'">小数</th>
        <th data-options="field:'bdate',
                formatter: function(value,row,index){
                    return moment(value).format(moment.HTML5_FMT.DATE);
                }">日期
        </th>
        <th data-options="field:'bdatetime',
                formatter: function(value,row,index){
                    return moment(value).format(moment.HTML5_FMT.DATETIME_LOCAL_SECONDS);
                }">时间
        </th>
        <th data-options="field:'btinyint'">布尔值</th>
    </tr>
    </thead>
</table>

<form:form id="sample_query_form" method="post"
           modelAttribute="sample" cssStyle="padding: 5px; margin: 0px;"
           data-options="inline: true" action="http://local.com/sample/query">
    <table class="ext-data-table" style="width: 100%" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>代码</td>
                <td><form:input path="bcode" cssClass="easyui-textbox" data-options="fit:true"></form:input></td>
                <td>标题</td>
                <td><form:input path="btitle" cssClass="easyui-textbox" data-options="fit:true"></form:input></td>
                <td style="text-align: left"><a href="javascript:;" class="easyui-linkbutton" data-options="
                    iconCls:'ext-icon fa fa-search',
                    onClick: function(){
                        var reqData = $('#sample_query_form').serializeJSON();
                        console.log(reqData);
                        $('#sample_index_datagrid').datagrid('reload',reqData);
                    }"></a></td>
            </tr>
        </tbody>
    </table>
</form:form>