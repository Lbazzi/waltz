package com.hua.mapper;

import com.hua.entity.Link;
import com.hua.vo.LinkQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface LinkMapper {

    @Transactional
    Link queryLinkById(@Param("id") int id);

    Link queryLinkByName(@Param("name") String name);

    @Transactional
    List<Link> queryAllLinks();

    List<Link> queryAllLinksByQuery(LinkQuery link);

    @Transactional
    List<Link> queryAllLinksOnFront();

    @Transactional
    int saveLink(Link link);

    @Transactional
    int updateLink(Link link);

    @Transactional
    int deleteLink(@Param("id") int id);

}
