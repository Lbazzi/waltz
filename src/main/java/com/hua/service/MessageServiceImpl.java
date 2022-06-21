package com.hua.service;

import com.hua.entity.Message;
import com.hua.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    private List<Message> tmpReplys = new ArrayList<>();

    @Override
    public List<Message> queryAllAdminMessages() {
        return messageMapper.queryAllAdminMessages();
    }

    @Override
    public List<Message> queryAllMessages() {
        //查询出父节点
        List<Message> messages = messageMapper.queryMessageById(Long.parseLong("-1"));
        for(Message message : messages){
            Long id = message.getId();
            String parentNickname1 = message.getNickname();
            List<Message> replyMessages = messageMapper.queryMessageByParentId(id);
            //查询出子留言
            combineChildren(replyMessages, parentNickname1);
            message.setReplyMessages(tmpReplys);
            tmpReplys = new ArrayList<>();
        }
        return messages;
    }

    private void combineChildren(List<Message> replyMessages, String parentNickname1) {
        //判断是否有一级子回复
        if(replyMessages.size() > 0){
            //循环找出子留言的id
            for(Message replyMessage : replyMessages){
                String parentNickname = replyMessage.getNickname();
                replyMessage.setParentNickname(parentNickname1);
                tmpReplys.add(replyMessage);
                Long replyId = replyMessage.getId();
                //查询二级以及所有子集回复
                recursively(replyId, parentNickname);
            }
        }
    }

    private void recursively(Long replyId, String parentNickname1) {
        //根据子一级留言的id找到子二级留言
        List<Message> replayMessages = messageMapper.queryMessageByReplyId(replyId);

        if(replayMessages.size() > 0){
            for(Message replayMessage : replayMessages){
                String parentNickname = replayMessage.getNickname();
                replayMessage.setParentNickname(parentNickname1);
                Long replayId = replayMessage.getId();
                tmpReplys.add(replayMessage);
                //循环迭代找出子集回复
                recursively(replayId,parentNickname);
            }
        }
    }

    @Override
    public int saveMessage(Message message) {
        message.setCreateTime(new Date());
        return messageMapper.saveMessage(message);
    }

    @Override
    public int deleteMessage(Long id) {
        return messageMapper.deleteMessage(id);
    }
}
