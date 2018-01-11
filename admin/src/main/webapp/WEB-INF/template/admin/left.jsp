<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../views/common/taglib.jsp" %>
<div>
<span style="display: block"><a href="http://local.com/sample/index">样例</a></span>

<shiro:hasRole name="admin">
<span style="display: block"><a href="http://local.com/admin/user/index">用户管理</a></span>
<span style="display: block"><a href="http://local.com/admin/group/index">群组管理</a></span>
<span style="display: block"><a href="http://local.com/admin/role/index">角色管理</a></span>
<span style="display: block"><a href="http://local.com/admin/permission/index">权限管理</a></span>
<span style="display: block"><a href="http://local.com/admin/config/index">配置管理</a></span>
</shiro:hasRole>

</div>