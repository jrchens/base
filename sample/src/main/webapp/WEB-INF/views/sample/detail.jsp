<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<form:form id="sample_detail_form" method="post"
           modelAttribute="sample" cssStyle="padding: 5px; margin: 0px;"
           cssClass="easyui-panel" title="样例管理-详情"
           data-options="inline: true" action="http://local.com/sample/remove">
    <form:hidden path="id"/>
    <form:hidden path="btinyint"/>

    <table class="ext-data-table">
        <tbody>
        <tr>
            <td>代码</td>
            <td><form:input path="bcode" cssClass="easyui-textbox" data-options="required:true,fit:true,readonly:true"/><form:errors
                    path="bcode"/></td>
            <td>标题</td>
            <td><form:input path="btitle" cssClass="easyui-textbox"
                            data-options="required:true,fit:true,readonly:true"/><form:errors path="btitle"/></td>
        </tr>
        <tr>
            <td>整数</td>
            <td><form:input path="bint" cssClass="easyui-numberbox"
                            data-options="required:true,fit:true,min:0,readonly:true"/><form:errors path="bint"/></td>
            <td>小数</td>
            <td><form:input path="bnum" cssClass="easyui-numberbox"
                            data-options="required:true,fit:true,min:0,precision:2,readonly:true"/><form:errors
                    path="bnum"/></td>
        </tr>

        <tr>
            <td>日期</td>
            <td><form:input path="bdate" cssClass="easyui-datebox"
                            data-options="required:true,editable:false,readonly:true"/><form:errors path="bdate"/>
                <span style="margin-left: 24px; margin-right: 24px;">Datetime</span>
                <form:input path="bdatetime" cssClass="easyui-datetimebox"
                            data-options="required:true,editable:false,width:160,readonly:true"/><form:errors
                        path="bdatetime"/>
            </td>
            <td>布尔值</td>
            <td>
                    <%--<form:input path="btinyint" cssClass="easyui-switchbutton"/>--%>
                <span class="easyui-switchbutton"
                      data-options="readonly:true,checked:${empty sample.btinyint ? false : sample.btinyint},onChange:function(checked){
                            var form = $('#sample_detail_form');
                            $('#btinyint',form).val(checked);
                        }"></span><form:errors path="btinyint"/>
            </td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-pencil ', width: 80,
                    onClick: function(){
                        $('#overlay').show();
                        $(this).linkbutton('disable');
                        $(this).linkbutton({text:'加载中...'});
                        location.href = 'http://local.com/sample/edit?id=${sample.id}';
                    }">编辑</a>
                <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls: 'ext-icon fa fa-trash ', width: 80,
                    onClick: function(){
                        var thisButton = $(this);

                        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                            if (r) {
                                // var index = $('#sys_group_datagrid').datagrid('getRowIndex', row);
                                // $('#sys_group_datagrid').datagrid('deleteRow', index);

                                $('#overlay').show();
                                thisButton.linkbutton('disable')
                                thisButton.linkbutton({text:'加载中...'});

                                var reqData = {id:${sample.id}};
                                $.post('http://local.com/sample/remove',reqData,function(data,textStatus,jqXHR){
                                    if(data.success){
                                        location.href = 'http://local.com/sample/index';
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
                    location.href = 'http://local.com/sample/index';
                    }">返回</a>
            </td>
        </tr>

        </tbody>
    </table>

</form:form>