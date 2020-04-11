<!doctype html>
<html>
<head>
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <title>修改密码</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
    <%@include file="/resources/common/commonNew.jsp" %>
    <link type="text/css" rel="stylesheet" href="/resources/login/login.css"/>
    <script type="text/javascript" src="/common/js/jquery.base64.js"></script>
    <script type="text/javascript" src="/resources/login/editPassword.js?v=${v!0}"></script>
	<style>
		.layui-layer-setwin {
			display: none!important;
		}
	</style>
</head>
<body class="login" mycollectionplug="bind">
<form id="loginForm" id="loginForm" method = 'post'  action = '/le/login'  >
	<div class="login_m">
		<div class="login_logo"><span style="font-size: 35px;">修改密码</span></div>
		<div class="login_boder" style="padding-bottom: 10%">
			<input type="hidden" id="redirectUrl" name="ssoRedirectUrl" value="${redirectUrl!''}">
		    <input type="hidden" id="systemAlias" name="systemAlias" value="${systemAlias!''}">
		    <input type="hidden" id="orgTitlePkid" name="orgTitlePkid" value = "10000">
		    <input id="loginName_enc" name="loginName" type="hidden" />
		    <input id="pwd_enc" name="pwd" type="hidden" />
			<div class="login_padding" id="login_model" style="margin-left: 7px;">
				  <h2>用户名</h2>
				  <label>
				    <input type="text" id="loginName" class="txt_input txt_input2" placeholder="请输入用户名" onblur="javascript:checkLoginName();"/>
				  </label>
				  <h2>原密码</h2>
				    <input type="password" id="oldPwd" class="txt_input" placeholder="请输入密码" onblur="javascript:checkOldPwd();"/>
				  </label>
					<h2>新密码</h2>
					<label>
						<input type="password" id="newPwd" class="txt_input" placeholder="请输入新密码" />
					</label>
					<h2>确认密码</h2>
					<label>
						<input type="password" id="newPwd2" class="txt_input" placeholder="请再次输入新密码"/>
					</label>
				   <div class="rem_sub" style="margin-top: 16px;">
                       <div id="errormsg" class="errorDiv" style="margin-top: 8px;"></div>
					   <br/>
				    <label>
				      <input type="button" class="sub_buttons" id="subButton" value="确认" onclick="javascript:submitEditPassword();" style="opacity: 0.7;">
				    </label>
					   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <label>
					   <input type="button" class="sub_buttons" id="delButton" value="取消" onclick="javascript:cancelEditPassword();" style="opacity: 0.7;float: right">
				   </label>
				  </div>
			</div>
		</div>
		<!--login_boder end-->
	</div>
<!--login_m end-->
</form>

</body>
</html>
