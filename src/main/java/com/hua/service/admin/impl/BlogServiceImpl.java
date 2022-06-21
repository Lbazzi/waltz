package com.hua.service.admin.impl;


import com.hua.entity.Blog;
import com.hua.entity.Tag;
import com.hua.exception.BlogNotFoundException;
import com.hua.mapper.AdminMapper;
import com.hua.mapper.BlogMapper;
import com.hua.mapper.BlogTagMapper;
import com.hua.mapper.TagMapper;
import com.hua.service.admin.BlogService;
import com.hua.service.admin.TagService;
import com.hua.util.MarkdownUtils;
import com.hua.util.MyBeanUtils;
import com.hua.vo.BlogQuery;
import com.hua.vo.BlogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.tree.Tree;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private AdminMapper adminMapper;

//    @Autowired
//    private RedisService redisService;

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return null;
        }
    }

    void handlerBlogTag(Long blogId, List<Tag> tags) {
        for (Tag tag : tags) {
            blogTagMapper.saveBlogTag(blogId, tag.getId());
        }
    }

    @Transactional
    @Override
    public Blog queryBlogById(Long id) {
        Blog blog = blogMapper.queryBlogById(id);
        List<Long> longList = blogTagMapper.queryBlogTagByBlogId(id);
        List<Tag> tags = tagMapper.queryAllTagsByIds(longList);
        String s = tagsToIds(tags);
        blog.setTagIds(s);
        return blog;
    }

    @Override
    public Blog queryBlogOnFront(Long id) {
        Blog blog = blogMapper.queryBlogById(id);
        if (blog == null) {
            throw new BlogNotFoundException("博客不存在！");
        }
        Blog blog1 = new Blog();
        BeanUtils.copyProperties(blog, blog1);
        String content = blog1.getContent();
        blog1.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return blog1;
    }

    @Override
    public List<BlogQuery> queryAllBlogs() {
        return blogMapper.queryAllBlogQuery();
    }

    @Transactional
    @Override
    public List<Blog> queryAllBlogs(BlogQuery blog) {
        return blogMapper.queryAllBlogs(blog);
    }

    @Transactional
    @Override
    public List<Blog> queryAllBlogsOnFront(BlogQuery blog) {
        return blogMapper.queryAllBlogsOnFront(blog);
    }

    @Transactional
    @Override
    public List<BlogQuery> queryAllBlogsByTagIdOnFront() {
        return blogMapper.queryAllBlogsByTagIdOnFront();
    }

    @Override
    public List<Blog> queryUtdBlogsOnFront(BlogQuery blog) {
        return blogMapper.queryUtdBlogsOnFront(blog);
    }

    @Override
    public List<Blog> queryPopBlogsOnFront(BlogQuery blog) {
        return blogMapper.queryPopBlogsOnFront(blog);
    }

//    @Override
//    public List<BlogVo> archiveBlogs() {
//        List<String> yearsList = blogMapper.findGroupYear();
//
//        List<BlogVo> list = new ArrayList();
////        Map<String, List<Blog>> map = new HashMap<>();
//        for (String year : yearsList) {
////            map.put(year, blogMapper.findByYear(year));
////            System.out.println("归档博客标题为：" + map.);
//
//            list.add(new BlogVo(year,blogMapper.findByYear(year)));
//        }
////        System.out.println("归档博客数量为：" + map.size());
//        return list;
//    }

    @Override
    public LinkedHashMap<String, List<Blog>> archiveBlogs() {
        List<String> yearsList = blogMapper.findGroupYear();

        LinkedHashMap<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : yearsList) {
            map.put(year, blogMapper.findByYear(year));
        }
        return map;
    }

    public Long queryTotalBlogs() {
        return adminMapper.queryTotalBlogs();
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setLikes(1);
        blog.setViews(1);
        int saveBlog = blogMapper.saveBlog(blog);
        handlerBlogTag(blog.getId(), blog.getTags());
        return saveBlog;
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        Blog blog1 = blogMapper.queryBlogById(blog.getId());
        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());

        int update = blogMapper.updateBlog(blog1);
        //删除所有标签（之后优化）
        blogTagMapper.deleteBlogTag(blog.getId());
        //插入新的标签
        handlerBlogTag(blog.getId(), blog.getTags());
        return update;
    }

    @Transactional
    @Override
    public int deleteBlog(Long id) {
        return blogMapper.deleteBlog(id);
    }

    @Transactional
    @Override
    public int incView(long id) {
        Blog blog = new Blog();
        blog.setId(id);
        return blogMapper.incView(blog);
    }

    @Transactional
    @Override
    public int incLikes() {
        return 0;
    }
}
