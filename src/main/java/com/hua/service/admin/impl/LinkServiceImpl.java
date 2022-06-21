package com.hua.service.admin.impl;


import com.hua.entity.Link;
import com.hua.mapper.LinkMapper;
import com.hua.service.admin.LinkService;
import com.hua.vo.LinkQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public Link queryLinkById(int id) {
        return linkMapper.queryLinkById(id);
    }

    @Override
    public Link queryLinkByName(String name) {
        return linkMapper.queryLinkByName(name);
    }

    @Override
    public List<Link> queryAllLinks() {
        return linkMapper.queryAllLinks();
    }

    @Override
    public List<Link> queryAllLinks(LinkQuery link) {
        return linkMapper.queryAllLinksByQuery(link);
    }

    @Override
    public List<Link> queryAllLinksOnFront() {
        return linkMapper.queryAllLinksOnFront();
    }

    @Override
    public int saveLink(Link link) {
        return linkMapper.saveLink(link);
    }

    @Override
    public int updateLink(Link link) {
        return linkMapper.updateLink(link);
    }

    @Override
    public int deleteLink(int id) {
        return linkMapper.deleteLink(id);
    }
}
