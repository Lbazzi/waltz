package com.hua.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogTagMapper {

    List<Long> queryBlogTagByBlogId(@Param("blogId") long blogId);

    int saveBlogTag(@Param("blogId") Long blogId, @Param("tagId") Long tagId);

    int deleteBlogTag(@Param("blogId") Long blogId);
}
