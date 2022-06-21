package com.hua.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hua.entity.Blog;
import com.hua.entity.User;
import com.hua.service.admin.BlogService;
import com.hua.service.admin.TagService;
import com.hua.service.admin.TypeService;
import com.hua.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BlogAdminController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


//    @GetMapping("/blogs")
//    public String blogs() {
//
//        return "admin/blogs";
//    }

    @GetMapping("/blogs")
    public String toBlogs(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "5") int pageSize,

                          Model model) {

        PageHelper.startPage(pageNum, pageSize);
        //传递一个空对象即可
        BlogQuery blog = new BlogQuery();
        List<Blog> lists = blogService.queryAllBlogs(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(lists);
        model.addAttribute("blogs", blogPageInfo);
        model.addAttribute("types", typeService.queryAllTypes());

        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "5") int pageSize,
                         BlogQuery blog,
                         Model model) {

        PageHelper.startPage(pageNum, pageSize);
        // 根据BlogQuery对象的属性来进行查询
        List<Blog> lists = blogService.queryAllBlogs(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(lists);
        model.addAttribute("blogs", blogPageInfo);
        model.addAttribute("types", typeService.queryAllTypes());

        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.queryAllTypes());
        model.addAttribute("tags", tagService.queryAllTags());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/update")
    public String editBlog(@PathVariable long id, Model model) {
        model.addAttribute("types", typeService.queryAllTypes());
        model.addAttribute("tags", tagService.queryAllTags());
        Blog blog = blogService.queryBlogById(id);
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs/save")
    public String save(Blog blog, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        // 获取当前用户
        if (user != null){
            blog.setUser(user);
            blog.setType(typeService.queryTypeById(blog.getType().getId()));
            blog.setTags(tagService.queryAllTags(blog.getTagIds()));
            int save = blogService.saveBlog(blog);
            if (save > 0) {
                attributes.addFlashAttribute("msg", "操作成功！");
            } else {
                attributes.addFlashAttribute("errormsg", "操作失败！");
            }
            return "redirect:/admin/blogs";
        } else {
            return "redirect:/admin/index";
        }
    }

    @PostMapping("/updateBlogs")
    public String updateBlogs(Blog blog, HttpSession session, RedirectAttributes attributes) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.queryTypeById(blog.getType().getId()));
        blog.setTags(tagService.queryAllTags(blog.getTagIds()));
        int update = blogService.updateBlog(blog);
        if (update > 0) {
            attributes.addFlashAttribute("msg", "操作成功！");

        } else {
            attributes.addFlashAttribute("errormsg", "操作失败！");
        }

        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        int i = blogService.deleteBlog(id);
        if (i > 0) {
            redirectAttributes.addFlashAttribute("msg", "删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "删除失败！");
        }
        return "redirect:/admin/blogs";
    }

    @PostMapping(value = "/uploadfile")
    @ResponseBody
    public Map<String, Object> demo(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        //保存
        try {

            File imageFolder= new File(request.getServletContext().getRealPath("img/upload/blog/"));

            File targetFile = new File(imageFolder,file.getOriginalFilename());
            if(!targetFile.getParentFile().exists()){
                targetFile.mkdirs();
            }
            // 将文件写入targetFile中
            file.transferTo(targetFile);

            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url","/img/upload/blog/"+file.getOriginalFilename());
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        return resultMap;
    }

}
