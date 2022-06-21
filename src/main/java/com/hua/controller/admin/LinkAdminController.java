package com.hua.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hua.entity.Link;
import com.hua.entity.Tag;
import com.hua.service.admin.LinkService;
import com.hua.vo.LinkQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class LinkAdminController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/links")
    public String toLink(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "2") int pageSize,

                         Model model) {

        PageHelper.startPage(pageNum, pageSize);
        LinkQuery link = new LinkQuery();
        List<Link> links = linkService.queryAllLinks(link);
        PageInfo<Link> pageInfo = new PageInfo<>(links);
        model.addAttribute("linkslist", pageInfo);

        return "admin/links";
    }

    @GetMapping("/links/input")
    public String toInput(Model model) {
        model.addAttribute("link", new Link());
        return "admin/links-input";
    }

    @PostMapping("/links/search")
    public String search(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "2") int pageSize,
                         LinkQuery link,
                         Model model) {

        PageHelper.startPage(pageNum, pageSize);
        List<Link> links = linkService.queryAllLinks(link);
        PageInfo<Link> linkPageInfo = new PageInfo<>(links);
        model.addAttribute("linkslist", linkPageInfo);

        return "admin/links :: linkList";
    }

    @PostMapping("/saveLinks")
    public String save(Link link, RedirectAttributes redirectAttributes) {
//        Link l = linkService.queryLinkByName(link.getName());
//
//        if (l != null) {
//            redirectAttributes.addFlashAttribute("msg", "不能存在相同的友链");
//            return "redirect:/admin/links/input";
//        }

        if (link == null) {
            redirectAttributes.addFlashAttribute("errormsg", "操作失败！");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作成功！");
        }

        int save = linkService.saveLink(link);
        if (save > 0) {
            System.out.println("save success!!!!");
        }
        return "redirect:/admin/links";
    }

    @GetMapping("/links/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        int i = linkService.deleteLink(id);
        if (i > 0) {
            model.addAttribute("msg", "操作成功！");
        } else {
            model.addAttribute("errormsg", "操作失败！");
        }

        return "redirect:/admin/links";
    }

    @GetMapping("/links/{id}/update")
    public String toUpdateLink(@PathVariable int id, Model model) {
        model.addAttribute("link", linkService.queryLinkById(id));
        return "admin/links-input";
    }

    @PostMapping("/updateLink/{id}")
    public String updateLink(Link link, @PathVariable int id, RedirectAttributes redirectAttributes) {

        int update = linkService.updateLink(link);
        if (update > 0) {
            redirectAttributes.addFlashAttribute("msg", "更新成功！");
        } else {

            redirectAttributes.addFlashAttribute("errormsg", "更新失败！");
        }
        return "redirect:/admin/links";
    }
}
