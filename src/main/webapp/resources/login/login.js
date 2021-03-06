$(function () {
    $("#loginName").focus();
    //回车登录
    document.onkeydown = function (e) {
        if (e.keyCode == '13') {
            login();
        }
    };
});

/**
 * 登录function
 */
function login() {
    $('#subButton').hide();
    if (validateLoginNameAndPwd()) {//前台数据校验
        validateLogin(); //后台数据校验
    }

}

/**
 * 登录后台数据验证
 */
function validateLogin() {
    $('#errorMsg').hide();
    var loginName = $('#loginName').val().replace(/(^\s*)|(\s*$)/g, '');
    var password = $('#password').val().replace(/(^\s*)|(\s*$)/g, '');
    $.ajax({
        url: '/login/validateLogin',
        data: {
            loginName: generateMixed(4) + generateNum(2) + $.base64Encode(loginName),
            password: generateMixed(4) + generateNum(2) + $.base64Encode(password)
        },
        type: 'POST',
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.code == '200') {
                $('#loginName_enc').val(generateMixed(4) + generateNum(2) + $.base64Encode(loginName));
                $('#password_enc').val(generateMixed(4) + generateNum(2) + $.base64Encode(password));
                $("#loginForm").submit();
            } else {
                $('#subButton').show();
                $('#errorMsg').html('<font color="red">' + data.message + '</font>');
                $('#errorMsg').show();
            }
        }
    });
}

/**
 * 前台数据校验
 * @returns {Boolean}
 */
function validateLoginNameAndPwd() {
    var result = true;
    var loginName = $('#loginName').val().replace(/(^\s*)|(\s*$)/g, '');
    if (loginName == '' || loginName == null) {
        $('#errorMsg').html('<font color="red">请输入用户名</font>');
        $('#errorMsg').show();
        $("#loginName").focus();
        $('#subButton').show();//显示登录按钮
        return false;
    }

    var pwd = $('#password').val().replace(/(^\s*)|(\s*$)/g, '');
    if (pwd == '' || pwd == null) {
        $('#errorMsg').html('<font color="red">请输入密码</font>');
        $('#errorMsg').show();
        $("#password").focus();
        $('#subButton').show();//显示登录按钮
        return false;
    }
    return result;
}

/**
 * 登录账号输入框失去焦点是验证
 */
function onblurLoginName() {
    var loginName = $('#loginName').val().replace(/(^\s*)|(\s*$)/g, '');
    if (loginName == '' || loginName == null) {
        $('#errorMsg').html('<font color="red">请输入用户名</font>');
        $('#errorMsg').show();
        // $("#loginName").focus();
    } else {
        $('#errorMsg').hide();
    }
}

/**
 * 登录密码输入框失去焦点是验证
 */
function onblurPwd() {
    var password = $('#password').val().replace(/(^\s*)|(\s*$)/g, '');
    if (password == '' || password == null) {
        $('#errorMsg').html('<font color="red">请输入密码</font>');
        $('#errorMsg').show();
        //IE中会出现循环focus
        //$("#pwd").focus();
        $('#subButton').show();//显示登录按钮
    }
}

//获取url中的参数
function getUrlParam(name) {
    try {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r) return r[2]; //返回参数值
    } catch (e) {
        console.log('getUrlParam_error:' + e);
    }

}

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
        res += Math.ceil(Math.random() % 10);
    }
    return res;
}

//修改密码
function editPassword() {
    window.location.href = '/login/goEditPassword';
}

//重置密码
function resetPassword() {
    var loginName = $('#loginName').val();
    loginName = loginName.replace(/(^\s*)|(\s*$)/g, '');
    if (loginName == '' || loginName == null) {
        $('#errorMsg').html('<font color="red">请输入用户名,才能重置密码</font>');
        $('#errorMsg').show();
        $("#loginName").focus();
    } else {
        layer.open({
            type: 0,
            title: false,
            closeBtn: 0,
            btn: false,
            content: '<div class="executeprogress">正在处理，请耐心稍等... ...</div>'
        });
        $.ajax({
            url: '/le/resetPassword',
            data: {
                loginName: generateMixed(4) + generateNum(2) + $.base64Encode(loginName),
                systemAlias: $("#systemAlias").val()
            },
            type: 'POST',
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.code == 200) {
                    layer.confirm('重置成功！新密码以邮件的方式送至您的公司邮箱，请查收！', {icon: 1, btn: ['确认']});
                } else if (data.code == 422) {
                    layer.confirm(data.msg, {icon: 2, btn: ['确认']});
                } else {
                    layer.confirm(data.msg, {icon: 2, btn: ['确认']});
                }
            }
        });
    }
}