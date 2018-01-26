<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../views/common/taglib.jsp" %>
<div style="float: left;margin: 0; padding: 0; border:0; display: inline">
    <span style="margin-right: 12px; float: left; font-size:14px; ">系统名称</span>
</div>
<div style="float: right;margin: 0; padding: 0; border:0; display: inline">

    <span style="margin-right: 12px;"><a
            href="${WEB_ROOT_CONTEXT}/admin/user?username=<shiro:principal />"><shiro:principal/></a></span>

    <span style="margin-right: 12px;"><a
            href="${WEB_ROOT_CONTEXT}/attachment/index">附件管理</a></span>
    <span style="margin-right: 12px;"><a href="${WEB_ROOT_CONTEXT}/logout">退出</a></span>
</div>