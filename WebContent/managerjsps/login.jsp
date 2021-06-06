<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>书店经理登录页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#managername").val()) {
				alert("经理名称不能为空！");
				return false;
			}
			if(!$("#managerpwd").val()) {
				alert("经理密码不能为空！");
				return false;
			}
			return true;
		}
	</script>
  </head>
  
  <body>
<h1>山主登录页面</h1>
<hr/>
  <p style="font-weight: 900; color: red">${msg }</p>
<form action="<c:url value='/ManagerServlet'/>" method="post" onsubmit="return checkForm()" target="_top">
	<input type="hidden" name="method" value="login"/>
	山主账户：<input type="text" name="managername" value="" id="managername"/><br/>
	密　　码：<input type="password" name="managerpwd" id="managerpwd"/><br/>
	<input type="submit" value="进入后台"/>
</form>
  </body>
</html>
