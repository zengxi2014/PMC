
//首页广告banner
(function(e) {
	var t = function(e, t) {
		this.$element = $(e), this.options = t, this.$indicator = $(this.options.indicator) || $element.find("iBanner-nav") || null, this.$active = this.$element.find(".box-active"), this.init()
	};
	t.prototype = {
		constructor: t,
		init: function() {
			return this.$indicator.hide(), this.bindEvent(), this.changeImgToBackground(), this.$element.find(".box").length > 1 && (this.cycle(), this.$element.find(".prev-mask, .next-mask").show(), this.$indicator.show()), this.bannerMask(), this
		},
		changeImgToBackground: function() {
			var e = this.$element.find(".box"),
				t;
			for (var n = 0; n < e.length; n++) t = $(e[n]).find("img").attr("src"), $(e[n]).find("img").remove(), $(e[n]).css("backgroundImage", "url(" + t + ")"), this.options.backgroundColor && $(e[n]).css("backgroundColor", this.options.backgroundColor[n])
		},
		getActiveIndex: function() {
			return this.$boxs = this.$active.parent().children(), this.$boxs.index(this.$active)
		},
		slide: function() {},
		slideTo: function(e) {
			var t = this,
				n = this.interval,
				r = this.getActiveIndex(),
				i = this.$element.find(".iBanner-box .box").eq(e);
			return n && this.pause(), this.options.animate && this.options.animate(t.$active, i), this.$active.removeClass("box-active"), i.addClass("box-active"), this.$active = i, this.$indicator.length && this.$indicator.find("[data-ibanner]").removeClass("active").eq(e).addClass("active"), n && this.cycle(), this
		},
		next: function() {
			var e = this,
				t = this.getActiveIndex(),
				n = this.$active.next().length ? t + 1 : 0;
			return this.slideTo(n)
		},
		prev: function() {
			var e = this,
				t = this.getActiveIndex(),
				n = this.$active.prev().length ? t - 1 : this.$element.find(".iBanner-box .box").length - 1;
			return this.slideTo(n)
		},
		cycle: function(e) {
			var t = this;
			return e || (this.paused = !1), this.interval && clearInterval(this.interval), this.options.interval && !this.paused && (this.interval = setInterval($.proxy(this.next, this), this.options.interval)), this
		},
		pause: function() {
			return this.paused = !0, clearInterval(this.interval), this.interval = null, this
		},
		bindEvent: function() {
			var e = this;
			e.$indicator.length && e.$indicator.on("click", "[data-ibanner]", function(t) {
				var n = $(this),
					r = $(n.data("ibanner")),
					i = n.attr("data-slide-to") || n.attr("href").match(/^#(\d+)$/)[1];
				e.$indicator.find(".active").removeClass("active"), e.slideTo(i), n.addClass("active"), t.preventDefault()
			})
		},
		bannerMask: function() {
			var e = this;
			this.$element.find(".prev-mask").hover(function() {
				$(this).parent().addClass("iBanner-prev")
			}, function() {
				$(this).parent().removeClass("iBanner-prev")
			}), this.$element.find(".next-mask").hover(function() {
				$(this).parent().addClass("iBanner-next")
			}, function() {
				$(this).parent().removeClass("iBanner-next")
			}), this.$element.find(".next-mask").on("click", function(t) {
				e.next(), t.preventDefault()
			}), this.$element.find(".prev-mask").on("click", function(t) {
				e.prev(), t.preventDefault()
			})
		}
	}, e.iBanner = t
})(window);



/////////////////////注册登录遮罩层Start///////////////////////////
function openNew(){
	//获取页面的高度和宽度
	var sWidth=document.body.scrollWidth;
	var sHeight=document.body.scrollHeight;
	
	//获取页面的可视区域高度和宽度
	var wHeight=document.documentElement.clientHeight;
	
	var oMask=document.createElement("div");
		oMask.id="mask";
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		document.body.appendChild(oMask);
	// var oLogin=document.createElement("div");
	var oLogin = document.getElementById("log_wrapper");	
		//oLogin.id="reg_login";
		//oLogin.innerHTML="<div class='loginCon'><div id='close'>点击关闭</div></div>";
		//document.body.appendChild(oLogin);
		oLogin.style.display='block';		
	
	//获取登陆框的宽和高
	var dHeight=oLogin.offsetHeight;
	var dWidth=oLogin.offsetWidth;
		//设置登陆框的left和top
		oLogin.style.left=sWidth/2-dWidth/2+"px";
		oLogin.style.top=wHeight/2-dHeight/2+"px";
	//点击关闭按钮
	//var oClose=document.getElementById("close");
	
		//点击登陆框以外的区域也可以关闭登陆框
	//oClose.onclick=oMask.onclick=function(){
		oMask.onclick=function(){
		//document.body.removeChild(oLogin);
		oLogin.style.display="none";
		document.body.removeChild(oMask);
		};
};
					

/////////////////////注册登录遮罩层End///////////////////////////

/////////////////////注册登录弹出框动画效果Start///////////////////////////
$(function() {
	//the form wrapper (includes all forms)
	var $form_wrapper = $('#form_wrapper'),
	//the current form is the one with class active
	$currentForm = $form_wrapper.children('form.active'),
	//the change form links
	$linkform = $form_wrapper.find('.linkform');

	//get width and height of each form and store them for later						
	$form_wrapper.children('form').each(function(i){
		var $theForm	= $(this);
		//solve the inline display none problem when using fadeIn fadeOut
		if(!$theForm.hasClass('active'))
		$theForm.hide();

		$theForm.data({
		width	: $theForm.width(),
		height	: $theForm.height()
		});
	});

	//set width and height of wrapper (same of current form)
	setWrapperWidth();

	/*
	clicking a link (change form event) in the form
	makes the current form hide.
	The wrapper animates its width and height to the 
	width and height of the new current form.
	After the animation, the new form is shown
	*/
	$linkform.bind('click',function(e){
		var $link = $(this);
		var target = $link.attr('rel');
		$currentForm.fadeOut(400,function(){
			//remove class active from current form
			$currentForm.removeClass('active');
			//new current form
			$currentForm= $form_wrapper.children('form.'+target);
			//animate the wrapper
			$form_wrapper.stop()
			.animate({
			width : $currentForm.data('width') + 'px',
			height : $currentForm.data('height') + 'px'
			},500,function(){
			//new form gets class active
			$currentForm.addClass('active');
			//show the new form
			$currentForm.fadeIn(400);
			});
		});
		e.preventDefault();
	});

	function setWrapperWidth(){
		$form_wrapper.css({
		width	: $currentForm.data('width') + 'px',
		height	: $currentForm.data('height') + 'px'
		});
	}

	/*
	for the demo we disabled the submit buttons
	if you submit the form, you need to check the 
	which form was submited, and give the class active 
	to the form you want to show
	*/
	$form_wrapper.find('input[type="submit"]')
	.click(function(e){
	e.preventDefault();
	});	
});
/////////////////////注册登录弹出框动画效果End///////////////////////////

/////////////////////注册登录功能Start///////////////////////////
//登录信息检查
function login_check(form){
    var username=form.userName.value;
    var psw=form.userPassword.value;
	var name_check=/^[A-Za-z0-9]+$/;
    if (username=="") {
		showMsg("请输入用户名");
        return;
    }else if(!(name_check.test(username))){
		showMsg("用户名只能由字母和数字组成");
		return;
	}
    else if (psw=="") {
		showMsg("请输入密码");
        return;
    }

    var postdata = { "userName": username, "userPassword":psw};
	var basepath = $("#base-path").val();

        $.post(form.action, postdata, function(data){
            data = $.parseJSON(data);
            if(data.result=="OK"){
                console.log('success');
				showMsg("登录成功");

                location.href=basepath+"/page/apps.jsp?userName="+username;
                 //跳转到app页面 
              
            }else{
                console.log('fail');
				showMsg("用户名或密码错误");
                // 提示用户名或密码错误
            }
        });
}

//用户注册信息验证 
function register_check(form){
    var username = form.userName.value;
    var password = form.userPassword.value;
    var email=form.email.value;
    var conform_password = form.userPassword.value;
    var email_check =  /\w@\w*\.\w/;
	var basepath = $("#base-path").val();

    if(username==""){
		showMsg("用户名不能为空");
    }else if(!(email_check.test(email))){
		showMsg("邮箱格式错误");

    }else	if(password==""){
		showMsg("密码不能为空");
    }else  if(password!=conform_password){
		showMsg("两次密码不相同");
    }else{

        $.post(form.action, { 
        	 userName : username,
        	 userPassword : password , 
        	 email : email
        	},
        	  function(data){
            data = $.parseJSON(data);
            console.log(data);
            if(data.result=="OK"){
                console.log('success');
				showMsg("注册成功");
                location.href=basepath+"/page/index.jsp";
               // 注册成功后转到首页
            }else{
                console.log('fail');
				showMsg("用户名已存在");
                // 提示用户名已存在或其他错误
            }
        }); 

    }
}

//注册新的app
function add_app(form, username){
    var appname=form.appName.value;
	var name_check=/^[A-Za-z0-9]+$/;
    if (appname=="") {
        showMsg("请输入app名称");
        return;
    }else if(!(name_check.test(appname))){
		showMsg("用户名只能由字母和数字组成");
		return;
	}

    var postdata = { "userName": username, "appName":appname};

        $.post(form.action, postdata, function(data){
            data = $.parseJSON(data);
            if(data.ret==0){
                console.log('success');
				showMsg("成功添加App");
                // 提示注册成功
				$("#log_wrapper").hide();
				$("#mask").remove();
				//将新节点加入页面
				var $temp = '<li class="app-name">' +
					'                <a href="display.jsp?appName='+appname+'" data-appname="'+ appname +'">' +
					'                    <div class="app-title">'+appname+'</div>' +
					'                    <div class="clear"></div>' +
					'                </a>' +
					'</li>';
				//console.log($temp);
				$(".app-list").append($temp);

            }else{
                console.log('fail');
				showMsg("添加App失败");
                // 提示注册失败
            }
        });
}


// 退出登录接口
function log_out(){
	var basepath = $("#base-path").val();

         $.post(basepath+"/logout", function(data){
			 data = $.parseJSON(data);
			 console.log(data);
			 if(data.result=="OK"){
				 console.log('success');

				// location.href=basepath+"/page/common/temp.jsp";
				 location.href=basepath+"/page/index.jsp";
				 // 退出后转到首页
			 }else{

			 }
        });
}

// 显示提示消息
function showMsg(msg, fun, params){
	if(showMsg.timer)
		clearTimeout(showMsg.timer);

	var $panel = $("#j-success-panel");
	$panel.find(".j-msg").html(msg);
	$panel.show();
	$panel.css({
		"left": (parseInt($(window).width()) - parseInt($panel.width())) / 2 + "px"
	});
	//alert($panel.css("left"));
	showMsg.timer=setTimeout(function(){
		$("#j-success-panel").hide();

		if(typeof fun == "function") {
			fun.call(null, params);
		}
	},1000);

}



/////////////////////注册登录功能 End///////////////////////////