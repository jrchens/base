<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="wx_user_info_detail_form" method="post"
           modelAttribute="wxUserInfo" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="关注用户管理-详情"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/wx/user-info/async-remove">
    <form:hidden path="id"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>显示名</td>
            <td><form:input path="viewname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="viewname"/></td>
            <td>手机号</td>
            <td><form:input path="mobile" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="mobile"/></td>
        </tr>

        <tr>
            <td>微信昵称</td>
            <td><form:input path="nickname" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="nickname"/>
            </td>
            <td>性别</td>
            <td><form:select path="sex" cssClass="easyui-combobox" data-options="required:true,fit:true,readonly:true">
                <form:option value="0">未知</form:option>
                <form:option value="1">男</form:option>
                <form:option value="2">女</form:option>
            </form:select><form:errors path="sex"/>
            </td>
        </tr>

        <tr>
            <td>启用</td>
            <td><span class="easyui-switchbutton"
                      data-options="checked:${empty wxUserInfo.enable ? false : wxUserInfo.enable},onChange:function(checked){
                            var form = $('#wx_user_info_detail_form');
                            $('#enable',form).val(checked);
                        },readonly:true"></span><form:errors path="enable"/>
            </td>
            <td>标签</td>
            <td>
                <c:forEach var="tag" items="${wxTagList}" varStatus="status">
                    <c:if test="${tag.checked}">
                        ${tag.name}<c:if test="${not status.last}">, </c:if>
                    </c:if>
                </c:forEach>
            </td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/edit?id=${wxUserInfo.id}';
                    }">编辑</a>
                <%--<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,--%>
                    <%--onClick: function(){--%>
                        <%--var thisButton = $(this);--%>

                        <%--$.messager.confirm('确认', '确认删除记录吗?', function(r) {--%>
                            <%--if (r) {--%>

                                <%--$('#overlay').show();--%>
                                <%--thisButton.linkbutton('disable')--%>
                                <%--thisButton.linkbutton({text:'加载中...'});--%>

                                <%--var reqData = {id:'${wxUserInfo.id}'};--%>
                                <%--$.post('${WEB_ROOT_CONTEXT}/wx/user-info/async-remove',reqData,function(data,textStatus,jqXHR){--%>
                                    <%--if(data.success){--%>
                                        <%--location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/index';--%>
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
                    location.href = '${WEB_ROOT_CONTEXT}/wx/user-info/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>