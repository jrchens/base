<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../views/common/taglib.jsp" %>
<div>
<span style="display: block"><a href="http://local.com/sample/index">样例</a></span>

<shiro:hasRole name="admin">
<span style="display: block"><a href="http://local.com/admin/user">用户管理</a></span>
<span style="display: block"><a href="http://local.com/admin/group">群组管理</a></span>
<span style="display: block"><a href="http://local.com/admin/role">角色管理</a></span>
<span style="display: block"><a href="http://local.com/admin/permission">权限管理</a></span>
<span style="display: block"><a href="http://local.com/admin/config">配置管理</a></span>
</shiro:hasRole>

</div>