package com.hua.service.admin;


import com.hua.entity.Blog;
import com.hua.vo.BlogQuery;
import com.hua.vo.BlogVo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public interface BlogService {

    Blog queryBlogById(Long id);

    Blog queryBlogOnFront(Long id);

    List<BlogQuery> queryAllBlogs();

    List<BlogQuery> queryAllBlogsByTagIdOnFront();

    List<Blog> queryAllBlogs(BlogQuery blog);

    List<Blog> queryAllBlogsOnFront(BlogQuery blog);

    List<Blog> queryUtdBlogsOnFront(BlogQuery blog);

    List<Blog> queryPopBlogsOnFront(BlogQuery blog);

//    List<BlogVo> archiveBlogs();

    LinkedHashMap<String, List<Blog>> archiveBlogs();

    Long queryTotalBlogs();

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

    int incView(long id);

    int incLikes();
}
