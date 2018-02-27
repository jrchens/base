<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="cms_article_detail_form" method="post"
           modelAttribute="article" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="文章管理-详情"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/cms/article/async-remove">
    <form:hidden path="id"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>文章名</td>
            <td><form:input path="articleName" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="articleName"/></td>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="viewname"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = '${WEB_ROOT_CONTEXT}/cms/article/edit?id=${article.id}';
                    }">编辑</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,
                    onClick: function(){
                        var thisButton = $(this);

                        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                            if (r) {
                                // var index = $('#sys_article_datagrid').datagrid('getRowIndex', row);
                                // $('#sys_article_datagrid').datagrid('deleteRow', index);

                                $('#overlay').show();
                                thisButton.linkbutton('disable')
                                thisButton.linkbutton({text:'加载中...'});

                                var reqData = {id:'${article.id}'};
                                $.post('${WEB_ROOT_CONTEXT}/cms/article/async-remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = '${WEB_ROOT_CONTEXT}/cms/article/index';
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
                    location.href = '${WEB_ROOT_CONTEXT}/cms/article/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>