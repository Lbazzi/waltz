package com.hua.service.admin;

import com.hua.entity.Link;
import com.hua.vo.LinkQuery;

import java.util.List;


public interface LinkService {

    Link queryLinkById(int id);

    Link queryLinkByName(String name);

    List<Link> queryAllLinks();

    List<Link> queryAllLinks(LinkQuery link);

    List<Link> queryAllLinksOnFront();

    int saveLink(Link link);

    int updateLink(Link link);

    int deleteLink(int id);

}
