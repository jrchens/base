<%--
  Created by IntelliJ IDEA.
  User: shengchen
  Date: 2018/1/4
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>

<script type="text/javascript">
    var current_node_id = 0;
</script>

<div class="easyui-panel" data-options="cls: 'ext-panel-float-left',width:200,minHeight:500">
    <ul id="wx_menu_index_tree" class="easyui-tree" style="padding: 5px;"
        data-options="
        url:'http://local.com/wx/menu/async-tree-query',
        method:'get',
        <%--,queryParams:{parentId:0}--%>
        loadFilter: function(data,parent){
            if(data.success){
                return data.data;
            }
            return false;
        },
        onLoadSuccess: function(node, data){
            if(current_node_id > 0){
                var node = $(this).tree('find', current_node_id);
                $(this).tree('select', node.target);
            }
        },
        onContextMenu: function(e,node){
            e.preventDefault();
            current_node_id = node.id;

            var tree = $(this);
            tree.tree('select',node.target);
            var isLeaf = tree.tree('isLeaf',node.target);
            if(node.id == 1){
                $('#wx_menu_index_tree_context_menu_create_button').css('display','block');
                $('#wx_menu_index_tree_context_menu_delete_button').css('display','none');
                $('#wx_menu_index_tree_context_menu_edit_button').css('display','none');
                $('#wx_menu_index_tree_context_menu_publish_button').css('display','none');
            }else if(node.parentId == 1){

                $('#wx_menu_index_tree_context_menu_create_button').css('display','block');
                $('#wx_menu_index_tree_context_menu_delete_button').css('display','block');
                $('#wx_menu_index_tree_context_menu_edit_button').css('display','block');
                $('#wx_menu_index_tree_context_menu_publish_button').css('display','block');

                if(!isLeaf){
                    $('#wx_menu_index_tree_context_menu_delete_button').css('display','none');
                }

            }else if(node.parentId > 1){
                $('#wx_menu_index_tree_context_menu_create_button').css('display','block');
                $('#wx_menu_index_tree_context_menu_delete_button').css('display','block');
                $('#wx_menu_index_tree_context_menu_edit_button').css('display','block');
                $('#wx_menu_index_tree_context_menu_publish_button').css('display','none');

                if(!isLeaf){
                    $('#wx_menu_index_tree_context_menu_delete_button').css('display','none');
                }
            }

            $('#wx_menu_index_tree_context_menu').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        },
        onDblClick: function(node){
            current_node_id = node.id;
            var reqData = {id:current_node_id};
            $('#overlay').show();
            $.get('http://local.com/wx/menu/async-detail',reqData,function(data,textStatus,jqXHR){
                if(data.success){
                    var form = $('#wx_menu_index_update_form');
                    form.form('load',data.data);
                    if(data.data.custom){
                        $('#wx_menu_index_update_form_custom_switchbutton',form).switchbutton('check');
                    }else {
                        $('#wx_menu_index_update_form_custom_switchbutton',form).switchbutton('uncheck');
                    }
                    $('#wx_menu_index_save_form_update_button').linkbutton('enable');
                }else{
                    $.messager.show({msg:data.message});
                }
            },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                $('#overlay').hide();
            });
        }
        ">
    </ul>
</div>

<div id="wx_menu_index_tree_context_menu" class="easyui-menu" style="width:120px;">
    <div id="wx_menu_index_tree_context_menu_create_button" onclick="{

        var reqData = {parent_id:current_node_id,node_name:'新菜单'};
        $('#overlay').show();
        $.post('http://local.com/wx/menu/async-save',reqData,function(data,textStatus,jqXHR){
            if(data.success){
                $('#wx_menu_index_tree').tree('reload');
            }else{
                $.messager.show({msg:data.message});
            }
        },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
            $('#overlay').hide();
        });

    }" data-options="iconCls:'ext-icon fa fa-plus'">新增</div>

    <div id="wx_menu_index_tree_context_menu_delete_button" style="display: block" onclick="{

        $.messager.confirm('确认', '确认删除记录吗?', function(r) {
            if (r) {
                var reqData = {id:current_node_id};
                var tree = $('#wx_menu_index_tree');
                current_node_id = tree.tree('find',current_node_id).parentId;

                $('#overlay').show();
                $.post('http://local.com/wx/menu/async-delete',reqData,function(data,textStatus,jqXHR){
                    if(data.success){
                        tree.tree('reload');
                    }else{
                        $.messager.show({msg:data.message});
                    }
                },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                    $('#overlay').hide();
                });
            }
        });

    }" data-options="iconCls:'ext-icon fa fa-trash'">删除</div>


    <div id="wx_menu_index_tree_context_menu_edit_button" style="display: none" onclick="{

                var reqData = {id:current_node_id};
                $('#overlay').show();
                $.get('http://local.com/wx/menu/async-detail',reqData,function(data,textStatus,jqXHR){
                    if(data.success){
                        var form = $('#wx_menu_index_update_form');
                        form.form('load',data.data);
                        if(data.data.custom){
                            $('#wx_menu_index_update_form_custom_switchbutton',form).switchbutton('check');
                        }else {
                            $('#wx_menu_index_update_form_custom_switchbutton',form).switchbutton('uncheck');
                        }
                    }else{
                        $.messager.show({msg:data.message});
                    }
                },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                    $('#overlay').hide();
                });

    }" data-options="iconCls:'ext-icon fa fa-pencil'">编辑</div>

    <div id="wx_menu_index_tree_context_menu_publish_button" style="display: none" onclick="{
        var reqData = {id:current_node_id};
        $('#overlay').show();
        $.post('http://local.com/wx/menu/async-publish',reqData,function(data,textStatus,jqXHR){
            if(data.success){

            }else{
                $.messager.show({msg:data.message});
            }
        },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
            $('#overlay').hide();
        });
    }" data-options="iconCls:'ext-icon fa fa-cloud-upload'">发布</div>

</div>

<div class="easyui-panel" data-options="title:'菜单信息',cls: 'ext-panel-float-left',width:620, minHeight:500">
    <form id="wx_menu_index_update_form" method="post"
               style="padding: 5px; margin: 0px;"
               data-options="inline: true" action="http://local.com/wx/menu/async-update">
        <input type="hidden" id="id" name="id">
        <input type="hidden" id="parent_id" name="parent_id">
        <input type="hidden" id="node_media_id" name="node_media_id">
        <input type="hidden" id="node_appid" name="node_appid">
        <input type="hidden" id="node_pagepath" name="node_pagepath">
        <input type="hidden" id="menuid" name="menuid">
        <input type="hidden" id="custom" name="custom">

        <table class="ext-data-table" style="width: 610px" cellspacing="0" cellpadding="0">
            <tbody>

            <tr>
                <td>菜单类型</td>
                <td>
                    <select id="node_type" name="node_type" class="easyui-combobox" data-options="required:true, fit:true">
                        <option value="click">click</option>
                        <option value="view">view</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>菜单名称</td>
                <td><input id="node_name" name="node_name" class="easyui-textbox" data-options="required:true, fit:true"></td>
            </tr>
            <tr>
                <td>菜单键值(KEY)</td>
                <td><input id="node_key" name="node_key" class="easyui-textbox" data-options="required:false, fit:true"></td>
            </tr>
            <tr>
                <td>菜单链接</td>
                <td><input id="node_url" name="node_url" class="easyui-textbox" data-options="required:false, fit:true"></td>
            </tr>
            <tr>
                <td>菜单状态</td>
                <td><input id="url_state" name="url_state" class="easyui-textbox" data-options="required:false, fit:true"></td>
            </tr>
            <%--<tr>--%>
                <%--<td>永久素材(media_id)</td>--%>
                <%--<td><input id="node_media_id" name="node_media_id" class="easyui-textbox" data-options="required:false, fit:true"></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>小程序ID(appid)</td>--%>
                <%--<td><input id="node_appid" name="node_appid" class="easyui-textbox" data-options="required:false, fit:true"></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>小程序的页面路径</td>--%>
                <%--<td><input id="node_pagepath" name="node_pagepath" class="easyui-textbox" data-options="required:false, fit:true"></td>--%>
            <%--</tr>--%>
            <tr>
                <td>网页授权作用域(scope)</td>
                <td>
                    <select id="view_scope" name="view_scope" class="easyui-combobox" data-options="required:true, fit:true">
                        <option value="snsapi_base">snsapi_base</option>
                        <option value="snsapi_userinfo">snsapi_userinfo</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>个性化菜单</td>
                <td>
                    <span id="wx_menu_index_update_form_custom_switchbutton" class="easyui-switchbutton" data-options="onChange:function(checked){
                    var form = $('#wx_menu_index_update_form');
                    $('#custom',form).val(checked);
                    }"></span><form:errors path="custom"/>
                </td>
            </tr>
            <tr>
                <td>排序</td>
                <td><input id="sort" name="sort" class="easyui-numberbox" data-options="required:false, fit:true,min:0,max:100"></td>
            </tr>
            <%--<tr>--%>
                <%--<td>menuid</td>--%>
                <%--<td><input id="menuid" name="menuid" class="easyui-textbox" data-options="required:false, fit:true"></td>--%>
            <%--</tr>--%>
            <tr><td>备注</td>
                <td><input id="rich_content" name="rich_content" class="easyui-textbox" data-options="required:false, fit:true, multiline:true,height:80"></td>
            </tr>

            <%--<tr>--%>
                <%--<td>是否链接</td>--%>
                <%--<td>--%>
                    <%--<span id="wx_menu_index_save_form_link_switchbutton" class="easyui-switchbutton" data-options="onChange:function(checked){--%>
                            <%--var form = $('#wx_menu_index_save_form');--%>
                            <%--$('#link',form).val(checked);--%>
                        <%--}"></span><form:errors path="link"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>类别名</td>--%>
                <%--<td><form:input path="menu" cssClass="easyui-textbox" data-options="fit:false,width:200"></form:input></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>显示名</td>--%>
                <%--<td><form:input path="menuName" cssClass="easyui-textbox" data-options="fit:false,width:200"></form:input></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>排序</td>--%>
                <%--<td><form:input path="sort" cssClass="easyui-numberbox" data-options="fit:false,width:200,precision:0"></form:input></td>--%>
            <%--</tr>--%>
            <tr>
                <td colspan="2"><a href="javascript:;" class="easyui-linkbutton" id="wx_menu_index_save_form_update_button" data-options="
                        id: 'wx_menu_index_save_form_save_button',
                        width: 80,
                        disabled: true,
                        iconCls:'ext-icon fa fa-floppy-o',
                        onClick: function(){

                            var reqData = $('#wx_menu_index_update_form').serializeJSON();
                            var url = 'http://local.com/wx/menu/async-update';

                            var thisButton = $(this);
                            $('#overlay').show();
                            thisButton.linkbutton('disable');
                            $.post(url,reqData,function(data,textStatus,jqXHR){
                                if(data.success){
                                    var tree = $('#wx_menu_index_tree');
                                    var node = tree.tree('find',reqData.id);

                                    tree.tree('update',{
                                        target: node.target,
                                        text: reqData.node_name
                                    });

                                } else {
                                    $.messager.show({msg:data.message});
                                }
                            },'json').always(function(data_jqXHR, textStatus, jqXHR_errorThrown){
                                $('#overlay').hide();
                                thisButton.linkbutton('enable');
                            });

                        }">更新</a></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>


<%--<div class="ext-warning" style="margin-top: 535px;">--%>
    <%--1、点击左侧树节点，新增子类别。--%>
    <%--<br>--%>
    <%--2、点击类别管理-列表记录，下方更新类别。--%>
<%--</div>--%>