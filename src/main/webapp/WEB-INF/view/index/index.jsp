<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="v" value="<%= String.valueOf((new Date()).getTime()) %>"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>后台系统登录</title>
    <link rel="icon" type="image/x-ico" href="/resources/favicon.ico" mce_href="/resources/favicon.ico"/>
    <script type="text/javascript" src="/common/js/jquery-1.11.3.min.js?v=${v}"></script>
    <script type="text/javascript" src="/common/js/jquery.easyui.min.js?v=${v}"></script>
    <script type="text/javascript" src="/common/js/easyui-lang-zh_CN.js?v=${v}"></script>
    <script type="text/javascript" src="/common/js/layer.min.js?v=${v}"></script>
    <script type="text/javascript" src="/common/js/jquery.base64.js?v=${v}"></script>

    <link rel="stylesheet" type="text/css" href="/common/easyUI/themes/default/easyui.css?v=${v}">
    <link rel="stylesheet" type="text/css" href="/common/css/supper/superBlue.css?v=${v}"/>
    <link rel="stylesheet" type="text/css" href="/common/fonts/font-awesome.min.css?v=${v}"/>
    <link rel="stylesheet" type="text/css" href="/common/easyUI/themes/icon.css?v=${v}">

    <script type="text/javascript" src="/common/js/jquery-form.js?v=${v}"></script>
    <script type="text/javascript" charset="utf-8" src="/resources/index/index.js?v=${v}"></script>
    <style type="text/css">
        #tabs .tabs-panels .panel-body {
            overflow: hidden;
        }

        .set-div {
            height: 30px !important;
        }

        .dialog-button {
            top: 0px !important;
        }
    </style>

</head>
<body id="main" class="easyui-layout">
<div data-options="region:'north',border:false" class="super-north" style="height: 50px;">
    <!--顶部-->
    <div class="super-navigation">
        <div class="super-navigation-title" style="text-align: center;">black后台系统</div>
        <div class="super-navigation-main">
            <div class="super-setting-left">
                <%-- <ul>
                 <li><i class="fa fa-commenting-o"></i></li>
                 <li><i class="fa fa-envelope-o"></i></li>
                 <li><i class="fa fa-bell-o"></i></li>
                 </ul>--%>
            </div>
            <div class="super-setting-right">
                <ul>
                    <li class="user" style="text-align: center">
                        <span style="float: none">${curUserName}</span>
                    </li>
                    <li>
                        <div style="height: 50px" class="super-setting-icon">
                            <i class="fa fa-gears" style="padding-top: 40%"></i>
                        </div>
                        <div id="setDiv" class="easyui-menu">
                            <input hidden id="userId" value="${userId}"/>
                            <div onclick="resetPassword();" class="set-div" style="height: 30px;">修改密码</div>
                            <div class="menu-sep"></div>
                            <div onclick="logout();" class="set-div" style="height: 30px;">退出</div>
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
                    <ul class="level2">
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

<div style="display:none">
    <div id="resetPasswordDialog" class="dialog">
        <table style="width:90%;margin:10px 10px 0 20px;">
            <tr style="height:25px;">
                <th><span style="font-size: 10pt;">原密码:<font size="2" color="red">*</font></span></th>
                <td style="align:center;">
                    <input class="easyui-textbox password-input" type="text" required="required" data-options="prompt:'请输入原密码',
                    events:{focus: function(){ easyuitextfocus($(this)); },blur: function(){ easyuitextblur($(this)); }}"
                           id="oldPassword" style="width: 180px;height: 25px;">
                </td>
            </tr>
            <tr style="height:25px;">
                <th><span style="font-size: 10pt;">新密码:<font size="2" color="red">*</font></span></th>
                <td style="align:center;">
                    <input class="easyui-textbox password-input" type="text" required="required" data-options="prompt:'请输入新密码',
                    events:{focus: function(){ easyuitextfocus($(this)); },blur: function(){ easyuitextblur($(this)); }}"
                           id="newPassword" style="width: 180px;height: 25px;">
                </td>
            </tr>
            <tr style="height:25px;">
                <th><span style="font-size: 10pt;">新密码:<font size="2" color="red">*</font></span></th>
                <td style="align:center;">
                    <input class="easyui-textbox password-input" type="text" required="required" data-options="prompt:'请再次输入新密码',
                    events:{focus: function(){ easyuitextfocus($(this)); },blur: function(){ easyuitextblur($(this)); }}"
                           id="newPassword2" style="width: 180px;height: 25px;">
                </td>
            </tr>
        </table>
    </div>
</div>

</body>

</html>
<script type="text/javascript">

    function logout() {
        layer.confirm('确认退出系统吗?', {icon: 3}, function () {
            window.location.href = "/login/logout";
        });
    }

    function resetPassword() {
        $("#resetPasswordDialog").dialog({
            title: '修改密码',
            width: '380px',
            height: '200px',
            close: true,
            shadow: false,
            modal: true,
            buttons: [{
                text: '提交',
                iconCls: 'icon-ok',
                handler: function () {
                    updatePassword();
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#resetPasswordDialog').dialog('close');
                }
            }],
            onClose: function () {
            },
            closable: true,
            closed: true   //已关闭
        });
        $('#resetPasswordDialog').dialog('open');
        $('.password-input').textbox().type = "text";
        $('.password-input').textbox('clear');
    }

    //更新密码
    function updatePassword() {
        var userId = $('#userId').val().replace(/(^\s*)|(\s*$)/g, '');
        var oldPassword = $('#oldPassword').val().replace(/(^\s*)|(\s*$)/g, '');
        var newPassword = $('#newPassword').val().replace(/(^\s*)|(\s*$)/g, '');
        var newPassword2 = $('#newPassword2').val().replace(/(^\s*)|(\s*$)/g, '');
        if (null == oldPassword || '' == oldPassword || null == newPassword || '' == newPassword || null == newPassword2 || '' == newPassword2) {
            layer.alert("请输入必填项！", {icon: 1, title: "提示"});
            return false;
        }
        if (null != newPassword && '' != newPassword && null != newPassword2 && '' != newPassword2 && newPassword != newPassword2) {
            layer.alert("两次输入的新密码不一致！", {icon: 1, title: "提示"});
            return false;
        }

        $.ajax({
            url: '/login/updatePassword',
            data: {
                userId: userId,
                oldPassword: generateMixed(4) + generateNum(2) + $.base64Encode(oldPassword),
                newPassword: generateMixed(4) + generateNum(2) + $.base64Encode(newPassword),
                newPassword2: generateMixed(4) + generateNum(2) + $.base64Encode(newPassword2)
            },
            type: 'POST',
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.code == '200') {
                    layer.alert(data.message, {
                        closeBtn: 0    // 是否显示关闭按钮
                        , anim: 1 //动画类型
                        , btn: ['确定'] //按钮
                        , icon: 6    // icon
                        , yes: function () {
                            //密码更新完成后重新登录
                            window.location.href = "/login/logout";
                        }
                    });

                } else {
                    layer.alert(data.message, {icon: 5, title: "提示"});
                }
            }
        });
    }


    //失去焦点时，将input类型设置为password
    function easyuitextfocus(obj) {
        console.log("focus");
        obj[0].type = 'password';
    }

    //input清空时，将input类型设置为text
    function easyuitextblur(obj) {
        var val = obj[0].value;
        var aa = val.toString().substring(0, 1);
        if (val == '' || aa == '请') {
            obj[0].type = 'text';
        }
    }

    //密码随机字符
    var chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

    function generateMixed(n) {
        var res = "";
        for (var i = 0; i < n; i++) {
            var id = Math.ceil(Math.random() * 35);
            res += chars[id];
        }
        return res;
    }

    function generateNum(n) {
        var res = "";
        for (var i = 0; i < n; i++) {
            var id = Math.ceil(Math.random() % 10);
            res += id;
        }
        return res;
    }

</script>



