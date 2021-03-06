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
//            redirectAttributes.addFlashAttribute("msg", "???????????????????????????");
//            return "redirect:/admin/links/input";
//        }

        if (link == null) {
            redirectAttributes.addFlashAttribute("errormsg", "???????????????");
        } else {
            redirectAttributes.addFlashAttribute("msg", "???????????????");
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
            model.addAttribute("msg", "???????????????");
        } else {
            model.addAttribute("errormsg", "???????????????");
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
            redirectAttributes.addFlashAttribute("msg", "???????????????");
        } else {

            redirectAttributes.addFlashAttribute("errormsg", "???????????????");
        }
        return "redirect:/admin/links";
    }
}
