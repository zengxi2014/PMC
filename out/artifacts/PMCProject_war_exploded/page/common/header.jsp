<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="mod-nav">
  <div class="mod-nav-content">
    <!-- logo图标 -->
    <a href="${basePath}/page/index.jsp" class="logo"></a>
    <!-- 导航栏按钮 -->
    <ul class="nav">
      <li><a href="${basePath}/page/index.jsp">首页</a></li>
      <li><a href="${basePath}/page/introduction.jsp">产品介绍</a></li>
      <li><a href="${basePath}/page/whitebook.jsp">SDK</a></li>
      <li><a href="${basePath}/page/contact.jsp">联系我们</a></li>
    </ul>

    <!-- 登录按钮 -->
    <div class="usr-info">
      <%--<c:set  var="alias"  value="Eric"  scope="session"  />--%>
      <%--<c:remove  var="alias"  scope="session"  />--%>
        <%--<span>session是${sessionScope.USER_NAME}</span>--%>

      <c:choose>
        <c:when test="${not empty sessionScope.USER_NAME}">
          <a href="javascript:void(0)" id="uinfo" >
            <i class="icon-me"></i>
            <span class="usr-name">licancan0119</span>
          </a>
          <ul id="oper" class="operation">
            <li><a href="${basePath}/page/apps.jsp?userName=${sessionScope.USER_NAME}" class="myapp">我的app</a></li>
            <li><a href="javascript:void(0);" onclick="log_out();" class="quit">退出登录</a></li>
          </ul>
        </c:when>
        <c:otherwise>
          <a href="javascript:void(0);" class="btn" id="login" onclick="openNew();">登录</a>
        </c:otherwise>
      </c:choose>
    </div>

  </div>
</div>