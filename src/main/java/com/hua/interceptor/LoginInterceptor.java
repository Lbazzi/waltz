package com.hua.interceptor;

import com.hua.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//public class LoginInterceptor extends HandlerInterceptorAdapter {
public class LoginInterceptor implements HandlerInterceptor {

//    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 若session中没有user, 则没登陆
        User user = (User)request.getSession().getAttribute("user");
//        if (user == null) {
        if (user == null || user.isType() == false) {
//            request.getSession().getAttribute("user") == null
            //request.removeAttribute("user");
            response.sendRedirect("/admin");
            return false; // 返回false表示忽略当前请求, 若用户调用登录接口, 若未登录直接忽略
        }
//        else if (user != null || user.isType() == true) {
//            response.sendRedirect("/admin/index");
//            return false;
//        }
        return true; // 若用户已登录且是管理员, 放行进入后台管理页面
    }
}
