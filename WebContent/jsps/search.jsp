<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <title>按图名查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {
		margin-top: 13px;
		margin-bottom: 0px;
		margin-left:350px;
		color: #404040;
	}
	div{
	width:700px;
	height:60px;
	position:raletive;
	}
	input {
		height: 46px;
  font-size: 20px;
  font-family: "华文楷体";
  line-height: 20px;
 text-indent:10px;
  border: 2px solid #de907b;
  box-sizing:border-box;
  border-radius: 12px;
  color: #888;
  width: 600px;
	}
	input:hover{
	border: 2px solid #de907b;
	border-radius: 12px;
	box-shadow: 0px 1px 4px 0px #de907b;
  	
	}
	input:focus{
	outline:none;
	}
	button{
	height: 46px;
	 box-sizing:border-box;
	background-color:#de907b;
	border-radius:0 12px 12px 0;
	border:none;
	color:white;
	mergin-bottom:0;
	margin-left:-5px;;
	width:70px;
	position:absolute;
	left:885px;
	cursor:pointer;
	font-size:16px;
	}
	button:focus{
	outline:none;
	}
	a {
		text-transform:none;
		text-decoration:none;
		border-width: 0px;
		color:#92978c;
		font-size:15px;
		margin-left:5px;
	} 
	a:hover {
		text-decoration:underline;
		border-width: 0px;
	}
	span {
		margin: 0px;
	}
	img{
	height:60px;
	}
</style>
  </head>
  
  <body>
 
  
    <form action="<c:url value='/BookServlet'/>" method="post" target="body" id="form1">
    	<input type="hidden" name="method" value="findByBname"/>
    	<div>
    	<input type="text" name="bname"/>
    	
    	<button type=submit>搜 索</button>
    		
    		<a href="<c:url value='/jsps/gj.jsp'/>"  target="body">高级搜索</a>
    		</div>
    </form>
   
  </body>
</html>