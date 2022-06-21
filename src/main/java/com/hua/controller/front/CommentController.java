package com.hua.controller.front;

import com.hua.entity.Comment;
import com.hua.entity.User;
import com.hua.service.CommentService;
import com.hua.service.admin.BlogService;
import com.hua.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId, Model model) {

        model.addAttribute("comments", commentService.listComments(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session, RedirectAttributes redirectAttributes) {
        Long id = comment.getBlog().getId() ;
        String email = comment.getEmail();

        comment.setBlog(blogService.queryBlogById(id));

        User user = (User) session.getAttribute("user");
        if ((user != null) && (user.isType())) { // 管理员评论
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else if ((user != null) && (user.isType() == false)) {
            comment.setAvatar("http://q1.qlogo.cn/g?b=qq&nk=" + StringUtils.getQQ(comment.getEmail()) + "&s=100");
//            comment.setAvatar(user.getAvatar());
        }

        commentService.saveComments(comment);

        return "redirect:/comments/" + id; // 重定向到上面的comment方法，返回评论列表给前端页面
    }
}










