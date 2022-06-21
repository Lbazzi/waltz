package com.hua.service.admin;


import com.hua.entity.Tag;
import com.hua.vo.TagQuery;

import java.util.List;

public interface TagService {

    Tag queryTagById(Long id);

    Tag queryTagByName(String name);

    List<Tag> queryAllTags();

    List<Tag> queryAllTags(TagQuery tag);

    List<Tag> queryAllTags(String ids);

    List<Tag> queryAllTags(List<Long> ids);

    int saveTag(Tag tag);

    int updateTag(Tag tag);

    int deleteTag(Long id);

}
