<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>后台系统登录</title>
    <script type="text/javascript" src="/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/common/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/common/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/common/js/layer.min.js"></script>
    <link rel="icon" href="/resources/favicon.ico" mce_href="/resources/favicon.ico" type="image/x-ico"/>
    <link rel="stylesheet" type="text/css" href="/common/easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/common/css/supper/superBlue.css" />
    <link rel="stylesheet" type="text/css" href="/common/fonts/font-awesome.min.css" />
    <script type="text/javascript" charset="utf-8" src="/resources/supper/super.js?v=20190715" ></script>
</head>
<body id="main" class="easyui-layout">
<div data-options="region:'north',border:false" class="super-north" style="height: 50px;">
    <!--顶部-->
    <div class="super-navigation">
        <div class="super-navigation-title" style="text-align: center">black后台系统</div>
        <div class="super-navigation-main">
            <div class="super-setting-left">
                <%--<ul>--%>
                    <%--<li><i class="fa fa-commenting-o"></i></li>--%>
                    <%--<li><i class="fa fa-envelope-o"></i></li>--%>
                    <%--<li><i class="fa fa-bell-o"></i></li>--%>
                <%--</ul>--%>
            </div>
            <div class="super-setting-right">
                <ul>
                    <li class="user">
                        <span>${curUserName}</span>
                    </li>
                    <li>
                        <div style="height: 50px">
                            <i class="fa fa-gears" style="padding-top: 40%"></i>
                        </div>
                        <div id="mm" class="easyui-menu">
                            <div>个人中心</div>
                            <div class="menu-sep"></div>
                            <div onclick="logout('${ssoUrl}');">退出</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div id="easyui-layout-west" data-options="region:'west',title:'菜单',border:false">
    <!--左侧导航-->
    <div class="easyui-accordion" data-options="border:false,fit:true,selected:true">

        <c:forEach items="${level1List}" var="item">
            <div title="${item.name}" data-options="iconCls:'fa fa-navicon'">

                <c:forEach items="${item.children}" var="item2">
                    <ul>
                        <li data-url='${item2.url}'>${item2.name}</li>
                    </ul>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

</div>

<div data-options="region:'center'" style="padding-top: 2px;">
    <!--主要内容-->
    <div id="tabs" class="easyui-tabs" data-options="border:false,fit:true">
    </div>
</div>

<script type="text/javascript">
    function logout(ssoUrl){
        window.location.href=ssoUrl+"/logout";
    }
</script>
</body>
</html>

