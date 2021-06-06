<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>showitem.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/showitem.css'/>">
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>


<script type="text/javascript">
	//计算合计
	$(function() {
		var total = 0;
		$(".subtotal").each(function() {
			total += Number($(this).text());
		});
		$("#total").text(round(total, 2));
	});
</script>
  </head>
  
  <body>
  <div id="divmain">
  <c:choose>
  	<c:when test="${empty cartItemList }">嘻嘻~</c:when>
  	<c:otherwise>
<form id="form1" action="<c:url value='/OrderServlet'/>" method="post">
	<input type="hidden" name="cartItemIds" value="${cartItemIds }"/>
	<input type="hidden" name="method" value="createOrder"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr id="trtitle">
		<td width="400px" colspan="5"><span class="spancreate">生成订单</span></td>
	</tr>
	<tr align="center" class="trtop">
		<td width="10%">&nbsp;</td>
		<td width="50%">图书名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
	</tr>


<c:forEach items="${cartItemList }" var="cartItem">
	<tr align="center" class="trcontent">
		<td align="right">
			<a class="linkImage" href="<c:url value='/jsps/book/desc.jsp'/>"><img border="0" width="60" align="top" src="<c:url value='/${cartItem.book.image_b }'/>"/></a>
		</td>
		<td align="left" class="tdBookName">
			<a href="<c:url value='/jsps/book/desc.jsp'/>"><span>${cartItem.book.bname }</span></a>
		</td>
		<td class="tdBookPrice">&yen;${cartItem.book.currPrice }</td>
		<td>${cartItem.quantity }</td>
		<td>
			<span class="price_n">&yen;<span class="subtotal">${cartItem.subtotal }</span></span>
		</td>
	</tr>
</c:forEach>
	
	
	







	<tr>
		<td colspan="6" align="right" id="tdtotal">
			<span>总计：</span><span class="price_t">&yen;<span id="total">${total }</span></span>
		</td>
	</tr>
	<tr>
		<td id="tdiddr" colspan="6"><span class="spancreate" >收货地址</span></td>
	</tr>
	<tr>
		<td colspan="6">
			<input id="addr" type="text" name="address"/><br/>
		</td>
	</tr>
	
	<tr>
		<td  colspan="5" align="right">
			<a id="linkSubmit" href="javascript:$('#form1').submit();">提交订单</a>
		</td>
	</tr>
</table>
</form>
  	</c:otherwise>
  </c:choose>
  </div>
  </body>
</html>
