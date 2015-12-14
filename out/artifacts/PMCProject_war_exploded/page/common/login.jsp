<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="wrapper" id="log_wrapper">
	<div class="content">
		<div id="form_wrapper" class="form_wrapper">
			<!-- 注册部分 -->
			<form class="register" action=${basePath}/register" >
				<h3>注册</h3>
				<div>
					<label>用户名：</label>
					<input type="text" name="userName" >
					<span class="error">出错啦o(￣ヘ￣o＃) </span>
				</div>
				<div>
					<label>邮箱：</label>
					<input type="text" name="email" >
					<span class="error">出错啦o(￣ヘ￣o＃) </span>
				</div>
				<div>
					<label>密码：</label>
					<input type="password" name="userPassword">
					<span class="error">出错啦o(￣ヘ￣o＃) </span>
				</div>
				<div>
					<label>再次输入密码：</label>
					<input type="password" name="userPpassword2" >
					<span class="error">出错啦o(￣ヘ￣o＃) </span>
				</div>
				<div class="bottom">
					<a href="index.jsp" rel="login" class="linkform">已有账号，点此登录</a>
					<input type="submit" onclick="register_check(this.form)" value="注册">
					<div class="clear"></div>
				</div>
			</form>
			<!-- 登录部分 -->
			<form class="login active" action="${basePath}/login">
				<h3>登录</h3>
				<div>
					<label>用户名：</label>
					<input type="text" name="userName">
					<span class="error">出错啦o(￣ヘ￣o＃) </span>
				</div>
				<div>
					<%--<label>密码：<a href="" rel="forgot_password" class="forgot linkform">忘记密码？</a></label>--%>
					<input type="password" name="userPassword">
					<span class="error">出错啦o(￣ヘ￣o＃) </span>
				</div>
				<div class="bottom">
					<%--<div class="remember">--%>
						<%--<input type="checkbox">--%>
						<%--<span>保持登录</span>--%>
					<%--</div>--%>
					<input type="submit" onclick="login_check(this.form)" value="登录">
					<a href="" rel="register" class="linkform">没有账号，点此注册</a>
					<div class="clear"></div>
				</div>
			</form>
			<!-- 忘记密码部分 -->
			<form class="forgot_password">
				<h3>忘记密码</h3>
				<div>
					<label>用户名</label>
					<input type="text" name="userName">
					<span class="error">出错啦o(￣ヘ￣o＃) </span>
				</div>
				<div class="bottom">
					<input type="submit" value="发送申请">
					<a href="index.jsp" rel="login" class="linkform">突然想起来密码？点此登录</a>
					<a href="register.html" rel="register" class="linkform">没有账号，点此注册</a>
					<div class="clear"></div>
				</div>
			</form>

		</div>
	</div>
</div>