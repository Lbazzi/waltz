package com.hua.vo;

import com.hua.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeQuery {
    private Long id;

    @NotBlank(message = "分类名称不能为空！")
    private String name;

    private String typeImg;
    private List<Blog> blogs;
}
