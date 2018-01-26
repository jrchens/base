<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>
<form:form id="wx_tag_create_form" method="post"
           modelAttribute="wxTag" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="标签管理-新增"
           data-options="inline: true" action="${WEB_ROOT_CONTEXT}/wx/tag/save">
    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>标签名</td>
            <td><form:input path="name" cssClass="easyui-textbox"
                            data-options="required:true,fit:true"/><form:errors
                    path="name"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-floppy-o ', width: 80,
                        onClick: function(){
                        $('#overlay').show();
                        var thisButton = $(this);
                        thisButton.linkbutton('disable');
                        thisButton.linkbutton({text:'加载中...'});
                        var form = $('#wx_tag_create_form');
                        form.submit();
                        }">保存</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                        onClick: function(){
                        location.href = '${WEB_ROOT_CONTEXT}/wx/tag/index';
                        }">返回</a>

            </td>
        </tr>
        </tbody>
    </table>

</form:form>