package com.hua.mapper;

import com.hua.entity.Blog;
import com.hua.vo.BlogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogMapper {

    Blog queryBlogById(Long id);

    List<Blog> queryAllBlogs(BlogQuery blog);

    List<Blog> queryAllBlogsOnFront(BlogQuery blog);

    List<Blog> queryUtdBlogsOnFront(BlogQuery blog);

    List<Blog> queryPopBlogsOnFront(BlogQuery blog);

    List<BlogQuery> queryAllBlogQuery();

    List<BlogQuery> queryAllBlogsByTagIdOnFront();

    List<String> findGroupYear();

    List<Blog> findByYear(String year);

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(@Param("id") Long id);

    int incView(Blog blog);

    int incLikes(Blog blog);

}
