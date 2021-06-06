<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    <base target="body">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {font-size: 10pt;}
	a {
		text-decoration:none;
		font-family:"华文楷体";
		color:9f5751;
		font-size:17px;
		font-weight:600;
	} 
	a:hover {
		text-decoration: underline;
		color:black;
		
	}
</style>
  </head>
  
  <body style="background:#f0f0f0;color: black;">
<h1 style="text-align: center; line-height: 30px;font-family:'华文中宋';">山 中 事 后 台 管 理</h1>
<div style="line-height: 30px;">
	<span style="font-family:'华文楷体'; font-size:18px;color:#244a60;font-weight:800;">管理员：${sessionScope.admin.adminname }</span>
	<a target="_top" href="<c:url value='/adminjsps/login.jsp'/>">退出</a>
	<span style="padding-left:80px;">
		<a href="<c:url value='/admin/AdminCategoryServlet?method=findAll'/>">分类管理</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/adminjsps/admin/book/main.jsp'/>">图书管理</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/admin/AdminOrderServlet?method=findAll'/>">订单管理</a>
	</span>
</div>
  </body>
</html>
