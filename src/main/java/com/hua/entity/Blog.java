package com.hua.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    private Long id;
    private String title;

    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer likes;
    private String description;
    private boolean doAppreciation;
    private boolean doShare;
    private boolean doComment;
    private boolean published;
    private boolean doRecommend;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Date updateTime;


    private Type type;
    private Tag tag;
    private User user;

    private List<Tag> tags;
    private List<Comment> comments;

    //tagIds不会进入数据库
    private String tagIds;
}
