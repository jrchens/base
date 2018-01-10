<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<form:form id="sample_create_form" method="post"
           modelAttribute="sample" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="样例管理-新增"
           data-options="inline: true" action="http://local.com/sample/save">
    <form:hidden path="btinyint"/>

    <table class="ext-data-table">
        <tbody>

        <tr>
            <td>代码</td>
            <td><form:input path="bcode" cssClass="easyui-textbox" data-options="required:true,fit:true"/><form:errors
                    path="bcode"/></td>
            <td>标题</td>
            <td><form:input path="btitle" cssClass="easyui-textbox" data-options="required:true,fit:true"/><form:errors
                    path="btitle"/></td>
        </tr>

        <tr>
            <td>整数</td>
            <td><form:input path="bint" cssClass="easyui-numberbox"
                            data-options="required:true,fit:true,min:0"/><form:errors path="bint"/></td>
            <td>小数</td>
            <td><form:input path="bnum" cssClass="easyui-numberbox"
                            data-options="required:true,fit:true,min:0,precision:2"/><form:errors path="bnum"/></td>
        </tr>

        <tr>
            <td>日期</td>
            <td><form:input path="bdate" cssClass="easyui-datebox"
                            data-options="required:true,editable:false"/><form:errors path="bdate"/>
                <span style="margin-left: 24px; margin-right: 24px;">Datetime</span>
                <form:input path="bdatetime" cssClass="easyui-datetimebox"
                            data-options="required:true,editable:false,width:160"/><form:errors path="bdatetime"/>
            </td>
            <td>布尔值</td>
            <td>
                    <%--<form:input path="btinyint" cssClass="easyui-switchbutton" data-options=""/>--%>
                    <%--onText:'Yes',offText:'No',reversed:true--%>
                <span class="easyui-switchbutton" data-options="onChange:function(checked){
                            var form = $('#sample_create_form');
                            $('#btinyint',form).val(checked);
                        }"></span><form:errors path="btinyint"/>
            </td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-floppy-o ', width: 80,
                        onClick: function(){
                        $('#overlay').show();
                        var thisButton = $(this);
                        thisButton.linkbutton('disable');
                        thisButton.linkbutton({text:'加载中...'});
                        var form = $('#sample_create_form');
                        form.submit();
                        }">保存</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-arrow-left ', width: 80,
                        onClick: function(){
                        location.href = 'http://local.com/sample/index';
                        }">返回</a>

            </td>
        </tr>
        </tbody>
    </table>

</form:form>