package com.hua.controller.front;

import com.hua.entity.Message;
import com.hua.entity.User;
import com.hua.service.MessageService;
import com.hua.util.StringUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

//    @Value("${message.avatar}") 没在application配置
    private String avatar;

    @GetMapping("/message")
    public String message(Model model) {
//        model.addAttribute("messages", messageService.queryAllMessages());
        return "message";
    }

    @GetMapping("/messagecomment")
    public String messages(Model model) {
        List<Message> messages = messageService.queryAllMessages();
        model.addAttribute("messages", messages);
        return "message :: messageList";
    }

    @PostMapping("/messages")
    public String post(Message message, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        //设置头像
        if ((user != null) && (user.isType())) {
            message.setAvatar(user.getAvatar());
            message.setAdminMessage(true);
        } else {
            message.setAvatar("http://q1.qlogo.cn/g?b=qq&nk=" + StringUtils.getQQ(message.getEmail()) + "&s=100");
        }
        if (message.getParentMessage().getId() != null) {
            message.setParentMessageId(message.getParentMessage().getId());
        }
        messageService.saveMessage(message);
        List<Message> messages = messageService.queryAllMessages();
        model.addAttribute("messages", messages);
        return "message :: messageList";
    }

    @GetMapping("/messages/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes, Model model){
        messageService.deleteMessage(id);
        return "redirect:/message";
    }
}
