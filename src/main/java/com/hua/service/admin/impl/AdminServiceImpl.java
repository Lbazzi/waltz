package com.hua.service.admin.impl;

import com.hua.mapper.AdminMapper;
import com.hua.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminMapper adminMapper;


    @Override
    public Long queryTotalBlogs() {
        return adminMapper.queryTotalBlogs();
    }

    @Override
    public Long queryTotalViews() {
        return adminMapper.queryTotalViews();
    }

    @Override
    public Long queryTotalComments() {
        return adminMapper.queryTotalComments();
    }
}
