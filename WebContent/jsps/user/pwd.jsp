<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <title>pwd.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/pwd.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/user/pwd.js'/>"></script>
	<script src="<c:url value='/js/common.js'/>"></script>
  </head>
  
  <body><div class="main">
    <div class="div0">
    	<span class="pwdTop">M O D I F Y</span>
    </div>

	<div class="div1">
		<form action="<c:url value='/UserServlet'/>" method="post" target="_top">
			<input type="hidden" name="method" value="updatePassword"/>
		<table>
			<tr id="errorinfo">
				<td><label class="error">${msg }</label></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" class="tdtext">原密码:</td>
				<td class="tdInput"><input class="input" type="password" name="loginpass" id="loginpass" value="${user.loginpass }"/></td>
				<td class="errorClass"><label id="loginpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right" class="tdtext">新密码:</td>
				<td class="tdInput"><input class="input" type="password" name="newpass" id="newpass" value="${user.newpass }"/></td>
				<td class="errorClass"><label id="newpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right" class="tdtext">确认密码:</td>
				<td class="tdInput"><input class="input" type="password" name="reloginpass" id="reloginpass" value="${user.reloginpass }"/></td>
				<td class="errorClass"><label id="reloginpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"  class="tdtext">验证码:</td>
				<td>
				  <input class="input" type="text" name="verifyCode" id="verifyCode" value="${user.verifyCode }"/>
				</td>
				<td class="errorClass"><label id="verifyCodeError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
				  <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" border="1"/>
		    	 
				</td>
				<td> <a href="javascript:_change();" id="hyz">看不清，换一张</a></td>
			</tr>
			
			<tr>
				<td align="right"></td>
				<td><input id="submit" type="submit" value="修改密码"/></td>
				<td></td>
			</tr>
		</table>
		</form>
	</div>
	</div>
  </body>
</html>