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
   <title>我的应用数据</title>

    <link rel="stylesheet" type="text/css" href="${basePath}/css/main.css">
    <script type="text/javascript" src="${basePath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/main.js"></script>



</head>
<body class="default">

<!-- 导航栏Start -->
<jsp:include page="common/header.jsp" flush="true"/>
<!-- 导航栏End -->

<!-- 左侧导航栏Start -->
<div class="new_crash_left">
  <div class="use_list">
    <div class="fgx"></div>
   
    <div class="sjjk cwfx">
      <h4>应用统计分析</h4>
      <ul>
      <li class="li1 clearfix active"><a href="javascript:void(0);" data-url="../maoxi/crash/crash_list.html"><span class="span1"></span><span class="span2">崩溃分析</span></a></li>
        <li class="li1 clearfix"><a href="javascript:void(0);" data-url="../junchao/views/activity.html"><span class="span1"></span><span class="span2">Activity信息采集</span></a></li>
        <li class="li2 clearfix"><a href="javascript:void(0);" data-url="../junchao/views/user.html"><span class="span22"></span><span class="span2">用户分析</span></a></li>
        <li class="li3 clearfix"><a href="javascript:void(0);" data-url="../junchao/views/resource.html"><span class="span3"></span><span class="span2">应用资源分析</span></a></li>
        <li class="li4 clearfix"><a href="javascript:void(0);" data-url="../junchao/views/ways.html"><span class="span4"></span><span class="span2">渠道分析</span></a></li>        
        </ul>
      <div class="fgx"></div>
    </div>

     <div class="sjjk">
      <h4>用户行为挖掘</h4>
      <ul>    
        
        <li class="li2 clearfix"><a href="javascript:void(0);" data-url="../maoxi/funnel/funnel.html"><span class="span22"></span><span class="span2">关键路径分析</span></a></li>
        <li class="li3 clearfix"><a href="javascript:void(0);" data-url="../maoxi/frequent/maximum_frequent_sequences.html"><span class="span3"></span><span class="span2">频繁序列挖掘</span></a></li>
        </li>
      </ul>
      <div class="fgx"></div>
    </div>
    
    <div class="sjjk qsfx">
      <h4>服务器性能监控</h4>
      <ul>
        <li class="li1 clearfix"><a href="javascript:void(0);" data-url="../lly-severMonitor-page/classTrace.html"><span class="span1"></span><span class="span2">服务器应用监控</span></a></li>
      </ul>
    </div>       

  </div>
</div>
<!-- 左侧导航栏End -->

<iframe src="../maoxi/crash/crash_list.html" frameborder="0" class="data-display" >
  
</iframe>

<!-- 底部版权栏Start -->
<jsp:include page="common/footer.jsp" flush="true"/>
<!-- 底部版权栏End -->

<!-- 登录注册弹出框 Start-->
<jsp:include page="common/login.jsp" flush="true"/>
<!-- 登录注册弹出框End -->


<script type="text/javascript">
window.onload=function(){
  //将tab中的url传给iframe，在iframe中打开
    var $change = $(".new_crash_left a");
    $change.click(function(){
        var  $this = $(this);
        var url = $this.attr("data-url");
        $(".data-display").attr("src",url);
        $(".new_crash_left .active").removeClass("active");
        $this.parent().addClass("active");
    })

//数据显示页面的高度随屏幕高度变化
    var new_height = parseInt($(window).height())-90;

    $(".data-display").css("height",new_height+"px");
    $(" .new_crash_left").css("height",new_height+"px");
}

</script>
  
</body>
</html>