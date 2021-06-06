<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>信息板</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<style type="text/css">
	body {
		font-size: 10pt;
		color: #404040;
		font-family: SimSun;
		background-color:#4F5D50;
	}
	
	.divBody {
	background-color:white;
		width:800px;
		margin:200px auto;
	}
	
	.divTitle {
		text-align:left;
		width: 860px;
		height: 50px;
		line-height: 35px;
		background-color: #D1BF83;
		border: 5px solid #D1BF83;
		padding-top:15px;
		padding-left:30px;
		mergin-left:30px;
	}
	.divContent {
	background-color:white;
		width: 890px;
		height: 300px;
		border: 5px solid #D1BF83;
		
	}
	.spanTitle {
		color: #333;
  		font-weight: 700;
  		font-size: 30px;
  		font-family:"Times New Roman";
	}
	#divExpress{
		margin:70px 80px;
	}
	#divImg{
		float:left;
		margin-left:40px;
		margin-right:160px;
	}
	#spanMessage{
	text-align:center;
		font-family:STZhongsong;
		font-size: 32px; 
		color: #4f5d50;
		font-weight: 900;
		letter-spacing:10px;
		border-bottom:3px solid #4f5d50;
		}
	.spanButton{
	margin-top:20px;
		margin-left:58px;
		font-family:"华文楷体";
		font-weight:bold;
		font-size:22px;
	}
a {text-decoration: none;color:#A18c3F}
a:visited {color: #018BD3;}
a:hover {color:#FF6600; text-decoration: underline;}
}
</style>

  </head>
  
  <body>
  <c:choose>
  	<c:when test="${code eq 'success' }"><%--如果code是成功，它显示对号图片 --%>
  		<c:set var="img" value="/images/duihao.jpg"/>
  		<c:set var="title" value="S U C C E S S"/>
  	</c:when>
  	<c:when test="${code eq 'error' }"><%--如果code是成功，它显示错号图片 --%>
  		<c:set var="img" value="/images/cuohao.png"/>
  		<c:set var="title" value="F A I L"/>
  	</c:when>
  	
  </c:choose>
<div class="divBody">
	<div class="divTitle">
		<span class="spanTitle">${title }</span>
	</div>
	<div class="divContent">
	  <div id= "divExpress">
		<img id="divImg" src="<c:url value='${img }'/>" width="150"/>
		<span id="spanMessage">${msg }</span>
		<br/>
		<br/>
		<span class="spanButton"><a target="_top" href="<c:url value='/jsps/user/login.jsp'/>">登录</a></span>
		<span class="spanButton"><a target="_top" href="<c:url value='/index.jsp'/>">主页</a></span>
	  </div>
	</div>
</div>


  </body>
</html>