<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>

<form id="attachment_save_form" method="post" class="easyui-panel" title="附件管理-上传" enctype="multipart/form-data"
      data-options="inline: true" action="${WEB_ROOT_CONTEXT}/attachment/save">
    <table class="ext-data-table" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
            <td><input name="file" class="easyui-filebox" data-options="buttonText:'请选择文件',fit:true,multiple:true,
                accept:'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/pdf,application/zip,image/png,image/jpeg'
                "></td>
            <td>
                <a href="javascript:;" class="easyui-linkbutton" data-options="
                    width: 80,
                    iconCls:'ext-icon fa fa-upload',
                    onClick: function(){
                        var form = $('#attachment_save_form');
                        form.submit();
                    }">上传</a>
            </td>
            <td>
                <span style="color: red; font-weight: bold">(xls,xlsx,doc,docx,ppt,pptx,pdf,zip,jpg,jpeg,png)</span>
            </td>
        </tr>
        </tbody>
    </table>
</form>

<div class="ext-div-line"></div>

<table id="attachment_index_datagrid" class="easyui-datagrid"
       data-options="title: '附件管理-列表',
            url: '${WEB_ROOT_CONTEXT}/attachment/async-query',
            method: 'get',
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 520,
            striped: true,
            toolbar: '#attachment_index_query_form',
            onClickRow: function(index,row){
                var file = '${downloadDomain}' + row.savePath + '/' + row.fileName;
                var btn = $('#attachment_index_clipboard_button');
                btn.attr('data-clipboard-text',file);
                btn.click();
            },
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }

                <%--var pager = $('#attachment_index_datagrid').datagrid('getPager');--%>
                <%--pager.pagination({--%>
                    <%--buttons:[--%>
                        <%--{--%>
                            <%--iconCls:'ext-icon fa fa-clipboard',--%>
                            <%--handler:function(){--%>
                                <%--var row = $('#attachment_index_datagrid').datagrid('getSelected');--%>
                                <%--if(row == null){--%>
                                    <%--$.messager.alert('提示', '请先选择一行记录!', 'warning');--%>
                                    <%--return false;--%>
                                <%--}--%>
                                <%--var file = row.savePath + '/' + row.fileName;--%>
                                <%--var btn = $('#attachment_index_clipboard_button');--%>
                                <%--btn.attr('data-clipboard-text',file);--%>
                                <%--btn.click();--%>
                            <%--}--%>
                        <%--}--%>
                    <%--]--%>
                <%--});--%>

                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'originalFileName'">原文件名</th>
        <th data-options="field:'fileSize',formatter: function(value,row,index){return filesize(value);}">大小</th>
        <th data-options="field:'fileSha1'">Hash</th>
        <th data-options="field:'owner'">上传用户</th>
        <%--<th data-options="field:'_',--%>
            <%--formatter: function(value,row,index){--%>
            <%--var file = row.savePath + '/' + row.fileName;--%>
            <%--return '<button class=\'btn fa fa-clipboard\' data-clipboard-text=\''+file+'\'></button>';--%>
        <%--}">操作</th>--%>
    </tr>
    </thead>
</table>

<div class="ext-div-line"></div>

<div class="ext-warning">
    点击表格行后，自动复制文件上传后的链接。
</div>

<form id="attachment_index_query_form" method="post"
           style="padding: 5px; margin: 0px;"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/attachment/async-query">
    <table class="ext-data-table" style="width: 100%" cellspacing="0" cellpadding="0">
        <tbody>
        <tr>
            <td>原文件名</td>
            <td><input name="originalFileName" class="easyui-textbox" data-options="fit:true"></input></td>
            <td>Hash</td>
            <td><input name="fileSha1" class="easyui-textbox" data-options="fit:true"></input></td>
            <td colspan="2" style="text-align: left"><a href="javascript:;" class="easyui-linkbutton" data-options="
                    width: 80,
                    iconCls:'ext-icon fa fa-search',
                    onClick: function(){
                        var reqData = $('#attachment_index_query_form').serializeJSON();
                        $('#attachment_index_datagrid').datagrid('reload',reqData);
                    }">查询</a></td>
        </tr>
        </tbody>
    </table>
</form>

<div style="display: none">
    <button id="attachment_index_clipboard_button" class="btn" data-clipboard-text="">

    </button>
    <script type="text/javascript">
        var clipboard = new Clipboard('#attachment_index_clipboard_button');
    </script>
</div>