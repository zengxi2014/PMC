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
   <title>联系我们</title>

   <link rel="stylesheet" type="text/css" href="${basePath}/css/main.css">
   <script type="text/javascript" src="${basePath}/js/jquery.min.js"></script>
   <script type="text/javascript" src="${basePath}/js/main.js"></script>

</head>
<body class="default">

<!-- 导航栏Start -->
<jsp:include page="common/header.jsp" flush="true"/>
<!-- 导航栏End -->



<!-- 底部版权栏Start -->
<jsp:include page="common/footer.jsp" flush="true"/>
<!-- 底部版权栏End -->

<!-- 登录注册弹出框 Start-->
<jsp:include page="common/login.jsp" flush="true"/>
<!-- 登录注册弹出框End -->

<script type="text/javascript">
   window.onload=function(){
      var $a = $(".nav a:last");
      $a.addClass("active");
   }
</script>
   
</body>
</html>