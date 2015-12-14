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
	<title>SDK</title>

	<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css">
	<script type="text/javascript" src="${basePath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/main.js"></script>

	<style>
		.zhinan-1, .zhinan-2, .zhinan-3{
			margin-left: 250px;
			margin-bottom: 10px;
			width: 60%;
		}
		p{
			text-indent: 30px;
			line-height: 30px;
		}
	</style>

</head>
<body class="white-book">

<!-- 导航栏Start -->
<jsp:include page="common/header.jsp" flush="true"/>
<!-- 导航栏End -->

<!-- 登录登出处理Start -->
<!-- 登录登出处理End -->

<div class="mod-content mod-SDK-download">

	<div class="clean"></div>
	<!-- 左侧导航栏Start -->
	<div class="side-bar">
		<ul>
			<li class="active">
				<h1><a href="javascript:void(0);" onclick="downloader();">下载</a></h1>
			</li>
			<li class="active">
				<h1><a href="javascript:void(0);">使用指南</a></h1>
			</li>
			<li>
				<h1><a href="javascript:void(0);" onclick="data_any();">数据分析</a></h1>
			</li>
			<li>
				<h1><a href="javascript:void(0);" onclick="user_any();" >用户行为分析</a></h1>
			</li>
			<li>
				<h1><a href="javascript:void(0);" onclick="server_mon();">服务器应用监控</a></h1>
			</li>
		</ul>
	</div>
	<!-- 左侧导航栏End-->

	<!-- 右侧下载区Start -->
	<div class="download-content">
		<div class="mod-section">
			<h1><i class="icon-android"></i>Android SDK下载</h1>
			<ul>
				<li>
					<h1>SDK包</h1>
					<p>V1.0.0（2015-08-27）</p>
					<a href="http://pan.baidu.com/s/1mgMSYSw" class="btn"><i class="icon-download"></i>下载</a>
				</li>
			</ul>

			<div class="clean"></div>
		</div>

		<div class="mod-section">
			<h1><i class="icon-iOS"></i>服务器应用监控</h1>
			<ul>
				<li>
					<h1>SDK包</h1>
					<p>V1.0.0（2015-08-27）</p>
					<a href="http://pan.baidu.com/s/1pJxezgf" class="btn"><i class="icon-download"></i>下载</a>
				</li>
			</ul>

			<div class="clean"></div>
		</div>

		<div class="mod-section" style="visibility:hidden">
			<h1><i style="background:url(../images/U3D_small.png);width:32px;height:31px"></i> Unity Plugin下载</h1>
			<ul>
				<li>
					<h1>SDK包</h1>
					<p>V1.0.9（2015-01-29）</p>
					<a href="javascript:void(0);" class="btn"><i class="icon-download"></i>下载</a>
				</li>
			</ul>
			<div class="clean"></div>
		</div>

	</div>
	<!-- 右侧下载区End-->

	<!-- 用户指南区Start -->
	<!-- 数据分析指南 -->
	<div class="zhinan-1" style="display:none">
		<h3 style="text-align:center;font-size:18px;line-height:40px;">数据分析包使用指南</h3>
		<h4 style="font-size:16px;">（一）崩溃分析</h4>
		<p>使用崩溃分析功能，您需要<br>
			1）在程序中创建一个Application文件并继承工具包中的CrashApplication，如：<br>
			<img src="../images/maoxi-1.png" alt="data1"> <br>
			注意：类名不能为Application或CrashApplication <br>
			2）在AndroidManifest.xml文件中找到<application>配置项并修改默认的Application为XxxApplication. <br>
				<img src="../images/maoxi-2.png" alt="data1"> <br>
				注意：需要写完整的路径名（包名+文件名）<br></p>
		<h4 style="font-size:16px;">（二）关键路径分析</h4>
		<p>使用关键路径分析功能，您需要输入需要分析的关键路径，页面间以逗号隔开，如：<br>
			MainActivity,SecondActivity,ThirdActivity <br>
			注意：关键路径至少包含两个以上的页面，如果页面输入不正确将不会显示结果或显示错误结果<br></p>
		<h4 style="font-size:16px;">（三）频繁序列挖掘</h4>
		<p>使用频繁序列挖掘功能，您需要根据App的用户量设置最小频繁阈值（整数或百分数），系统将根据用户设置的最小频繁阈值计算超过最小频繁阈值的频繁序列。<br></p>
	</div>
	<!-- 用户行为分析指南 -->
	<div class="zhinan-2" style="display:none">
		<h3 style="text-align:center;font-size:18px;line-height:40px;">APM应用监测与行为分析</h3>
		<h4 style="font-size:16px;">使用说明</h4>
		<p>第一步：创建一个使用Eclipse创建一个Android项目。</p>
		<p>第二步：导入apm.jar包</p>
		<p>第三步：在assets文件夹中引入apm-config.properties文件，修改需要配置的选项。</p>
		<p>第四步：接下来像一般的应用一样进行开发即可。</p>
		<h4 style="font-size:16px;">样例应用</h4>
		<p>提供了两个可以运行的开源应用，使用者只要安装此应用，在使用过程中的，对此应用的操作行为都可以被记录。</p>
		<h4 style="font-size:16px;">功能介绍</h4>
		<h5 style="line-height:25px;">Activity监控</h5>
		<p>监控Activity的加载时间，Activity页面加载是一个消耗资的过程，也是用户对一个应用评价的重要指标，流畅地加载页面，应用会受到用户喜爱，对Activity的加载时间进行监控，帮助开发者快速定位应用性能瓶颈。</p>
		<p>监控Activity的使用时间，一个页面的使用时间明天这个页面对用户的受欢迎程度，对页面加载时间进行监控，帮助开发者快速定位应用热点页面。</p>
		<h5 style="line-height:25px;">应用资源分析</h5>
		<p>应用的初始使用的资源信息的情况，往往反映了应用在使用过程是的一样情况，对应用初始化资源使用的情况进行分析，对开发者来说非常必要。</p>
		<p>监控APP初始运行时内存使用变化，反映应用使用一般内存占用。</p>
		<p>监控APP初始运行时CPU使用变化，反映应用的内存消耗。</p>
		<h5 style="line-height:25px;">用户分析</h5>
		<p>开发者最关心事情是应用被多少人使用，每天有多少人使用此使用，APM应用监测记录了应用每日的使用次数和用户注册量。</p>
		<p>分析应用每日的使用次数，反映应用的热门度。</p>
		<p>分析应用每日的注册量，快速衡量用户的传播速度。</p>
		<h5 style="line-height:25px;">渠道分析</h5>
		<p>好的应用渠道可以帮助开发者快速传播产品，提高产品的市场传播率，APM分析用户所使用的应用的不同渠道，帮助开者确定渠道质量，快速推广应用。</p>

	</div>
	<!-- 服务器应用监控指南 -->
	<div class="zhinan-3" style="display:none">
		<h3 style="text-align:center;font-size:18px;line-height:40px;">服务器应用监控使用指南</h3>
		<h4 style="font-size:16px;">环境配置</h4>
		<p><strong>1. </strong>需要配置的基本环境：Java、aspectj 以及搭建测试服务器</p>
		<p><strong>2. </strong>下载服务端应用监控程序包并解压</p>
		<p><img src="../images/lly-1.png" alt=""> <br></p>
		<p><strong>3. </strong>将解压好的程序包导入开发工具中，以下以MyEclipse为例进行演示</p>
		<p><img src="../images/lly-2.png" alt=""> <br></p>
		<p><img src="../images/lly-3.png" alt=""> <br></p>
		<p><strong>4. </strong>在服务器程序中链接已经下载的监控程序</p>
		<p><img src="../images/lly-4.png" alt=""> <br></p>
		<p><img src="../images/lly-5.png" alt=""> <br></p>
		<p><img src="../images/lly-6.png" alt=""> <br></p>
		<p><strong>5. </strong> 在服务器程序中导入以下两个包（安装包附在解压的文件夹中）</p>
		<p><img src="../images/lly-7.png" alt=""> <br></p>
		<p><strong>6. </strong> 将项目转化为aspectj项目</p>
		<p><img src="../images/lly-8.png" alt=""> <br></p>
		<br><br><br><br>
		<h4 style="font-size:16px;">使用说明</h4>
		<p><strong>1. </strong>运行服务器程序，出现如下界面</p>
		<p><img src="../images/lly-9.png" alt=""> <br></p>
		<p><strong>2. </strong>输入userName 点击 start开始监控</p>
		<p><img src="../images/lly-10.png" alt=""> <br></p>
		<p><strong>3. </strong>点击end结束监控</p>
		<p><strong>4. </strong>登陆网站查看云端监控数据</p>
	</div>
	<!-- 用户指南区End -->

	<div class="clean"></div>
</div>
<!-- 底部版权栏Start -->
<jsp:include page="common/footer.jsp" flush="true"/>
<!-- 底部版权栏End -->

<!-- 登录注册弹出框 Start-->
<jsp:include page="common/login.jsp" flush="true"/>
<!-- 登录注册弹出框End -->


<script>
	window.onload=function(){
		var $a = $(".nav a:eq(2)");
		$a.addClass("active");
	}
	function downloader(){
		$(".download-content").show();
		$(".zhinan-1").hide();
		$(".zhinan-2").hide();
		$(".zhinan-3").hide();
	}

	function data_any(){
		$(".download-content").hide();
		$(".zhinan-1").show();
		$(".zhinan-2").hide();
		$(".zhinan-3").hide();
	}

	function user_any(){
		$(".download-content").hide();
		$(".zhinan-1").hide();
		$(".zhinan-2").show();
		$(".zhinan-3").hide();
	}

	function server_mon(){
		$(".download-content").hide();
		$(".zhinan-1").hide();
		$(".zhinan-2").hide();
		$(".zhinan-3").show();
	}

</script>

</body>
</html>