
$(document).ready(function() {
    $("#loginName").focus();
    //loginInit();//登录时初始化提示DIV为隐藏

    //回车登录
    $('input').keyup(function(e) {
        if(e.keyCode == '13') {
            login();
        }
    })
});

/**
 * 登录function
 */
function login(){
    //loginInit();
    $('#subButton').hide();
    if(validateLoginNameAndPwd()){//前台数据校验
        vilidateLogin();
    }

}
/**
 * 登录后台数据验证
 */
function vilidateLogin(){
    $('#errormsg').hide();
    $('#subButton').hide();
    var loginName = $('#loginName').val();
    loginName = loginName.replace(/(^\s*)|(\s*$)/g,'');
    var password = $('#password').val();
    password = password.replace(/(^\s*)|(\s*$)/g,'');
    $.ajax({
        url : '/login/validateLogin',
        //data:'loginName='+encodeURIComponent($.base64Encode(loginName))+'&pwd='+encodeURIComponent($.base64Encode(pwd)),
        data: {
            loginName: generateMixed(4)+generateNum(2)+$.base64Encode(loginName),
            password:generateMixed(4)+generateNum(2)+$.base64Encode(password),
            //isProd: getUrlParam('isProd'),
            systemAlias:$("#systemAlias").val()
        },
        type : 'POST',
        cache:false,
        dataType : 'json',
        success : function(data) {
            //loginInit();
            if(data.code == 200) {
                $('#loginName_enc').val(generateMixed(4)+generateNum(2)+$.base64Encode(loginName));
                $('#pwd_enc').val(generateMixed(4)+generateNum(2)+$.base64Encode(password));
                document.getElementById("loginForm").submit();
            } else{
                $('#subButton').show();
                // -1：帐号不存在、0：密码错误：1：成功、-2：人员记录不存在、-3：多条记录
                var $show = $('#pwdDiv').is(':visible') ? $('#pwdTip') : $('#loginNameTip');
                var msg;
                debugger;
                if(data.data == '-1') {
                    msg = '用户帐号不存在或已离职';
                } else if(data.data == '0') {
                    msg = '用户密码不正确';
                } else if(data.data == '-2') {
                    msg = '账号不存在或密码错误';
                } else if(data.data == '-3') {
                    msg = '人员信息重复';
                }else {
                    msg = '用户名不存在或密码不正确';
                }
                oldPwdCheckResult = false;
                $('#errormsg').html('<font color="red">' + msg +'</font>');
                $('#errormsg').show();
            }
        }
    });
}
/**
 * 前台数据校验
 * @returns {Boolean}
 */
function validateLoginNameAndPwd(){
    var ret = true;
    var loginName = $('#loginName').val();
    loginName = loginName.replace(/(^\s*)|(\s*$)/g,'');

    if(loginName==''||loginName==null){
        $('#errormsg').html('<font color="red">请输入用户名</font>');
        $('#errormsg').show();
        $("#loginName").focus();
        $('#subButton').show();//显示登录按钮
        return false;
    }
    if($('#pwdDiv').is(':visible')) {
        var pwd = $('#password').val();
        pwd = pwd.replace(/(^\s*)|(\s*$)/g, '');
        if (pwd == '' || pwd == null) {
            $('#errormsg').html('<font color="red">请输入密码</font>');
            $('#errormsg').show();
            $("#password").focus();
            $('#subButton').show();//显示登录按钮
            return false;
        }
    }
    return ret;
}
/**
 * 登录账号输入框失去焦点是验证
 */
function onblurLoginName(){
    var loginName = $('#loginName').val();
    loginName = loginName.replace(/(^\s*)|(\s*$)/g,'');
    if(loginName==''||loginName==null){
        $('#errormsg').html('<font color="red">请输入用户名</font>');
        $('#errormsg').show();
        // $("#loginName").focus();
    }else{
        $('#errormsg').hide();
    }
}
/**
 * 登录密码输入框失去焦点是验证
 */
function onblurPwd(){
    var password = $('#password').val();
    password = password.replace(/(^\s*)|(\s*$)/g,'');
    if(password==''||password==null){
        $('#errormsg').html('<font color="red">请输入密码</font>');
        $('#errormsg').show();
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

var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
}

function generateNum(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()%10);
        res += id;
    }
    return res;
}
//修改密码
function editPassword(){
    window.location.href='/login/goEditPassword';
}
//重置密码
function resetPassword() {
    var loginName = $('#loginName').val();
    loginName = loginName.replace(/(^\s*)|(\s*$)/g,'');
    if(loginName==''||loginName==null){
        $('#errormsg').html('<font color="red">请输入用户名,才能重置密码</font>');
        $('#errormsg').show();
        $("#loginName").focus();
    }else{
        layer.open({
            type: 0,
            title: false,
            closeBtn: 0,
            btn: false,
            content: '<div class="executeprogress">正在处理，请耐心稍等... ...</div>',
        });
        $.ajax({
            url : '/le/resetPassword',
            data: {
                loginName: generateMixed(4)+generateNum(2)+$.base64Encode(loginName),
                systemAlias:$("#systemAlias").val()
            },
            type : 'POST',
            cache:false,
            dataType : 'json',
            success : function(data) {
                if (data.code == 200){
                    layer.confirm('重置成功！新密码以邮件的方式送至您的公司邮箱，请查收！', {icon: 1,btn:['确认']});
                }else if (data.code == 422){
                    layer.confirm(data.msg, {icon: 2,btn:['确认']});
                }else {
                    layer.confirm(data.msg, {icon: 2,btn:['确认']});
                }
            }
        });
    }
}