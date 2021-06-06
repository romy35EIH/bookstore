<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<html>
  <head>
    
    <title>main</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/main.css'/>">
	<!--  <script type="text/javascript">
	function setIframeHeight(hei){
	    document.getElementById("body").style.height = 1200 ;
	}
	</script>
-->

	
  </head>
  
  <body>
 
<table class="table" align="center">
	<tr class="trTop">
		<td colspan="2" class="tdTop">
			<iframe id="top" frameborder="0" src="<c:url value='/jsps/top.jsp' />" name="top" ></iframe>
		</td>
	</tr>
	<tr>
		
		<td colspan="2" class="tdSearch" style="border-bottom-width: 0px;">
			<iframe id="search" frameborder="0" src="<c:url value='/jsps/search.jsp'/>" name="search"></iframe>
		</td>
	</tr>
	<tr>
	<td class="tdLeft" >
			<iframe frameborder="0" src="<c:url value='/CategoryServlet?method=findAll'/>" name="left"></iframe>
		</td>
		<td style="border-top-width: 0px;">
			<iframe id="body" frameborder="0" src="<c:url value='/BookServlet?method=rank'/>" name="body" ></iframe>
		</td>
	</tr>
</table>
  </body>

</html>

