//如果iframe中的session过期，跳转至主页面
if (window != top) {
    top.location.href = location.href;
}

layui.use(['form','layer'], function () {
    var form = layui.form;
    var $ = layui.jquery,
        layer = layui.layer;
});

function check() {

    var username = $("#username").val();
    var password = $("#password").val();

    if (username.length == 0) {
        layer.tips("请输入用户名", '#username', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#username").focus();
        return;
    }

    if (password.length == 0) {
        layer.tips("请输入密码", '#password', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#password").focus();
        return;
    }

    if (password.length > 20 || password.length < 3) {
        layer.tips("请输入合法密码", '#password', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#password").focus();
        return;
    }

    $("#submit").addClass("layui-btn-disabled");
    $("#submit").attr("disabled", false);
    var object = new Object();
    object["username"] = username;
    object["password"] = password;
    var jsonData = JSON.stringify(object);

    $.ajax({
        url: basePath + "/admin/login",
        data: jsonData,
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
            if (data.status == 200) { // 管理员登录成功
                layer.msg(data.message, {
                    time: 1000,
                    icon: 1,
                    offset: '100px'
                }, function () {
                    $.session.set('user', object);
                    location.href = basePath + "/admin/index";
                });
            } else if (data.status == 203) { // 非管理员
                layer.msg(data.message, {
                    time: 1000,
                    icon: 5,
                    offset: '100px'
                }, function () {
                    // sessionStorage.setItem('user', String(object));
                    $.session.set('user', object);
                    location.href = basePath + "/blog";
                    // window.history.go(-1);
                });
            } else if (data.status == 202) { // 用户名或密码错误
                layer.msg(data.message, {
                    time: 1000,
                    icon: 5,
                    offset: '100px'
                }, function () {
                    // location.href = basePath + "/admin";
                });
                $("#submit").removeClass("layui-btn-disabled");
                $("#submit").attr("disabled", false);
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