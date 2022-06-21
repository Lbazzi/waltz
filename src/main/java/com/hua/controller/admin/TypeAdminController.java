package com.hua.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hua.entity.Link;
import com.hua.entity.Type;
import com.hua.service.admin.TypeService;
import com.hua.vo.LinkQuery;
import com.hua.vo.TypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class TypeAdminController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String toTypes(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "5") int pageSize,
                          Model model) {
        PageHelper.startPage(pageNum, pageSize);
        TypeQuery type = new TypeQuery();
        List<Type> list = typeService.queryAllTypes(type);
        PageInfo<Type> typePageInfo = new PageInfo<>(list);

        model.addAttribute("page", typePageInfo);
        model.addAttribute("types", typeService.queryAllTypes());
        return "admin/types";
    }

    @PostMapping("/types/search")
    public String search(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "5") int pageSize,
                         TypeQuery type,
                         Model model) {

        PageHelper.startPage(pageNum, pageSize);
        List<Type> types = typeService.queryAllTypes(type);
        PageInfo<Type> typePageInfo = new PageInfo<>(types);
        model.addAttribute("page", typePageInfo);
        model.addAttribute("types", typeService.queryAllTypes());
        return "admin/types :: typeList";
    }

    @PostMapping("/saveType")
    public String save(Type type, RedirectAttributes redirectAttributes) {
        Type t = typeService.queryTypeByName(type.getName());
        if (t != null) {
            redirectAttributes.addFlashAttribute("msg", "分类已存在");
            return "redirect:/admin/types/input";
        }

        if (type == null) {
            redirectAttributes.addFlashAttribute("errormsg", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作成功");
        }

        int save = typeService.saveType(type);
        if (save > 0) {
            System.out.println("save success!!!!");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/updateTypes/{id}")
    public String updateTypes(Type type, RedirectAttributes redirectAttributes) {
//        Type t = typeService.getTypeByName((String) param.get("name"));
//        if (t != null){
//            redirectAttributes.addFlashAttribute("msg","不能存在相同的分类");
//            return "redirect:/admin/types/input";
//        }

        if (type == null) {
            redirectAttributes.addFlashAttribute("errormsg", "更新失败！");
        } else {
            redirectAttributes.addFlashAttribute("msg", "更新成功！");
        }


        int update = typeService.updateType(type);
        if (update > 0) {
            System.out.println("save success!!!!");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/input")
    public String toInput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/update")
    public String toUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.queryTypeById(id));
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        int i = typeService.deleteType(id);
        if (i > 0) {
            redirectAttributes.addFlashAttribute("msg", "删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "删除失败！");
        }
        return "redirect:/admin/types";
    }

}
