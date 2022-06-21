package com.hua.vo;

import com.hua.entity.Blog;
import com.hua.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentQuery {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;

    private Blog blog;

    private List<Comment> replyComments = new ArrayList<>();

    private boolean adminComment;
    private Comment parentComment;
}
