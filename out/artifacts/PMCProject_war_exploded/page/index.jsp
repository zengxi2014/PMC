<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	session.setAttribute("basePath", basePath);
%>

<!DOCTYPE html>
<html >
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=1170, user-scalable=yes">
	<meta name="author" content="team:lululab,2015-04-15">
	<meta name="description" content="此处放产品描述内容">
	<meta name="keywords" content="此处放产品关键字">
	<title>首页</title>

	<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css">
	<script type="text/javascript" src="${basePath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/main.js"></script>

</head>
<body class="default">

<!-- 导航栏Start -->
<jsp:include page="common/header.jsp" flush="true"/>
<!-- 导航栏End -->

<!-- 登录登出处理Start -->
<!-- 登录登出处理End -->

<!-- 首页滚动广告图片Start -->
<div class="mod-banner banner">
<div class="iBanner" id="homeBanner">
<div class="iBanner-box">
	<!-- 三张图片 -->
	<div class="box box-active">
		<a href="javascript:void(0);" target="_blank">
			<img src="${basePath}/images/advertisement1.jpg" alt="" width="1920" height="434">
		</a>
	</div>

	<div class="box">
		<!-- 鼠标样式显示为默认样式 -->
		<a href="javascript:void(0);" style="cursor:default;">
			<img src="${basePath}/images/advertisement2.jpg" alt="" width="1920" height="434">
		</a>
	</div>

	<div class="box">
		<a href="javascript:void(0);" target="_blank">
			<img src="${basePath}/images/advertisement3.jpg" alt="" width="1920" height="434">
		</a>
	</div>
</div>

	<!-- 前后遮罩层箭头 -->
	<div class="prev-mask">
		<div class="previous"></div>
	</div>
	<div class="next-mask">
		<div class="next"></div>
	</div>

	<!-- 三个小点 -->
	<div id="homeBannerNav" class="iBanner-nav">
		<ul>
			<li><a data-ibanner="#homeBanner" href="#0" class="highlight active"></a></li>
			<li><a data-ibanner="#homeBanner" href="#1"></a></li>
			<li><a data-ibanner="#homeBanner" href="#2"></a></li>
		</ul>
	</div>
</div>
</div>
<!-- 首页滚动广告图片End-->

<!-- 我的应用入口Start -->
<div class="btn-wrapper">
	<a href="javascript:void(0);" class="btn-exp">体验Demo</a>
	<!-- 此处应判断session中是否有用户对象，如果没有则弹出登录框，有则进入app.html页面 -->
	<c:choose>
        <c:when test="${not empty sessionScope.USER_NAME}">
          <a href="${basePath}/page/apps.jsp?userName=${sessionScope.USER_NAME}" class="btn-myapp">我的应用</a>
        </c:when>
        <c:otherwise>
          <a href="javascript:void(0);" class="btn-myapp" onclick="openNew();">我的应用</a>
        </c:otherwise>
      </c:choose>
	
</div>
<!-- 我的应用入口End -->

<!-- 功能介绍Start -->
<div class="mod-content">
<div class="functions">
	<!-- 功能描述的四个tab -->
	<div class="function-nav clearfix">
	<ul id="function-nav">
		<li class="function-0">
			<a href="#0" class="active" data-ibanner="#FunctionsTab">
			<i class="icon-function"></i>
			<span class="function-heading">服务器应用监控</span>
			<span class="function-describe"> 深入至代码层面，轻松地解决性能问题</span>
			</a>
		</li>
		<li class="function-1">
			<a href="#1" data-ibanner="#FunctionsTab">
			<i class="icon-function"></i>
			<span class="function-heading">关键路径转化</span>
			<span class="function-describe">自动化进行关键路径转化率分析</span>
			</a>
		</li>
		<li class="function-2">
			<a href="#2" data-ibanner="#FunctionsTab">
			<i class="icon-function"></i>
			<span class="function-heading">用户行为分析</span>
			<span class="function-describe">数据层面追踪用户行为以及使用习惯</span>
			</a>
		</li>
		<li class="function-3">
			<a href="#3" data-ibanner="#FunctionsTab">
			<i class="icon-function"></i>
			<span class="function-heading">频繁序列挖掘</span>
			<span class="function-describe">深入解析用户用户使用习惯，挖掘频繁序列</span>
			</a>
		</li>		
	</ul>
	</div>

	<div id="FunctionsTab">
		<ul class="function-item clearfix iBanner-box">
			<li class="box box-active"><img src="${basePath}/images/tab3.jpg" alt="" width="746" height="417"></li>
			<li class="box"><img src="${basePath}/images/tab1.jpg" alt="" width="746" height="417"></li>
			<li class="box"><img src="${basePath}/images/tab0.jpg" alt="" width="746" height="417"></li>
			<li class="box"><img src="${basePath}/images/tab2.jpg" alt="" width="746" height="417"></li>
		</ul>
	</div>
</div>
</div>
<!-- 功能介绍End -->

<!-- 底部版权栏Start -->
<jsp:include page="common/footer.jsp" flush="true"/>
<!-- 底部版权栏End -->

<!-- 登录注册弹出框 Start-->
<jsp:include page="common/login.jsp" flush="true"/>
<!-- 登录注册弹出框End -->

<script type="text/javascript">
window.onload=function(){
	var $a = $(".nav a:first");
	$a.addClass("active");
}
//功能介绍部分JS
var functionTab = new iBanner('#FunctionsTab', {
  indicator:'#function-nav',
  backgroundColor:['#454f57', '#454f57', '#454f57', '#454f57'],
  animate: function (activeItem, targetItem) {
    activeItem.hide()
    targetItem.show().css('opacity', 0.6).stop().animate({opacity: 1}, 500)
  }
}),

//首页广告滚动JS
homeBanner = new iBanner('#homeBanner', {
  indicator:'#homeBannerNav',
  backgroundColor:['#454f57', '#007ebb'],
  animate: function (activeItem, targetItem) {
    activeItem.hide()
    targetItem.show().css('opacity', 0.2).stop().animate({opacity: 1}, 200)
  },
  interval: 5000,
  isAuto : true
}) 

</script>
	
</body>
</html>