package com.hua.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    private Long id;

    @NotBlank(message = "分类名称不能为空！")
    private String name;

    private String typeImg;
    private List<Blog> blogs;

}
