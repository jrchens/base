<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../views/common/taglib.jsp" %>
<div>
    <%--<span style="display: block"><a href="http://local.com/sample/index">样例</a></span>--%>

    <%--<shiro:hasRole name="admin">--%>
        <%--<span style="display: block"><a href="http://local.com/admin/user/index">用户管理</a></span>--%>
        <%--<span style="display: block"><a href="http://local.com/admin/group/index">群组管理</a></span>--%>
        <%--<span style="display: block"><a href="http://local.com/admin/role/index">角色管理</a></span>--%>
        <%--<span style="display: block"><a href="http://local.com/admin/permission/index">权限管理</a></span>--%>
        <%--<span style="display: block"><a href="http://local.com/admin/config/index">配置管理</a></span>--%>
    <%--</shiro:hasRole>--%>

    <%--<span style="display: block"><a href="http://local.com/cms/category/index">类别管理</a></span>--%>
    <%--<span style="display: block"><a href="http://local.com/cms/article/index">文章管理</a></span>--%>

    <script type="text/javascript">
        var admin_left_tree_data = [
            <shiro:hasRole name="admin">
            {
                text:'系统管理',
                children:[
                    {text:'用户管理',attributes:{url:'http://local.com/admin/user/index'}},
                    {text:'群组管理',attributes:{url:'http://local.com/admin/group/index'}},
                    {text:'角色管理',attributes:{url:'http://local.com/admin/role/index'}},
                    {text:'权限管理',attributes:{url:'http://local.com/admin/permission/index'}},
                    {text:'配置管理',attributes:{url:'http://local.com/admin/config/index'}},
                ],
            },
            </shiro:hasRole>
            {
                text:'内容管理',
                children:[
                    {text:'类别管理',attributes:{url:'http://local.com/cms/category/index'}},
                    {text:'文章管理',attributes:{url:'http://local.com/cms/article/index'}},
                ],
            },
        ];
    </script>


        <ul id="cms_category_index_tree" class="easyui-tree"
            data-options="
                data:admin_left_tree_data,
                onClick:function(node){
                    location.href = node.attributes.url;
                }
            ">
        </ul>

</div>

<%--id: node id, which is important to load remote data--%>
<%--text: node text to show--%>
<%--state: node state, 'open' or 'closed', default is 'open'. When set to 'closed', the node have children nodes and will load them from remote site--%>
<%--checked: Indicate whether the node is checked selected.--%>
<%--attributes: custom attributes can be added to a node--%>
<%--children: an array nodes defines some children nodes--%>
