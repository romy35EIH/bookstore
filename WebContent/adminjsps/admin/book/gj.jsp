<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>boo_gj.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body{
background-color:#9bb8d3;
}
	div{
		width:400px;
		height:400px;
		margin:40px auto;
		background-color:white;
		
	}
	table {
	color: #404040;
		
		
	}
	td{
	font-size:18px;
	font-family:"华文楷体";
	
	}
	tr{
	height:60px;
	padding:10px;
	}
	input[type="text"] 
{
height: 35px;

  font-size: 20px;
  font-family: "华文楷体";
  line-height: 20px;
  text-indent:10px;
  border: 2px solid #c0c0c0;
  background: #fff;
  border-radius: 12px;
  color: #888;
  width: 220px;
}
input :hover{
	border: 2px solid #D9C0BC;
	border-radius: 12px;
  	box-shadow: 0px 1px 8px 0px #D9C0BC;

}
input:focus{
	outline: none;
}
h2 {
    text-align: center;
    font-weight: 300;
    letter-spacing: 0.03em;
    padding-top: 30px;
    padding-bottom: 5px;
}
.btn 
{
	margin-top:10px;

	 border:0px;
	width: 70px; 
	height: 30px;
	padding:5px;
	color:white;
	background-color:#666565;
	font-family:sans-serif;
	font-weight:550;
	border-radius: 6px;
	font-size:13px;
	}
</style>
  </head>
  
  <body>
  <form action="<c:url value='/admin/AdminBookServlet'/>" method="get">
  	<input type="hidden" name="method" value="findByCombination"/>
<div>
  	<h2>Search</h2>
<table align="center">
	<tr>
		<td>书名：</td>
		<td><input type="text" name="bname"/></td>
	</tr>
	<tr>
		<td>作者：</td>
		<td><input type="text" name="author"/></td>
	</tr>
	<tr>
		<td>出版社：</td>
		<td><input type="text" name="press"/></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<input class="btn" type="submit" value="搜　　索"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input class="btn" type="reset" value="重新填写"/>
		</td>
	</tr>
</table>
</div>
	</form>
  </body>
</html>
