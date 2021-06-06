<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加分类</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/category/add.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#cname").val()) {
				alert("分类名不能为空！");
				return false;
			}
			if(!$("#pid").val()) {
				alert("一级分类不能为空！");
				return false;
			}
			if(!$("#desc").val()) {
				alert("分类描述不能为空！");
				return false;
			}
			return true;
		}
	</script>
<style type="text/css">
.select_style{
 
 padding-top:20px;
}
.select_style select{ 
  background: transparent;
   width: 220px;
   padding: 5px;
   font-size: 16px;
   border: 2px solid #ccc;
   height: 34px;
   
  
} 
select:focus{
outline:none;
}

</style>
  </head>
  
  <body>
  <div id="divmain">
    <h3>ADD LEVEL2</h3>
    <h1></h1>
    <p style="font-weight: 900; color: red">${msg }</p>
    <form action="<c:url value='/admin/AdminCategoryServlet'/>" method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="method" value='addChild'/>
    	分类名称：<input type="text" name="cname" id="cname"/><br/>
    	<div class="select_style">一级分类：  <select name="pid" id="pid">
    		<option value="">===选择1级分类===</option>
<c:forEach items="${parents }" var="parent">
    		<option value="${parent.cid }" <c:if test="${parent.cid eq pid }">selected="selected"</c:if> >${parent.cname }</option>
</c:forEach>
    	</select></div>
    	<br/>
    	<div>
    	分类描述：<textarea rows="5" cols="50" name="desc" id="desc"></textarea></div><br/>
    	<input class="btn" type="submit" value="添加二级分类"/>
    	<input class="btn" type="button" value="返回" onclick="history.go(-1)"/>
    </form>
    </div>
  </body>
</html>
