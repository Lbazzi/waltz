package com.hua.vo;

import com.hua.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BlogVo {
    private String year;
    private List<Blog> list;
}
