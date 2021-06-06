<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>管理员登录页面</title>
    
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
			if(!$("#adminname").val()) {
				alert("管理员名称不能为空！");
				return false;
			}
			if(!$("#adminpwd").val()) {
				alert("管理员密码不能为空！");
				return false;
			}
			return true;
		}
	</script>
	<style type="text/css">
	body {
		margin: 0px;
		background-color:#9bb8d3;
	}
	
	#divmain{
	width:600px;
	margin:200px auto;
	height:350px;
	background-color: #fefdfe;
	text-align:center;
	}
	
	.tdText{
	font-family: "华文楷体";
	font-size:19px;
	font-weight:bold;
	color:#524943;
	width:120px;
	text-align:center;
}
	.textinput 
{
height: 40px;
margin-left: 10px;
margin-top:10px;
  font-size: 20px;
  font-family: "华文楷体";
  line-height: 20px;
  padding: 10px;
  border: 2px solid #c0c0c0;
  background: #fff;
  border-radius: 12px;
  color: #888;
  width: 220px;
  text-indent:10px;
}
.textinput:hover{
	border: 2px solid #9bb8d3;
	border-radius: 12px;
  	box-shadow: 0px 1px 8px 0px #9bb8d3;
}
.textinput:focus{
	outline: none;
}

input[type="submit"]
{
	margin-top:20px;
	margin-left:10px;
	 border:0px;
	width: 160px; 
	height: 40px;
	padding:5px;
	color:white;
	background-color:#3a4a56;
	font-family:sans-serif;
	font-weight:550;
	border-radius: 6px;
	font-size:18px;
	}
	input[type="submit"]:hover
{
background-color:#94a3b4;
	}
input[type="submit"]:focus
{
outline:none;
	}
	table{
	padding-left:90px;
	}
	h1{
	color: #333;
  	font-weight: 700;
  	font-size: 27px;
  	font-family:"Times New Roman";
  	padding-top:20px;
	}
</style>
	
  </head>
  
  <body>
  <div id="divmain">
<h1>L O G I N</h1>
<hr/>
  <p style="font-weight: 900; color: red;height:20px;margin:5px auto;">${msg }</p>
<form action="<c:url value='/AdminServlet'/>" method="post" onsubmit="return checkForm()" target="_top">
	<input type="hidden" name="method" value="login"/>
	
	
	<table>
	<tr>
       <td class="tdText" >管理员名称:</td>
      <td><input class="textinput" type="text" name="adminname" value="" id="adminname"/></td>
       </tr>
                   
       <tr>
       <td class="tdText" >密　       码:</td>
       <td><input class="textinput" type="password" name="adminpwd" id="adminpwd"/></td>
       </tr>                  
	</table>
	<input type="submit" value="进入后台"/>
</form>
</div>
  </body>
</html>
