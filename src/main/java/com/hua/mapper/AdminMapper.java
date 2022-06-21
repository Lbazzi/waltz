package com.hua.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {

    Long queryTotalBlogs();

    Long queryTotalViews();

    Long queryTotalComments();

}
