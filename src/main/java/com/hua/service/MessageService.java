package com.hua.service;

import com.hua.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> queryAllAdminMessages();

    List<Message> queryAllMessages();

    int saveMessage(Message message);

    int deleteMessage(Long id);
}
