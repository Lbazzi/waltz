package com.hua.service.admin.impl;

import com.hua.entity.Tag;
import com.hua.mapper.TagMapper;
import com.hua.service.admin.TagService;
import com.hua.vo.TagQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    public List<Long> convertSTL(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                list.add(new Long(split[i]));
            }
        }
        return list;
    }

    @Override
    public Tag queryTagById(Long id) {
        return tagMapper.queryTagById(id);
    }

    @Override
    public Tag queryTagByName(String name) {
        return tagMapper.queryTagByName(name);
    }

    @Override
    public List<Tag> queryAllTags() {
        List<Tag> list = tagMapper.queryAllTags();
        return list;
    }

    @Override
    public List<Tag> queryAllTags(TagQuery tag) {
        return tagMapper.queryAllTagsByQuery(tag);
    }

    @Override
    public List<Tag> queryAllTags(String ids) {
        return tagMapper.queryAllTagsByIds(convertSTL(ids));
    }

    @Override
    public List<Tag> queryAllTags(List<Long> ids) {
        return tagMapper.queryAllTagsByIds(ids);
    }

    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Override
    public int deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }
}
