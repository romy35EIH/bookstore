<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="">
    
    <title>body</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/body.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-3.5.1.min.js'/>"></script>
<script type="text/javascript">
$(function () {
  //获取焦点图的宽度（显示面积）
  var sWidth = $("#focus").width();
  //获取焦点图个数，从而确定过完全部图片的次数和透明框数量
  var len = $("#focus ul li").length;
  var index = 0;
  var picTimer;

  //添加透明按钮
  var bov = "<div class='box'></div><div class='bov'>";
  for (var i = 0; i < len; i++) {
    bov += "<span></span>";
  }
  //添加上一页、下一页两个按钮 
  bov += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
  $("#focus").append(bov);
  //添加半透明条
  $("#focus .box").css("opacity", 0.5);

  //为透明按钮添加鼠标滑入事件，显示对应的内容 
  $("#focus .bov span").css("opacity", 0.4).mouseenter(function () {
    index = $("#focus .bov span").index(this);
    showPics(index);
  }).eq(0).trigger("mouseenter");

  //上一页、下一页按钮透明度处理,鼠标滑入时改变颜色标识对应图片 
  $("#focus .preNext").css("opacity", 0.2).hover(function () {
    $(this).stop(true, false).animate({
      "opacity": "0.5"
    }, 300);
  }, function () {
    $(this).stop(true, false).animate({
      "opacity": "0.2"
    }, 300);
  });

  //上一页按钮 
  $("#focus .pre").click(function () {
    index -= 1;
    if (index == -1) {
      index = len - 1;
    }
    showPics(index);
  });

  //下一页按钮 
  $("#focus .next").click(function () {
    index += 1;
    if (index == len) {
      index = 0;
    }
    showPics(index);
  });

  //本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度 
  $("#focus ul").css("width", sWidth * (len));

  //鼠标滑上焦点图时停止自动播放，滑出时开始自动播放 
  $("#focus").hover(function () {
    clearInterval(picTimer);
  }, function () {
    picTimer = setInterval(function () {
      showPics(index);
      index++;
      if (index == len) {
        index = 0;
      }
    }, 5000); //此5000代表自动播放的间隔，单位：毫秒 
  }).trigger("mouseleave");

  //显示图片函数，根据接收的index值显示相应的内容 
  function showPics(index) {
    //进行切换 
    //根据index值计算ul元素的left值 
    var nowLeft = -index * sWidth;
    //通过animate()调整ul元素滚动到计算出的position 
    $("#focus ul").stop(true, false).animate({
      "left": nowLeft
    }, 300);
    //为当前的按钮切换到选中的效果 
    $("#focus .bov span").stop(true, false).animate({
      "opacity": "0.4"
    }, 300).eq(index).stop(true, false).animate({
      "opacity": "1"
    }, 300); //为当前的按钮切换到选中的效果 

  }
});
</script>
  </head>
  
  <body style="border: 1px solid #f0f0f0;padding:10px;">
    <div id="focus"> 
<ul> 
<li><img class="foodi" src="<c:url value='/images/advertise00.jpg'/>" alt="" />
	
<li><img class="foodi" src="<c:url value='/images/advertise01.jpg'/>" alt=""  />
	
<li><img class="foodi" src="<c:url value='/images/advertise02.jpg'/>" alt="" />
	
<li><img class="foodi" src="<c:url value='/images/advertise03.jpg'/>"  alt="" />
	
<li><img class="foodi" src="<c:url value='/images/advertise04.jpg'/>"  alt="" /> 
	
<li><img class="foodi" src="<c:url value='/images/advertise05.jpg'/>"  alt="" />
	
</ul> 
</div> 
<div class= "divrank">
<h3>H O T</h3>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr id="trTop">
		<th colspan="2" align="center" class="tdTop">书籍信息</th>
		<th class="tdTop">单 价</th>
		<th class="tdTop">销 量</th>
	</tr>

<c:forEach items="${bookList}" var="bookItem">
	<tr align="center" id="booklist">
		
		<td align="left" width="70px" id="tdBookImg">
			<a class="linkImage" href="<c:url value='/BookServlet?method=load&bid=${bookItem.bid }'/>"><img id="imglink" src="<c:url value='/${bookItem.image_b }'/>"/></a>
		</td>
		<td id="tdBookName" width="300px">
		<a href="<c:url value='/BookServlet?method=load&bid=${bookItem.bid }'/>"><span>${bookItem.bname }</span></a>
		</td>
		<td id="tdBookPrice"><span>&yen;<span class="currPrice">${bookItem.currPrice }</span></span></td>
		<td id="tdBookSellnum"><span><span class="sellnum">${bookItem.sellnum }</span>本</span></td>
		
		
	</tr>
</c:forEach>


</table>
</div>
  </body>
</html>
