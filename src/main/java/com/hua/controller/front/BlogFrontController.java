package com.hua.controller.front;

import com.hua.entity.Blog;
import com.hua.entity.User;
import com.hua.exception.BlogNotFoundException;
import com.hua.mapper.BlogTagMapper;
import com.hua.service.admin.BlogService;
import com.hua.service.admin.LinkService;
import com.hua.service.admin.TagService;
import com.hua.util.StatusCode;
import com.hua.vo.TipsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class BlogFrontController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private LinkService linkService;

    @GetMapping({"/blog/{id}"})
    public String blogDetail(
            @PathVariable long id,
            Model model) {

        Blog blog = blogService.queryBlogOnFront(id);
        if (blog == null){
            throw new BlogNotFoundException("很抱歉，这篇博客不存在~");
        }
        blogService.incView(id);
        model.addAttribute("blog", blog);
        model.addAttribute("tags", tagService.queryAllTags(blogTagMapper.queryBlogTagByBlogId(id)));
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "blog";
    }

    @PostMapping("/log")
    @ResponseBody
    public TipsVo log(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new TipsVo(false, StatusCode.NOLOGED, "未登录");
        }
        else {
            session.removeAttribute("user");
            return new TipsVo(false, StatusCode.LOGOFF, "注销成功");
        }
    }
}
