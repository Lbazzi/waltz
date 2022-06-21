package com.hua.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hua.entity.Blog;
import com.hua.entity.User;
import com.hua.service.admin.UserService;
import com.hua.vo.BlogQuery;
import com.hua.vo.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String toUsers(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "2") int pageSize,
                          Model model) {
        PageHelper.startPage(pageNum, pageSize);
        UserQuery user = new UserQuery();
        //        List<User> usersList = userService.queryAllUsers();
        List<User> usersList = userService.queryAllUsers(user);
        PageInfo<User> userPageInfo = new PageInfo<>(usersList);
        model.addAttribute("page", userPageInfo);
        return "admin/users";
    }

    @PostMapping("/users/search")
    public String search(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "2") int pageSize,
                         UserQuery user,
                         Model model) {

        PageHelper.startPage(pageNum, pageSize);
        // 根据BlogQuery对象的属性来进行查询
//        List<User> users = userService.queryUsersByUserName(username);
        List<User> users = userService.queryAllUsers(user);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        model.addAttribute("page", userPageInfo);

        return "admin/users :: userList";
    }

    @PostMapping("/saveUser")
    public String save(User user, RedirectAttributes redirectAttributes) {
        User u = userService.queryUserByUserName(user.getUsername());
        if (u != null) {
            redirectAttributes.addFlashAttribute("msg", "不能存在相同的用户名");
            return "redirect:/admin/users/input";
        }

        if (user == null) {
            redirectAttributes.addFlashAttribute("errormsg", "操作失败！");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作成功！");
        }

        int save = userService.saveUser(user);
        if (save > 0) {
            System.out.println("save success!!!!");
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/updateUsers/{id}")
    public String updateUsers(User user, RedirectAttributes redirectAttributes) {
        if (user == null) {
            redirectAttributes.addFlashAttribute("errormsg", "更新失败！！！");
        } else {
            redirectAttributes.addFlashAttribute("msg", "更新成功！！！");
        }


        int update = userService.updateUser(user);
        if (update > 0) {
            System.out.println("save success!!!!");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/input")
    public String toInput(Model model) {
        model.addAttribute("user", new User());
        return "admin/users-input";
    }

    @GetMapping("/users/{id}/update")
    public String toUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.queryUserById(id));
        return "admin/users-input";
    }

    @GetMapping("/users/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        int i = userService.deleteUser(id);
        if (i > 0) {
            redirectAttributes.addFlashAttribute("msg", "删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "删除失败！");
        }
        return "redirect:/admin/users";
    }
}
