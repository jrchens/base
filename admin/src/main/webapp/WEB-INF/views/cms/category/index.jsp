<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>

<div class="easyui-panel" data-options="cls: 'ext-panel-float-left',width:200,minHeight:525">
    <ul id="cms_category_index_tree" class="easyui-tree" style="padding: 5px;"
        data-options="
        url:'${WEB_ROOT_CONTEXT}/cms/category/async-tree-query',
        method:'get',
        loadFilter: function(data,parent){
            if(data.success){
                return data.data;
            }
            return false;
        },
        onClick: function(node){
            // $(this).tree('toggle', node.target);
            $('#cms_category_index_datagrid').datagrid('reload',{parentId: node.id});

            var form = $('#cms_category_index_save_form');
            form.form('clear');

            $('#parentId',form).val(node.id);
            $('#link',form).val(0);

            var thisButton = $('#cms_category_index_save_form_save_button');
            thisButton.linkbutton({text:'保存'});
            thisButton.linkbutton('enable');
        }
        <%--,queryParams:{parentId:0}--%>
        ">
    </ul>
    <%--<script type="text/javascript">--%>
        <%--setTimeout(function () {--%>
            <%--var tree = $('#cms_category_index_tree');--%>
            <%--var rootNode = tree.tree('find',1);--%>
            <%--tree.tree('select',rootNode.target);--%>
        <%--},2000);--%>
    <%--</script>--%>
</div>

<table id="cms_category_index_datagrid" class="easyui-datagrid"
       data-options="title: '类别管理-列表',
            url: '${WEB_ROOT_CONTEXT}/cms/category/async-query',
            method: 'get',
            queryParams: {parentId:-1},
            sortName: 'sort',
            sortOrder: 'asc',
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            minHeight: 350,
            minWidth: 520,
            striped: true,
            pageSize: 10,
            cache: false,
            cls: 'ext-datagrid-float-left',
            <%--toolbar: '#cms_category_index_query_form',--%>
            <%--onDblClickRow: function(index,row){--%>
            onBeforeLoad:function(param){
                if(param.parentId == -1){
                    return false;
                }
            },
            onClickRow: function(index,row){
                var thisButton = $('#cms_category_index_save_form_save_button');
                $('#overlay').show();
                thisButton.linkbutton('disable');
                var url = '${WEB_ROOT_CONTEXT}/cms/category/async-detail';
                var reqData = {id:row.id};
                $.get(url,reqData,function(data,textStatus,jqXHR){
                    if(data.success){
                        var form = $('#cms_category_index_save_form');
                        form.form('load',data.data);

                        if(data.data.link){
                            $('#cms_category_index_save_form_link_switchbutton').switchbutton('check');
                        }else{
                            $('#cms_category_index_save_form_link_switchbutton').switchbutton('uncheck');
                        }

                    }else{
                        $.messager.show({msg:data.message});
                    }
                },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                    $('#overlay').hide();
                    thisButton.linkbutton('enable');
                    thisButton.linkbutton({text:'更新'});
                });
            },
            loadFilter: function(data){
                if(!data.success){
                    $.messager.show({msg:data.message});
                }
                var pager = $('#cms_category_index_datagrid').datagrid('getPager');
                pager.pagination({
                    buttons:[
                        {   iconCls:'ext-icon fa fa-trash',
                            handler:function(){
                                var thisButton = $(this);
                                var row = $('#cms_category_index_datagrid').datagrid('getSelected');
                                if(row == null){
                                    $.messager.alert('提示', '请先选择一行记录!', 'warning');
                                    return false;
                                }

                                $.messager.confirm('确认', '确认删除记录吗?', function(r) {
                                    if (r) {
                                        // var index = $('#sys_article_datagrid').datagrid('getRowIndex', row);
                                        // $('#sys_article_datagrid').datagrid('deleteRow', index);

                                        $('#overlay').show();
                                        thisButton.linkbutton('disable');

                                        var reqData = {id:row.id};
                                        $.post('${WEB_ROOT_CONTEXT}/cms/category/async-remove',reqData,function(data,textStatus,jqXHR){
                                            if(data.success){
                                                var tree = $('#cms_category_index_tree');
                                                var node = tree.tree('getSelected');
                                                var id = 1;
                                                if(node != null){
                                                    id = node.id;
                                                }
                                                tree.tree('reload',node.target);
                                                // TODO remove tree node
                                                $('#cms_category_index_datagrid').datagrid('reload',{parentId:id});
                                            }else{
                                                $.messager.show({msg:data.message});
                                            }
                                        },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                            $('#overlay').hide();
                                            thisButton.linkbutton('enable');
                                        });
                                    }
                                });
                            }
                        }
                    ]
                });

                return data.data;
            },
       ">
    <thead>
    <tr>
        <th data-options="field:'category'">类别名</th>
        <th data-options="field:'categoryName'">显示名</th>
        <th data-options="field:'sort'">排序</th>
    </tr>
    </thead>
</table>

<div class="easyui-panel" data-options="cls: 'ext-panel-float-left',minWidth:520, minHeight:150" style="margin-top: 10px;">
    <form:form id="cms_category_index_save_form" method="post"
               modelAttribute="category" cssStyle="padding: 5px; margin: 0px;"
               data-options="inline: true" action="${WEB_ROOT_CONTEXT}/cms/category/async-save">
        <form:hidden path="id"/>
        <form:hidden path="parentId"/>
        <form:hidden path="link"/>
        <table class="ext-data-table" style="width: 100%" cellspacing="0" cellpadding="0">
            <tbody>
            <tr>
                <td>是否链接</td>
                <td>
                    <span id="cms_category_index_save_form_link_switchbutton" class="easyui-switchbutton" data-options="onChange:function(checked){
                            var form = $('#cms_category_index_save_form');
                            $('#link',form).val(checked);
                        }"></span><form:errors path="link"/>
                </td>
            </tr>
            <tr>
                <td>类别名</td>
                <td><form:input path="category" cssClass="easyui-textbox" data-options="fit:false,width:200"></form:input></td>
            </tr>
            <tr>
                <td>显示名</td>
                <td><form:input path="categoryName" cssClass="easyui-textbox" data-options="fit:false,width:200"></form:input></td>
            </tr>
            <tr>
                <td>排序</td>
                <td><form:input path="sort" cssClass="easyui-numberbox" data-options="fit:false,width:200,precision:0"></form:input></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td style="text-align: left"><a href="javascript:;" class="easyui-linkbutton" data-options="
                        id: 'cms_category_index_save_form_save_button',
                        disabled: true,
                        width: 80,
                        iconCls:'ext-icon fa fa-floppy-o',
                        onClick: function(){
                            var tree = $('#cms_category_index_tree');
                            var node = tree.tree('getSelected');

                            if(node == null){
                                $.messager.alert('提示', '请先选择节点记录!', 'warning');
                                return false;
                            }

                            var reqData = $('#cms_category_index_save_form').serializeJSON();
                            var url = '${WEB_ROOT_CONTEXT}/cms/category/async-save';
                            if(reqData.id > 0){
                                url = '${WEB_ROOT_CONTEXT}/cms/category/async-update';
                            } else {
                                reqData.parentId = node.id;
                            }

                            var thisButton = $(this);
                            $('#overlay').show();
                            thisButton.linkbutton('disable');
                            $.post(url,reqData,function(data,textStatus,jqXHR){
                                if(data.success){
                                    var form = $('#cms_category_index_save_form');
                                    form.form('clear');

                                    if(!node.children || node.children.length == 0){
                                        tree.tree('reload');
                                    }else{
                                        tree.tree('reload',node.target);
                                    }
                                } else {
                                    $.messager.show({msg:data.message});
                                }
                            },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                $('#overlay').hide();
                                thisButton.linkbutton('enable');
                            });

                            $('#cms_category_index_datagrid').datagrid('reload',{parentId:reqData.parentId});
                        }">保存</a></td>
            </tr>
            </tbody>
        </table>
    </form:form>
</div>


<div class="ext-warning" style="margin-top: 535px;">
    1、点击左侧树节点，新增子类别。
    <br>
    2、点击类别管理-列表记录，下方更新类别。
</div>