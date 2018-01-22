<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="wx_config_edit_form" method="post"
           modelAttribute="config" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="配置管理-编辑"
           data-options="inline: true" action="http://local.com/wx/config/update">
    <form:hidden path="cfgCode"/>
    <form:hidden path="cfgGroup"/>
    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>名称</td>
            <td><form:input path="cfgName" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="cfgName"/></td>
            <td>值</td>
            <td><form:input path="cfgValue" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors path="cfgValue"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                    $('#overlay').show();
                    var thisButton = $(this);
                    thisButton.linkbutton('disable');
                    thisButton.linkbutton({text:'加载中...'});
                    var form = $('#wx_config_edit_form');
                    form.submit();
                    }">更新</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                    onClick: function(){
                    location.href = 'http://local.com/wx/config/index';
                    }">返回</a>
            </td>
        </tr>
        </tbody>
    </table>

</form:form>