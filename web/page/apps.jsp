<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--<%--%>
    <%--session.setAttribute("USER_NAME",request.getParameter("userName"));--%>
<%--%>--%>
<!DOCTYPE html>
<html >
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=1170, user-scalable=yes">
	<meta name="author" content="team:lululab,2015-04-15">
	<meta name="description" content="此处放产品描述内容">
	<meta name="keywords" content="此处放产品关键字">
	<title>我的应用</title>

    <link rel="stylesheet" type="text/css" href="${basePath}/css/main.css">
    <script type="text/javascript" src="${basePath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/main.js"></script>



</head>
<body class="default">

<!-- 导航栏Start -->
<jsp:include page="common/header.jsp" flush="true"/>
<!-- 导航栏End -->

<!-- 显示已有app Start -->
<div class="main">
<div class="app-wrapper">
    <ul class="app-list">
    </ul>
    <!-- 添加app -->
    <div class="app-new-btn" id="add-app" onclick="openNew();"><span>+</span>添加APP</div>
</div>
<div class="clear"></div>
</div>
<!-- 显示已有app End-->

<!-- 填充部分 -->
<div id="fill" style="display:none"></div>

<!-- 添加新app弹出框 -->
<div class="wrapper" id="log_wrapper">
<div class="content">
   <div id="form_wrapper" class="form_wrapper">
      <!-- 添加新的App -->
      <form class="login active" action="${basePath}/appInfo!addApp">
         <h3>添加新的App</h3>
         <div>
            <label>App名称：</label>
            <input type="text" name="appName">
            <span class="error">出错啦o(￣ヘ￣o＃) </span>
         </div>
         <div class="bottom">
            <input type="submit" value="提交" onclick="add_app(this.form, '${sessionScope.USER_NAME}')">
            <div class="clear"></div>
         </div>        
      </form>

   </div>
</div>
</div>

<!-- <input type=button value="logout" onclick="log_out();"> -->


<!-- 底部版权栏Start -->
<jsp:include page="common/footer.jsp" flush="true"/>
<!-- 底部版权栏End -->

<!-- 登录注册弹出框 Start-->
<jsp:include page="common/login.jsp" flush="true"/>
<!-- 登录注册弹出框End -->


<script type="text/javascript">
window.onload=function(){
          //用户名需要从session中获取
         $.post("${basePath}/appInfo!appList", {userName: "${sessionScope.USER_NAME}"}, function(result){
         var result = $.parseJSON(result);  
         var length = result.data.length;      
            if(length!=0){
                console.log('数据不为空');
                console.log(length);
                console.log(result);  
                for (var i = 0; i < length; i++) {
                  var username = "${sessionScope.USER_NAME}"
                  var appname = result.data[i].appName;
                  var appid = result.data[i].appId;
                  console.log(result.data[i].appName);
                  var $temp = '<li class="app-name">' +
'                <a href="display.jsp?appName='+appname+'&appId='+ appid +'&userName='+ username +'" data-appname="'+ appname +'" data-appid="'+ appid +'">' +
'                    <div class="app-title">'+appname+'</div>' +
'                    <div class="clear"></div>' +
'                </a>' +
'</li>';
                 //console.log($temp);
                 $(".app-list").append($temp);
                };                 
              
            }else{
                console.log('数据为空');
            }
        });   
}

</script>
	
</body>
</html>