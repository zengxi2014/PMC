<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="footer clearfix">
  <p class="copyRightEn">Copyright &copy; 2015 - 2016 Lululab.</p>
  <%--<p class="copyRightCn">版权所有，翻版必究</p>--%>
</div>

<!-- 信息提示部分Start -->
<div id="j-success-panel" class="ui-success" style="width:150px;top: 350px; left:0px; display:none;" >
  <span class="j-msg">测试消息</span>
</div>
<!-- 信息提示部分End  -->

<input type="hidden" value="${basePath}" id="base-path"/>