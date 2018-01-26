<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="wx_tag_detail_form" method="post"
           modelAttribute="wxTag" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="标签管理-详情"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/wx/tag/async-remove">
    <form:hidden path="id"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>标签名</td>
            <td><form:input path="name" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="name"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = '${WEB_ROOT_CONTEXT}/wx/tag/edit?id=${wxTag.id}';
                    }">编辑</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,
                    onClick: function(){
                        var thisButton = $(this);

                        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                            if (r) {
                                $('#overlay').show();
                                thisButton.linkbutton('disable')
                                thisButton.linkbutton({text:'加载中...'});

                                var reqData = {id:'${wxTag.id}'};
                                $.post('${WEB_ROOT_CONTEXT}/wx/tag/async-delete',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = '${WEB_ROOT_CONTEXT}/wx/tag/index';
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
                    location.href = '${WEB_ROOT_CONTEXT}/wx/tag/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>