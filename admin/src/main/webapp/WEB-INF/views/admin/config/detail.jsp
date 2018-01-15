<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="admin_config_detail_form" method="post"
           modelAttribute="config" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="配置管理-详情"
           data-options="inline: true" action="http://local.com/admin/config/async-remove">
    <form:hidden path="cfgCode"/>
    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>名称</td>
            <td><form:input path="cfgName" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="cfgName"/></td>
            <td>值</td>
            <td><form:input path="cfgValue" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors path="cfgValue"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = 'http://local.com/admin/config/edit?cfgCode=${config.cfgCode}';
                    }">编辑</a>
                <%--<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,--%>
                    <%--onClick: function(){--%>
                        <%--var thisButton = $(this);--%>

                        <%--$.messager.confirm('确认', '确认删除记录吗?', function(r) {--%>
                            <%--if (r) {--%>
                                <%--// var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);--%>
                                <%--// $('#sys_group_datagrid').datagrid('deleteRow', index);--%>

                                <%--$('#overlay').show();--%>
                                <%--thisButton.linkbutton('disable')--%>
                                <%--thisButton.linkbutton({text:'加载中...'});--%>

                                <%--var reqData = {cfgCode:'${config.cfgCode}'};--%>
                                <%--$.post('http://local.com/admin/config/async-remove',reqData,function(data,textStatus,jqXHR){--%>
                                    <%--if(data.success){--%>
                                        <%--location.href = 'http://local.com/admin/config/index';--%>
                                    <%--}else{--%>
                                        <%--$.messager.show({msg:data.message});--%>
                                    <%--}--%>
                                <%--},'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){--%>
                                    <%--$('#overlay').hide();--%>
                                    <%--thisButton.linkbutton('enable');--%>
                                    <%--thisButton.linkbutton({text:'删除'});--%>
                                <%--});--%>

                            <%--}--%>
                        <%--});--%>

                    <%--}">删除</a>--%>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                    onClick: function(){
                    location.href = 'http://local.com/admin/config/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>