<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html >
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=1170, user-scalable=yes">
	<meta name="author" content="team:lululab,2015-04-15">
	<meta name="description" content="此处放产品描述内容">
	<meta name="keywords" content="此处放产品关键字">
	<title>产品介绍</title>

	<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css">
	<script type="text/javascript" src="${basePath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/main.js"></script>

</head>
<body class="introduction">

<!-- 导航栏Start -->
<jsp:include page="common/header.jsp" flush="true"/>
<!-- 导航栏End -->

<!-- 登录登出处理Start -->
<!-- 登录登出处理End -->

<!-- 顶部产品介绍内容，白色背景Start -->
<%--<div class="bg-white">--%>
<%--<div class="mod-section">--%>
	<%--<div class="mod-headgroup">--%>
		<%--<h1>重视Test</h1>--%>
		<%--<p>重视Test的原因重视Test的原因重视Test的原因</p>--%>
	<%--</div>--%>
	<%--<div class="mod-section-content"><img src="../images/introduction-zhongshi.jpg" alt="" width="896" height="354" class="mod-section-img"></div>--%>
<%--</div>--%>
</div>
<!-- 顶部产品介绍内容，白色背景End -->

<!-- 下方详细介绍内容，灰色背景Start -->
<div class="bg-gray">
<div class="mod-section">
	<div class="mod-headgroup">
	         <p class="sub-title">PMC用户行为分析平台让你：</p>
	         <h1>深入解析用户行为习惯，提供针对性性能测试方案</h1>
	</div>

	<div class="mod-headgroup">
		<p>支持两大移动平台：Android、iOS</p>
	</div>

	<ul class="start-from">
		<li class="icon-start-ios"><a href="whitebook.jsp"><i></i></a></li>
		<li class="icon-start-android"><a href="javascript:showMsg('待推出')"><i></i></a></li>
	</ul>

  	<div class="clean"></div>

	<div class="mod-headgroup">
		<h2>全面的崩溃情况监控</h2>
		<p>统计一段时间内的崩溃情况，并对崩溃原因进行硬件和软件层面上的分析</p>
	</div>
	<div class="mod-section-img-frame">
		<img src="../images/introduction1.png" alt="" width="893" height="438">
	</div>

	<div class="mod-headgroup">
		<h2>对Android Activity信息进行采集</h2>
		<p>统计Activity的加载时间、使用时间，Fragment的使用时间等重要信息</p>
	</div>
	<div class="mod-section-img-frame">
		<img src="../images/introduction2.png" alt="" width="893" height="357">
	</div>

	<div class="mod-headgroup">
		<h2>分析应用资源消耗</h2>
		<p>分析和统计应用所占用的CPU和内存等硬件信息</p>
	</div>
	<div class="mod-section-img-frame">
		<img src="../images/introduction3.png" alt="" width="893" height="357">
	</div>

	<div class="mod-headgroup">
		<h2>渠道分析</h2>
		<p>分析应用的使用渠道</p>
	</div>
	<div class="mod-section-img-frame">
		<img src="../images/introduction4.png" alt="" width="893" height="357">
	</div>
  
	<div class="mod-headgroup">
		<h2>代码级别深度追踪</h2>
		<p>代码层面追踪class的使用信息</p>
	</div>
	<div class="mod-section-img-frame"><img src="../images/introduction5.png" alt="" width="893" height="465"></div>

</div>
</div>
<!-- 下方详细介绍内容，灰色背景End -->

<!-- 底部下载栏Start -->
<div class="bg-white">
<div class="mod-start clearfix">
	<div class="mod-headgroup">
		<div class="dec-line"></div>
		<h1>简单一步，质量从此可控</h1>
		<div class="dec-line"></div>
		<div class="clean"></div>
	</div>

	<ul class="start-from">
		<li class="icon-start-ios"><a href="whitebook.jsp"><i></i><span>Android</span></a></li>
		<li class="icon-start-android"><a href="javascript:showMsg('待推出')""><i></i><span>iOS</span></a></li>
	</ul>

	<div class="clean"></div>
</div>
</div>
<!-- 底部下载栏End -->

<!-- 底部版权栏Start -->
<jsp:include page="common/footer.jsp" flush="true"/>
<!-- 底部版权栏End -->

<!-- 登录注册弹出框 Start-->
<jsp:include page="common/login.jsp" flush="true"/>
<!-- 登录注册弹出框End -->


<script type="text/javascript">
window.onload=function(){
		var $a = $(".nav a:eq(1)");
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
})
</script>
	
</body>
</html>