layui.use(['form','layer'], function () {
    var form = layui.form;
    var $ = layui.jquery,
        layer = layui.layer;
});

var verifyMail =  /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/; //正则表达式验证邮箱

function f() {

    var nickname= $("#nickname").val();
    var username= $("#username1").val();
    var email= $("#email").val();
    var password = $("#password1").val();

    if (nickname.length == 0) {
        layer.tips("请输入昵称", '#nickname', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#nickname").focus();
        return;
    }

    if (username.length == 0) {
        layer.tips("请输入用户名", '#username1', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#username1").focus();
        return;
    }

    if (email.length == 0) {
        layer.tips("请输入邮箱", '#email', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#email").focus();
        return;
    }

    if (password.length == 0) {
        layer.tips("请输入密码", '#password1', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#password1").focus();
        return;
    }

    if (password.length > 20 || password.length < 3) {
        layer.tips("密码长度为：3-20", '#password1', {
            tips: [1, "red"],
            tipsMore: !1,
            time: 1300
        });
        $("#password1").focus();
        return;
    }

    if(!verifyMail.test(email)){
        layer.tips("请输入正确的QQ邮箱", '#email', {
            tips: [1, "#FF5722"],
            tipsMore: !1,
            time: 1300
        });
        $("#email").focus();
        return ;
    }

    $("#submit1").addClass("layui-btn-disabled");
    $("#submit1").attr("disabled", false);
    var object = new Object();
    object["nickname"] = nickname;
    object["username"] = username;
    object["email"] = email;
    object["password"] = password;
    var jsonData = JSON.stringify(object);

    $.ajax({
        url: basePath + "/admin/register",
        data: jsonData,
        // data: $('#register').serialize(),
        contentType: "application/json; charset = UTF-8",
        type: "post",
        dataType: "json",

        beforeSend: function () {
            layer.load(1, {
                content: 'loading...',
                success: function (layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '60px'
                    });
                }
            });
        },
        complete: function () {
            layer.closeAll('loading');
        },
        success: function (data) {
            if (data.status == 200) {
                layer.msg(data.message, {
                    time: 1000,
                    icon: 1,
                    offset: '100px'
                }, function () {
                    $.session.set('user', object);
                    // location.href = basePath + "/admin";
                });
            } else {
                layer.msg(data.message, {
                    time: 1000,
                    icon: 5,
                    offset: '100px'
                });
                $("#submit1").removeClass("layui-btn-disabled");
                $("#submit1").attr("disabled", false);
            }
        },error:function () {
            layer.msg("系统错误", {
                time: 1000,
                icon: 2,
                offset: '100px'
            });
        }
    });
    return false;
}
