layui.use(['form','layer'], function () {
    var form = layui.form;
    var $ = layui.jquery,
        layer = layui.layer;
});

function modify() {

    var username = $("#username2").val();
    var password = $("#password2").val();

    if (username.length == 0) {
        layer.tips("请输入用户名", '#username2', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#username2").focus();
        return;
    }

    if (password.length == 0) {
        layer.tips("请输入修改后的密码", '#password2', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#password2").focus();
        return;
    }

    if (password.length > 20 || password.length < 3) {
        layer.tips("请输入合法密码", '#password', {
            tips: [1, "black"],
            tipsMore: !1,
            time: 1300
        });
        $("#password2").focus();
        return;
    }

    $("#submit2").addClass("layui-btn-disabled");
    $("#submit2").attr("disabled", false);
    var object = new Object();
    object["username"] = username;
    object["password"] = password;
    var jsonData = JSON.stringify(object);

    $.ajax({
        url: basePath + "/admin/modify",
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
            if (data.status == 200) { // 修改成功
                layer.msg(data.message, {
                    time: 1000,
                    icon: 1,
                    offset: '100px'
                }, function () {
                });
            } else if (data.status == 201) { // 修改失败
                layer.msg(data.message, {
                    time: 1000,
                    icon: 5,
                    offset: '100px'
                }, function () {
                });
            }
            $("#submit2").removeClass("layui-btn-disabled");
            $("#submit2").attr("disabled", false);
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