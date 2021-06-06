<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册页面</title>

	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/regist.css'/>">

	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/user/regist.js'/>"></script>
</head>
<body>
	<div id="divOuter">

		<div id="divTitle">
			<span id="spanTitle">R E G I S T</span>
		</div>
		
		<div id="divCenter">
		<form action="<c:url value='/UserServlet'/>" method="post" id="registForm">
		<input type="hidden" name="method" value="regist"/>
			<table id="tableForm">
				<tr id="">
					<td class="tdText">用户名：</td>
					<td class="tdInput">
					<input class = "inputClass" type="text" name="loginname" id="loginname" value="${form.loginname }"/>
					</td>
					<td class="tdError">
					<label class="errorClass" id="loginnameError">${errors.loginname}</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">登录密码：</td>
					<td class="tdInput">
					<input class = "inputClass" type="password" name="loginpass" id="loginpass" value="${form.loginpass }"/>
					</td>
					<td class="tdError">
					<label class="errorClass" id="loginpassError">${errors.loginpass}</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">确认密码：</td>
					<td class="tdInput">
					<input class = "inputClass" type="password" name="reloginpass" id="reloginpass" value="${form.reloginpass }"/>
					</td>
					<td class="tdError">
					<label class="errorClass" id="reloginpassError">${errors.reloginpass}</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">email：</td>
					<td class="tdInput">
					<input class = "inputClass" type="text" name="email" id="email" value="${form.email }"/>
					</td>
					<td class="tdError">
					<label class="errorClass" id="emailError">${errors.email}</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">验证码：</td>
					<td class="tdInput">
					<input class = "inputClass" type="text" name="verifyCode" id="verifyCode" value="${form.verifyCode }"/>
					</td>
					<td class="tdError">
					<label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
					<div id="divVerifyCode">
					<img id="imgVerifyCode" src= "<c:url value='/VerifyCodeServlet'/>" /></div>
					</td>
					<td class="tdError">
					<label><a href="javascript:_hyz()">换一张</a></label>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
					<input type="image" src="../../images/regist1.jpg" id="submitBtn"/>
					</td>
					<td class="tdError">
					<label></label>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
	</div>
</body>
</html>