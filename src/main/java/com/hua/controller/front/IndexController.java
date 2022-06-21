package com.hua.controller.front;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hua.entity.Blog;
import com.hua.entity.Tag;
import com.hua.entity.Type;
import com.hua.mapper.BlogTagMapper;
import com.hua.service.CommentService;
import com.hua.service.admin.BlogService;
import com.hua.service.admin.LinkService;
import com.hua.service.admin.TagService;
import com.hua.service.admin.TypeService;
import com.hua.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LinkService linkService;

    @GetMapping({"/home"})
    public String home(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "6") int pageSize,
                        BlogQuery blog,
                        Model model) {

        PageHelper.startPage(pageNum, pageSize);
        List<Blog> lists = blogService.queryAllBlogsOnFront(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(lists);
        model.addAttribute("comments", commentService.listAllComments());
        model.addAttribute("hotBlogs", blogService.queryPopBlogsOnFront(blog));
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "home";
    }

    @GetMapping({"/blog", "/"})
    public String blog(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize,
                       BlogQuery blog,
                       Model model) {

        PageHelper.startPage(pageNum, pageSize);
        List<Blog> lists = blogService.queryAllBlogsOnFront(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(lists);
        model.addAttribute("blogs", blogPageInfo);
        model.addAttribute("tags", tagService.queryAllTags());
        model.addAttribute("comments", commentService.listAllComments());
        model.addAttribute("hotBlogs", blogService.queryPopBlogsOnFront(blog));
        model.addAttribute("utdBlogs", blogService.queryUtdBlogsOnFront(blog));
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "index";
        //return "preload";
    }

    @GetMapping({"/preload"})
    public String preload() {
        return "preload";
    }

    @GetMapping("/category")
    public String toCategory(@RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "3") int pageSize,
                             BlogQuery blog,
                             Model model) {
        List<Type> types = typeService.queryAllTypes();
        Type type = types.get(0);
        blog.setTypeId(type.getId());

        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogService.queryAllBlogsOnFront(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);

        model.addAttribute("types", types);
        model.addAttribute("blogs", blogPageInfo);
        model.addAttribute("typeId", type.getId());
        model.addAttribute("hotBlogs", blogService.queryPopBlogsOnFront(blog));
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "category";

    }

    @GetMapping("/category/{id}")
    public String category(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "3") int pageSize,
                           @PathVariable long id,
                           Model model) {
        BlogQuery blog = new BlogQuery();
        blog.setTypeId(id);

        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogService.queryAllBlogsOnFront(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);

        List<Type> types = typeService.queryAllTypes();
        model.addAttribute("types", types);

        model.addAttribute("blogs", blogPageInfo);
        model.addAttribute("typeId", id);
        model.addAttribute("typeId1", id);
        model.addAttribute("hotBlogs", blogService.queryPopBlogsOnFront(blog));
        return "category :: blogList";
    }

    @GetMapping("/tag")
    public String toTag(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "3") int pageSize,
                        BlogQuery blog,
                        Model model) {

        List<Tag> tags = tagService.queryAllTags();
        Tag tag = tags.get(0);
        blog.setTagId(tag.getId());

        PageHelper.startPage(pageNum, pageSize);
        List<BlogQuery> blogs = blogService.queryAllBlogsByTagIdOnFront();
        PageInfo<BlogQuery> blogPageInfo = new PageInfo<>(blogs);

        model.addAttribute("tags", tags);
        model.addAttribute("blogs", blogPageInfo);
        model.addAttribute("tagId", tag.getId());
        model.addAttribute("hotBlogs", blogService.queryPopBlogsOnFront(blog));
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "tag";
    }

    @GetMapping("/tag/{id}")
    public String tag(@RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "3") int pageSize,
                           @PathVariable long id,
                           Model model) {
        BlogQuery blog = new BlogQuery();
        blog.setTagId(id);

        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogService.queryAllBlogsOnFront(blog);

        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        List<Tag> tags = tagService.queryAllTags();

        model.addAttribute("tags", tags);
        model.addAttribute("blogs", blogPageInfo);
        model.addAttribute("tagId", id);
        model.addAttribute("tagId1", id);
        model.addAttribute("hotBlogs", blogService.queryPopBlogsOnFront(blog));
        return "tag :: blogList";
    }

    @GetMapping("/archive")
    public String archive(
//            @RequestParam(defaultValue = "1") int pageNum,
//                          @RequestParam(defaultValue = "100") int pageSize,
                          BlogQuery blogQuery,
                          Model model) {

//        PageHelper.startPage(pageNum, pageSize);
        List<BlogQuery> blogs = blogService.queryAllBlogs();
        PageInfo<BlogQuery> blogQueryPageInfo = new PageInfo<>(blogs);

        model.addAttribute("blogs", blogQueryPageInfo);
        model.addAttribute("archiveMap", blogService.archiveBlogs());
        model.addAttribute("blog_count", blogService.queryTotalBlogs());
        model.addAttribute("links", linkService.queryAllLinksOnFront()); //归档重复年份 只有一篇博客
        return "archive";
    }

    @GetMapping("/music")
    public String music(Model model) {
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "music";
    }

    @GetMapping("/diary")
    public String diary(Model model) {
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "diary";
    }

//    @GetMapping("/message")
//    public String message(Model model) {
//        //model.addAttribute("comments", commentService.listAllComments());
//        model.addAttribute("links", linkService.queryAllLinksOnFront());
//        return "message";
//    }

    @GetMapping("/video")
    public String video(Model model) {
        //model.addAttribute("comments", commentService.listAllComments());
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "video";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "link";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "about";
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "5") int pageSize,
                         @RequestParam("keyword") String keyword,
                         Model model) {
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTitle(keyword);
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogService.queryAllBlogsOnFront(blogQuery);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogs", pageInfo);
        model.addAttribute("keyword", keyword);
        model.addAttribute("hotBlogs", blogService.queryPopBlogsOnFront(new BlogQuery()));
        model.addAttribute("links", linkService.queryAllLinksOnFront());
        return "search";
    }
}
