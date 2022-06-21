package com.hua.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag {

    private Long id;

    private Long blogId;

    private Integer tagsId;

}
