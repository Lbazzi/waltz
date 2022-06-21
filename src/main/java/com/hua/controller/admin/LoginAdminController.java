package com.hua.controller.admin;

import com.hua.entity.User;
import com.hua.service.admin.AdminService;
import com.hua.service.admin.UserService;
import com.hua.util.MD5Utils;
import com.hua.util.StatusCode;
import com.hua.util.StringUtils;
import com.hua.vo.RegisterUser;
import com.hua.vo.TipsVo;
import com.hua.vo.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping("/admin")
public class LoginAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String toLogin(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null || user.isType() == false) {
            return "admin/login";
        } else {
            return "redirect:/admin/index";
        }
    }

    @GetMapping("/index")
    public String toAdminIndex() {
        return "admin/index";
    }

    @GetMapping("/main")
    public String toAdminMain(Model model) {
        model.addAttribute("blog_count", adminService.queryTotalBlogs());
        model.addAttribute("views_sum", adminService.queryTotalViews());
        model.addAttribute("comment_count", adminService.queryTotalComments());
        return "admin/main";
    }

    @PostMapping("/login")
    @ResponseBody
    public TipsVo login(@RequestBody User user, HttpSession httpSession, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User u = userService.userVerify(user.getUsername(), MD5Utils.code(user.getPassword()));
        if (u != null && u.isType()) {
            user.setPassword(null);
            httpSession.setAttribute("user", u);
            return new TipsVo(true, StatusCode.OK, "管理员登录成功");
        } else if (u != null && u.isType() == false) {
            httpSession.setAttribute("user", u);
            return new TipsVo(false, StatusCode.ACCESSERROR, "非管理员");
        } else {
            return new TipsVo(false, StatusCode.LOGINERROR, "用户名或密码错误");
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public TipsVo register(@RequestBody User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User u = userService.queryUserByUserName(user.getUsername());
        if (u != null) {
            redirectAttributes.addFlashAttribute("rmsg", "");
            return new TipsVo(false, StatusCode.ERROR, "用户名已存在");
        }
        int save = userService.registerUser(user);

        if (save > 0) {
            session.setAttribute("user", user);
            System.out.println("save success!!!!");
        }
        return new TipsVo(true, StatusCode.OK, "注册成功");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @PostMapping("/modify")
    @ResponseBody
    public TipsVo modify(@RequestBody User user) {
        User u = userService.queryUserByUserName(user.getUsername());
        if (u != null) {
              int update = userService.modifyPassword(user);
              if (update > 0) {
                  return new TipsVo(true, StatusCode.OK, "修改成功");
              }
        }
        return new TipsVo(false, StatusCode.ERROR, "修改失败");
    }
}
