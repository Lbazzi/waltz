package com.hua.vo;

import com.hua.entity.Blog;
import com.hua.entity.Tag;
import com.hua.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogQuery {

    private Long id;
    private Date updateTime;
    //private boolean doRecommend;
    private boolean published;
    private Type type;
    private Tag tag;
    private String flag;
    private Blog blog;

    private String title;

    private Long typeId;

    private Long tagId;

    private String tagIds;

    private boolean doRecommend;
}
