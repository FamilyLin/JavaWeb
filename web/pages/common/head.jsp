<%--
  Created by IntelliJ IDEA.
  User: Family_Lin
  Date: 2020/8/1
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String basePath = request.getScheme()
            + "://"
            + request.getServerName()
            + ":"
            + request.getServerPort()
            + request.getContextPath()
            + "/" ;
    pageContext.setAttribute("basePath",basePath);

%>

<!--写base标签，永远固定相对路径跳转的结果-->
<base href="http://localhost:8080/book/">
<%--	css格式--%>
<link type="text/css" rel="stylesheet" href="static/css/style.css">
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
