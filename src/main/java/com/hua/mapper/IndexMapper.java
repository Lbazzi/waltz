package com.hua.mapper;

import com.hua.entity.Blog;
import com.hua.vo.BlogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface IndexMapper {

    Blog queryBlogById(Long id);

    //查出所有博客
    List<Blog> listBlog(BlogQuery blog);

    //查出所有博客
    List<Blog> listBlogOnFront(Blog blog);

}
