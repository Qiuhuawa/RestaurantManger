<#assign ctx = request.contextPath />
<!DOCTYPE html>
<html lang="ch">
<head>
    <base id="ctx" href="${ctx}"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="pragma" content="no-cache"> 
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="expires" content="0">   
    <title>登录</title>
    <link rel="shortcut icon" type="images/x-icon" href="${ctx}/assets/images/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/css/login.css">
    <script type="text/javascript">
        var ctx = '${ctx}';
	</script>
	<script>
        //rem 适配
        (function (doc, win) {
            var html = doc.getElementsByTagName("html")[0],
                // orientationchange->手机屏幕转屏事件
                // resize->页面大小改变事件(一般pc端有用)
                reEvt = "orientationchange" in win ? "orientationchange" : "resize",
                reFontSize = function () {
                    var clientW = doc.documentElement.clientWidth || doc.body.clientWidth;
                    if (!clientW) {
                        return;
                    }
                    html.style.fontSize = 100 * (clientW / 1920) + "px";
                }
            win.addEventListener(reEvt, reFontSize);
            // DOMContentLoaded->dom加载完就执行,onload要dom / css /js都加载完才执行
            doc.addEventListener("DOMContentLoaded", reFontSize);
        })(document, window);
    </script>
<body>
<div class="browser-tips">
	<div class="browser-tips-con">
		当前您使用的浏览器不是使用本系统体验最好的浏览器，为了给您带来更好的体验，建议您使用谷歌或火狐浏览器 ! <a href="${ctx}/browser" target="_blank"><i class="icon-xiazai11"></i>点击去下载</a>
	</div>
</div>
<div class="login-box">
    <div class="login-con">
    	<div class="logo-top">
			<a href="${ctx!}">
	            <#if sysSystemSetting.systemImageUrl?? && sysSystemSetting.systemImageUrl != '' >
	            	<img src="${ctx}${sysSystemSetting.systemImageUrl}" height="46" class="login_logo login_logo_set"/>
	            <#else>
	                <img src="${ctx}/assets/images/login-logo.png" class="login_logo"/>					
	            </#if>
	        </a>
            <span class="tiltle">${sysSystemSetting.systemName!'餐厅管理系统'}</span>
        </div>
       
        <div class="login-center">
            <div class="login-title">帐号登录</div>
            <form method="post" id="loginForm" target="_top">
                <input type="hidden" id="publicKey" value="${publicKey}"/>
                <input type="hidden" id="message" value="${message}">
                <input style="display:none" type="text" name="usernameremembered"/>
				<input style="display:none" type="password" name="passwordremembered"/>
                <div class="login-input">
                    <input type="text" id="login_name" class="login_name" name="login_name" placeholder="帐号" autocomplete="off">
                </div>
                <div class="login-input">
                    <input type="password" id="login_password" class="login_password" name="login_password" placeholder="密码" autocomplete="off">
                </div>
                <button type="button" id="denglu" class="login-button">登录</button>
                <div class="prompt-box">
                    <span class="prompt_warn" id ="result_message">${message}</span>
                </div>
            </form>
        </div>
        <div class="login-footer">
        	<p>技术支持 ${sysSystemSetting.tecSupportName!'？？？'}</p>
      		<p class="jianyi">建议使用谷歌或火狐浏览器最新版本</p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${ctx}/assets/lib/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/lib/jsencrypt/jsencrypt.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/login.js"></script>
</html>
