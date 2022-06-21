package com.hua.mapper;

import com.hua.entity.Tag;
import com.hua.vo.TagQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {

    @Transactional
    Tag queryTagById(Long id);

    @Transactional
    Tag queryTagByName(@Param("name") String name);

    @Transactional
    List<Tag> queryAllTags();

    List<Tag> queryAllTagsByQuery(TagQuery tag);

    @Transactional
    List<Tag> queryAllTagsByIds(@Param("ids") List<Long> ids);

    @Transactional
    int saveTag(Tag tag);

    @Transactional
    int updateTag(Tag tag);

    @Transactional
    int deleteTag(Long id);

}
