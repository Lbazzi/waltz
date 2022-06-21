package com.hua.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hua.entity.Comment;
import com.hua.entity.Link;
import com.hua.service.CommentService;
import com.hua.vo.CommentQuery;
import com.hua.vo.LinkQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comment")
    public String toComment(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "5") int pageSize,
                            Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentService.listAdminComments();
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        model.addAttribute("comments", commentPageInfo);
        return "admin/comments";
    }

    @GetMapping("/comment/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        int i = commentService.deleteComments(id);
        if (i > 0) {
            redirectAttributes.addFlashAttribute("msg", "删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "删除失败！");
        }
        return "redirect:/admin/comment";
    }
}
