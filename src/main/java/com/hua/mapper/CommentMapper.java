package com.hua.mapper;

import com.hua.entity.Comment;
import com.hua.vo.CommentQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    List<Comment> listComments(@Param("blogId") Long blogId);

    List<Comment> listAllComments();

    List<Comment> queryAllCommentsByQuery(CommentQuery comment);

    List<Comment> listAdminComments();

    Comment getCommentByParentId(@Param("parentId") Long parentId);

    int saveComments(Comment comment);

    int deleteComments(@Param("id") Long id);
}
