var oldPwdCheckResult = true;

$(document).ready(function() {
    $("#loginName").focus();

});




//验证用户名是否为空
function checkLoginName(){
    var loginName = $('#loginName').val();
    loginName = loginName.replace(/(^\s*)|(\s*$)/g,'');
    if(loginName==''||loginName==null){
        $('#errorMsg').html('<font color="red">请输入用户名</font>');
        $('#errorMsg').show();
        // $("#errorMsg").focus();
        return false;
    }else{
        $('#errorMsg').hide();
        return true;
    }
}


//验证原密码是否正确
function checkOldPwd(){
    if(!checkLoginName){
        return;
    }
    var loginName = $('#loginName').val();
    loginName = loginName.replace(/(^\s*)|(\s*$)/g,'');
    var oldPwd = $('#oldPwd').val();
    oldPwd = oldPwd.replace(/(^\s*)|(\s*$)/g,'');
    if(oldPwd==''||oldPwd==null){
        $('#errorMsg').html('<font color="red">请输入原密码！</font>');
        $('#errorMsg').show();
        oldPwdCheckResult = false;
        return false;
    }else {
        $('#errorMsg').hide();
    }

    $.ajax({
        url : '/le/validateLogin',
        data: {
            loginName: generateMixed(4)+generateNum(2)+$.base64Encode(loginName),
            pwd:generateMixed(4)+generateNum(2)+$.base64Encode(oldPwd),
            //isProd: getUrlParam('isProd'),
            systemAlias:$("#systemAlias").val()
        },
        type : 'POST',
        cache:false,
        dataType : 'json',
        success : function(data) {
            //loginInit();
            if(data.isOk) {
                // $('#loginName_enc').val(generateMixed(4)+generateNum(2)+$.base64Encode(loginName));
                // $('#pwd_enc').val(generateMixed(4)+generateNum(2)+$.base64Encode(pwd));
                // document.getElementById("loginForm").submit();
                oldPwdCheckResult = true;
                $('#errorMsg').hide();
            } else{
                // $('#subButton').show();
                // -1：帐号不存在、0：密码错误：1：成功、-2：人员记录不存在、-3：多条记录
                var $show = $('#pwdDiv').is(':visible') ? $('#pwdTip') : $('#loginNameTip');
                var msg;
                if(data.data == '-1') {
                    msg = '用户帐号不存在或已离职';
                } else if(data.data == '0') {
                    msg = '用户原密码不正确';
                } else if(data.data == '-2') {
                    msg = '账号不存在或原密码错误';
                } else if(data.data == '-3') {
                    msg = '人员信息重复';
                }else {
                    msg = '用户名不存在或密码不正确';
                }
                oldPwdCheckResult = false;
                $('#errorMsg').html('<font color="red">' + msg +'</font>');
                $('#errorMsg').show();
            }
        }
    });
}


//提交修改密码
function submitEditPassword() {
    if (!checkLoginName()){
        return;
    }
    checkOldPwd();
    if (!oldPwdCheckResult){
        return;
    }

    var newPwd = $('#newPwd').val();
    newPwd = newPwd.replace(/(^\s*)|(\s*$)/g,'');

    var newPwd2 = $('#newPwd2').val();
    newPwd2 = newPwd2.replace(/(^\s*)|(\s*$)/g,'');

    if (newPwd==''|| newPwd==null){
        $('#errorMsg').html('<font color="red">请输新密码</font>');
        setTimeout(function () {
            $('#errorMsg').show();
            $("#newPwd").focus();
        },100)

        return;
    }else if (newPwd2==''|| newPwd2==null){
        $('#errorMsg').html('<font color="red">请输入确认密码</font>');
        setTimeout(function () {
            $('#errorMsg').show();
            $("#newPwd2").focus();
        },100)
        return;
    }else if (newPwd2 != newPwd){
        $('#errorMsg').html('<font color="red">两次输入的密码不一致</font>');
        setTimeout(function () {
            $('#errorMsg').show();
            $("#newPwd").focus();
        },100)
        return;
    }



    $('#subButton').hide();

    var loginName = $('#loginName').val();
    loginName = loginName.replace(/(^\s*)|(\s*$)/g,'');
    var newPwd = $('#newPwd').val();
    newPwd = newPwd.replace(/(^\s*)|(\s*$)/g,'');

    $.ajax({
        url : '/le/updatePassword',
        data: {
            loginName: generateMixed(4)+generateNum(2)+$.base64Encode(loginName),
            pwd:generateMixed(4)+generateNum(2)+$.base64Encode(newPwd),
            //isProd: getUrlParam('isProd'),
            systemAlias:$("#systemAlias").val()
        },
        type : 'POST',
        cache:false,
        dataType : 'json',
        success : function(data) {
            $('#subButton').show();
            if (data.code == 200) {
                layer.confirm('修改成功，请使用新密码登录', {icon: 1,btn:['确认']},function () {
                        window.location.href = '/hr/goHr';
                    }
                );
            }else if (data.code == 422){
                $('#errorMsg').html('<font color="red">请输入一致的新密码</font>');
                $('#errorMsg').show();
                $("#newPwd").focus();
            }else {
                $('#errorMsg').html('<font color="red">系统异常，请联系技术人员</font>');
                $('#errorMsg').show();
            }
        }
    });

}
//取消修改密码
function cancelEditPassword() {
    $("#loginName").unbind('focus');
    window.location.href='/hr/goHr';
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

//验证新密码是否为空
// function checkNewPwd(){
//     if (!oldPwdCheckResult){
//         $("#oldPwd").focus();
//         return false;
//     }
//     var newPwd = $('#newPwd').val();
//     newPwd = newPwd.replace(/(^\s*)|(\s*$)/g,'');
//     if(newPwd==''||newPwd==null){
//         $('#errorMsg').html('<i class="icon icon_error"></i><font color="red">请输入新密码</font>');
//         $('#errorMsg').show();
//         // $("#newPwd").focus();
//         return false;
//     }
// }

//验证新密码和老密码是否一致
// function checkNew2Pwd(){
//
//     if (!oldPwdCheckResult){
//         $("#loginName").focus();
//         return false;
//     }
//     var newPwd = $('#newPwd').val();
//     newPwd = newPwd.replace(/(^\s*)|(\s*$)/g,'');
//
//     var newPwd2 = $('#newPwd2').val();
//     newPwd2 = newPwd2.replace(/(^\s*)|(\s*$)/g,'');
//
//     if ((newPwd==''|| newPwd==null) || (newPwd2==''|| newPwd2==null)  || (newPwd != newPwd2)){
//         $('#errorMsg').html('<font color="red">两次输入的密码不一致</font>');
//         $('#errorMsg').show();
//         // $("#newPwd").focus();
//         return false;
//     }
// }

