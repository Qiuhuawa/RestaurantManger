$(document).ready(function () {
	
	//计算一屏高度
	calculateScreenSize();
	
	//改变窗口大小
	$(window).resize(function() {
		calculateScreenSize();
	}).resize();

    //点击登录
    $('.login-button').on('click', function() {
    	  $('.login-button').attr('disabled', true);//不可用
	      var error_login = $('.prompt_warn');
	      error_login.html('');
	      var loginnamestr = $('.login_name').val();
	      var loginnamereg = /^[0-9a-zA-Z_]{4,20}$/;
	      var passwordstr = $('.login_password').val();
	      var passwordreg = /^[0-9a-zA-Z\.$@]{6,20}$/;
	  	  if (loginnamestr == '') {
		  		 error_login.html('账号不能为空！');
		  		 $('.prompt-box').attr('style', 'display:block');
		  		 $('.login_name').focus(function() {
		  			 error_login.html('');
		  			 $('.prompt-box').attr('style', 'display:none');
		  		 });
		  		 $('.login-button').attr('disabled', false);
	  	  } else if (passwordstr == '') {
		  		 error_login.html('密码不能为空！');
		  		 $('.prompt-box').attr('style', 'display:block');
		  		 $('.login_password').focus(function() {
		  			 error_login.html('');
		  			 $('.prompt-box').attr('style', 'display:none');
		  		 });
		  		 $('.login-button').attr('disabled', false);
	  	  } else if (passwordreg.test(passwordstr) == false || loginnamereg.test(loginnamestr) == false) {
		  		 $('.prompt-box').attr('style', 'display:block');
		  		 error_login.html('账号或密码不正确！');
		  		 $('.login-button').attr('disabled', false);
	  	  }
	      if (passwordreg.test(passwordstr) == true && loginnamereg.test(loginnamestr) == true) {
		    	 error_login.html('');
		    	 var publicKey = $('#publicKey').val();
		    	 var encrypt = new JSEncrypt();
	    	     encrypt.setPublicKey(publicKey);
	    	     var data = encrypt.encrypt(passwordstr);
		    	 $('.prompt-box').attr('style', 'display:none');
		    	 $('.login_password').val(data);
				 $('#loginForm').attr('target', '_top');
				 $('#loginForm').attr('action', ctx + '/login');
				 $('#loginForm').submit();
	      }
    });
   
	//错误提示信息
	if ($('#message').val() != '') {
		$('.prompt-box').attr('style', 'display:block');
	} else {
		$('.prompt-box').attr('style', 'display:none');
	}
	
	//绑定键盘事件
    $('input:text:first').focus();
    var $inp = $('input');
    $inp.bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            if ($('.login-button').attr('disabled') != 'disabled' && $('.login-button').attr('disabled') != 'true') {
            	$('.login-button').trigger('click');
            }
        }
    });

});

// 计算一屏幕的高度
function calculateScreenSize() {
	var allHeight = $(document).height();
	var footerHeight = allHeight - 90 - 600;
	$('.login_footer').height(footerHeight);
	$('.login_footer').css('line-height', footerHeight + 'px');
}

//解决系统超时无法跳出框架的问题
if (window != top){
	top.location.href = ctx + '/';
}

