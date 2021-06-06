<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <title>top</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {
		margin: 0px;
	}
	a {
		text-decoration:none;
		font-family:"华文楷体";
		color:#2e4131;
	} 
	a:hover {
		text-decoration: underline;
		color:black;
		
	}
	.topnav{
	float:right;
	}
	#div1{
	height:250px;
	padding:10px;
	margin-bottom:5px;
	background-color: #4f5d50;
	text-align:center;
	box-shadow: 8px 1px 2px 0px #de907b;
	}
	
	#div2{
	font-size: 18px; 
	line-height: 23px;
	border-bottom:3px solid #f0f0f0;
	padding:5px;
	background-color:#f9f9f9;
	color:#c5c6c2;
	}
	img{
	height:200px;
	padding:25px;
	border-radius:12px;
	}
	span{
	font-family:"华文楷体";
		color:#2e4131;
	}
	.logo{
	position:relative;
	
	top:20px;;
	width:250px;
	border-radius:100px;
}
</style>
  </head>
  
  <body>
<div id = "div1"><img src="/mystore/images/storename00.jpg"/></div>
 
<div id = "div2">

<%-- 根据用户是否登录，显示不同的链接 --%>
<c:choose>
	<c:when test="${empty sessionScope.sessionUser }">
		  <a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">会员登录</a> |&nbsp; 
		  <a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">注册会员</a>	
	</c:when>
	<c:otherwise>
		     <span> 欢迎你！山中人：${sessionScope.sessionUser.loginname }&nbsp;&nbsp;|&nbsp;&nbsp;</span>
		     <div class="topnav">
		  <a href="<c:url value='/CartItemServlet?method=myCart'/>" target="body">我的购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/OrderServlet?method=myOrders'/>" target="body">我的订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">退出</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="http://www.itcast.cn/channel/contact.shtml" target="_top">联系我们</a>
		  </div>
	</c:otherwise>
</c:choose>



</div>
  </body>
</html>