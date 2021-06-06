<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书管理员列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/admin/css/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>">
  </head>
  
  <body>
    <h2 style="text-align: center;">图书列表</h2>
    <table align="center" border="1" cellpadding="0" cellspacing="0">
    	<caption class="captionAddOneLevel">
    	  <a href="<c:url value='/managerjsps/admin/add.jsp'/>">添加管理员</a>
    	</caption>
    	<tr class="trTitle">
    		<th>管理员姓名</th>
    		<th>密码</th>
    		<th>操作</th>
    	</tr>
    	
<c:forEach items="${admins }" var="admin">    	
    	<tr class="trOneLevel">
    		<td width="200px;">${admin.adminname }</td>
    		<td>${admin.adminpwd }</td>
    		<td width="200px;">
    		  <a href="<c:url value='/ManagerServlet?method=editPre&adminId=${admin.adminId }'/>">修改</a>
    		  <a onclick="return confirm('您是否真要删除该管理员？')" href="<c:url value='/ManagerServlet?method=delete&adminId=${admin.adminId }'/>">删除</a>
    		</td>
    	</tr>
</c:forEach>


    </table>
  </body>
</html>
