package com.hua.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hua.entity.Tag;
import com.hua.entity.Type;
import com.hua.service.admin.TagService;
import com.hua.vo.TagQuery;
import com.hua.vo.TypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagAdminController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String toTags(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "5") int pageSize,
                         Model model) {
        PageHelper.startPage(pageNum, pageSize);
        TagQuery tag = new TagQuery();
        List<Tag> list = tagService.queryAllTags(tag);
        PageInfo<Tag> tagPageInfo = new PageInfo<>(list);

        model.addAttribute("page", tagPageInfo);
        return "admin/tags";
    }

    @PostMapping("/tags/search")
    public String search(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "5") int pageSize,
                         TagQuery tag,
                         Model model) {

        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tags = tagService.queryAllTags(tag);
        PageInfo<Tag> tagPageInfo = new PageInfo<>(tags);
        model.addAttribute("page", tagPageInfo);
//        model.addAttribute("tags", tagService.queryAllTags());
        return "admin/tags :: tagList";
    }

    @PostMapping("/saveTag")
    public String save(Tag tag, RedirectAttributes redirectAttributes) {
        Tag t = tagService.queryTagByName(tag.getName());
        if (t != null) {
            redirectAttributes.addFlashAttribute("msg", "标签已存在");
            return "redirect:/admin/tags/input";
        }

        if (tag == null) {
            redirectAttributes.addFlashAttribute("errormsg", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作成功");
        }
        int save = tagService.saveTag(tag);
        if (save > 0) {
            System.out.println("save success!!!!");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/updateTags/{id}")
    public String updateTags(Tag tag, @PathVariable Long id, RedirectAttributes redirectAttributes) {
//        Tag t = tagService.getTagByName(tag.getName());
//        if (t != null){
//            redirectAttributes.addFlashAttribute("msg","不能存在相同的分类");
//            return "redirect:/admin/tags/input";
//        }

        if (tag == null) {
            redirectAttributes.addFlashAttribute("errormsg", "更新失败！");
        } else {
            redirectAttributes.addFlashAttribute("msg", "更新成功！");
        }
        int update = tagService.updateTag(tag);
        if (update > 0) {
            System.out.println("save success!!!!");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/input")
    public String toInput(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/update")
    public String toUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.queryTagById(id));
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        int i = tagService.deleteTag(id);
        if (i > 0) {
            redirectAttributes.addFlashAttribute("msg", "删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "删除失败！");
        }
        return "redirect:/admin/tags";
    }

}
