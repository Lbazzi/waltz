window.onload = function () {
    NProgress.done();
};
$(function () {
    document.onkeydown = keyDownSearch;
    function keyDownSearch(e) {
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            $('#commentpost-btn').click();
            return false;
        }
        return true;
    }
});

$("#like-btn").click(function () {
    $.ajax(/*[[@{/clickLike/{id}(id=${blog.id})}]]*/);
    $("#like-count").text(parseInt($('#like-count').text()) + 1);
});


$('#appreciation-btn').click(function () {
    $('.ui.modal')
        .modal('show')
    ;
})


$("#menu-btn").click(function () {
    $(".menu-item").toggleClass("mobile-hide")
});

// 目录弹窗
$('#toc-btn').popup({
    popup: $('.toc-container.popup'),
    on: 'hover',
    position: 'left center'
});

tocbot.init({
    // Where to render the table of contents.
    tocSelector: '.js-toc',
    // Where to grab the headings to build the table of contents.
    contentSelector: '.js-toc-content',
    // Which headings to grab inside of the contentSelector element.
    headingSelector: 'h1, h2, h3',
    // For headings inside relative or absolute positioned containers within content.
    hasInnerContainers: true,
});

// 二维码弹窗
$('.wechat').popup({
    popup: $('#qrcode.popup'),
    position: 'left center'
});

$(function () {
    $("#comment-container").load(/*[[@{/comments/{id}(id=${blog.id})}]]*/"@{/comments/{id}(id=${blog.id})}");
});

//评论表单验证
$('.ui.form').form({
    fields: {
        title: {
            identifier: 'content',
            rules: [{
                type: 'empty',
                prompt: '请输入评论内容'
            }
            ]
        },
        content: {
            identifier: 'nickname',
            rules: [{
                type: 'empty',
                prompt: '请输入你的昵称'
            }]
        },
        type: {
            identifier: 'email',
            rules: [{
                type: 'email',
                prompt: '请填写正确的邮箱地址'
            }]
        }
    }
});

$('#commentpost-btn').click(function () {

    var boo = $('.ui.form').form('validate form');
    // HttpSession session = request.getSession();
    // var user = session.getAttribute("user");
    // var user = "{:session('user')}";
    // session = requests.session();
    // var user = sessionStorage.getItem('user');

    // if (!user) {
    //     alert("请先登录");
    //     location.href = basePath + "/admin";
    //     // console.log('校验失败')
    // }
    if (!$.session.get('user')) { // 未登录
        layer.msg("请先登录", {
            time: 1000,
            icon: 5,
            offset: '100px'
        }, function () {
            location.href = basePath + "/admin";
        });
    }

    // if (boo && user) {
    //     postData();
    //     console.log(user);
    // } else {
    //     console.log('校验失败')
    // }
    if (boo && $.session.get('user')) {
        layer.msg("发布成功", {
            time: 1000,
            icon: 1,
            offset: '100px'
        }, function () {
            postData();
            console.log("校验成功");
            // $.session.remove('user');
            // $.session.clear();
        });
    } else {
        console.log("校验失败")
    }
});

// 提交评论数据
function postData() {
    $body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');// 这行是 Opera 的补丁, 少了它 Opera 是直接用跳的而且画面闪烁 by willin

    $("#comment-container").load(/*[[@{/comments}]]*/"/comments", {
        "parentComment.id": $("[name='parentComment.id']").val(),
        "blog.id": $("[name='blog.id']").val(),
        "nickname": $("[name='nickname']").val(),
        "email": $("[name='email']").val(),
        "content": $("[name='content']").val()
    }, function (response, status, xhr) {
        // swal("操作", "评论成功", "success");
        $body.animate({scrollTo: $('#comment-container').offset().top}, 500);
        clearContent();
    });
}

function clearContent() {
    $("[name='content']").val('');
    $("[name='parentComment.id']").val(-1);
    $("[name='content']").attr("placeholder", "请输入评论信息...");
}

function reply(obj) {
    var commentId = $(obj).data('commentid');
    var commentNickname = $(obj).data('commentnickname');
    $("[name='content']").attr("placeholder", "@" + commentNickname).focus();
    $("[name='parentComment.id']").val(commentId);
}