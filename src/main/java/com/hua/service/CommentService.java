package com.hua.service;


import com.hua.entity.Comment;
import com.hua.vo.CommentQuery;

import java.util.List;

public interface CommentService {


    List<Comment> listComments(Long blogId);

    List<Comment> listAllComments();

    List<Comment> queryAllComments(CommentQuery comment);

    List<Comment> listAdminComments();

    int saveComments(Comment comment);

    int deleteComments(Long id);
}
