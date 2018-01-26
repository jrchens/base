<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="wx_user_info_edit_form" method="post"
           modelAttribute="wxUserInfo" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="关注用户管理-编辑"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/wx/user-info/update">
    <form:hidden path="id"/>
    <form:hidden path="enable"/>
    <form:hidden path="openid"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="viewname"/></td>
            <td>手机号</td>
            <td><form:input path="mobile" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="mobile"/></td>
        </tr>

        <tr>
            <td>微信昵称</td>
            <td><form:input path="nickname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="nickname"/></td>
            <td>性别</td>
            <td>
                <form:select path="sex" cssClass="easyui-combobox" data-options="required:true,fit:true">
                    <form:option value="0">未知</form:option>
                    <form:option value="1">男</form:option>
                    <form:option value="2">女</form:option>
                </form:select></td>
        </tr>

        <tr>
            <td>启用</td>
            <td><span class="easyui-switchbutton"
                      data-options="checked:${empty wxUserInfo.enable ? false : wxUserInfo.enable},onChange:function(checked){
                            var form = $('#wx_user_info_edit_form');
                            $('#enable',form).val(checked);
                        }"></span>
                <form:errors path="enable"/></td>
            <td>标签</td>
            <td>
                <c:forEach var="tag" items="${wxTagList}" varStatus="status">
                    <label for="tag_${status.index}">
                        <input type="checkbox" id="tag_${status.index}" name="tagid_list" value="${tag.id}" <c:if test="${tag.checked}">checked</c:if>>${tag.name}
                    </label>
                </c:forEach>
            </td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                    $('#overlay').show();
                    var thisButton = $(this);
                    thisButton.linkbutton('disable');
                    thisButton.linkbutton({text:'加载中...'});
                    var form = $('#wx_user_info_edit_form');
                    form.submit();
                    }">更新</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                    onClick: function(){
                    location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/index';
                    }">返回</a>
            </td>
        </tr>
        </tbody>
    </table>

</form:form>