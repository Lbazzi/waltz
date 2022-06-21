layui.use(['form','layer'], function () {
    var form = layui.form;
    var $ = layui.jquery,
        layer = layui.layer;
});

function out() {

    $.ajax({
        url: basePath + "/log",
        // data: jsonData,
        // contentType: "application/json; charset = UTF-8",
        type: "post",
        // dataType: "json",

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
            if (data.status == 209) { // 用户未登录
                layer.msg(data.message, {
                    time: 1000,
                    icon: 5,
                    offset: '100px'
                }, function () {
                    location.href = basePath + "/admin";
                });
            }  else if (data.status == 207) { // 用户已注销
                layer.msg(data.message, {
                    time: 1000,
                    icon: 1,
                    offset: '100px'
                }, function () {
                    $.session.remove('user');
                    $.session.clear();
                    // location.href = basePath + "/admin";
                    location.reload();
                });
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