package com.hua.mapper;

import com.hua.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    List<Message> queryAllAdminMessages();

    List<Message> queryMessageById(@Param("ParentId") Long ParentId);

    List<Message> queryMessageByParentId(@Param("id") Long id);

    List<Message> queryMessageByReplyId(@Param("replyId") Long replyId);

    int saveMessage(Message message);

    int deleteMessage(Long id);
}
