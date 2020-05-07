<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="ctxPath" value="${pageContext.request.contextPath}" scope="request" />
<c:set var="v" value="<%= String.valueOf((new Date()).getTime()) %>"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>后台系统登录</title>
    <%@include file="/resources/common/commonNew.jsp" %>
    <link rel="icon" type="image/x-ico" href="/resources/favicon.ico?v=${v}" mce_href="/resources/favicon.ico?v=${v}"/>
    <link type="text/css" rel="stylesheet" href="/resources/login/login.css?v=${v}"/>
    <script type="text/javascript" src="/common/js/jquery.base64.js?v=${v}"></script>
    <script type="text/javascript" src="/resources/login/login.js?v=${v}"></script>
</head>
<body class="login">
<form id="loginForm" method = 'post'  action = '/login/login'  >
    <div class="login_m">
        <div class="login_logo"><span style="font-size: 35px;">后台系统登录</span></div>
        <div class="login_div" style="padding-bottom: 6%;">
            <input type="hidden" id="redirectUrl" name="redirectUrl" value="${redirectUrl}">
            <input id="loginName_enc" name="loginName" type="hidden" />
            <input id="password_enc" name="password" type="hidden" />
            <div class="login_padding" id="login_model" style="margin-left: 7px;">
                <h2>用户名</h2>
                <label>
                    <input type="text" id="loginName" class="txt_input txt_input2" placeholder="输入用户名" onblur="onblurLoginName();">
                </label>
                <h2>密码</h2>
                <label>
                    <input type="password" id="password" class="txt_input" placeholder="输入密码" onblur="onblurPwd();">
                </label>
                <h2 style="text-align: right;margin-right: 3%;">
                    <a onclick="forgetPassword()" style="color: #0b6ac6">忘记密码</a>
                </h2>
                <div class="rem_sub" style="margin-top: 16px;margin-right: 2%;">
                    <label style="margin-right: 4%">
                        <input type="submit" class="sub_button" id="subButton" value="登陆" onclick="login();return false;" style="opacity: 0.7;">
                    </label>
                    <div id="errorMsg"></div>


                </div>
            </div>
        </div>
        <!--login_div end-->
    </div>
    <!--login_m end-->
</form>
</body>