package com.hua.service.admin.impl;


import com.hua.entity.Type;
import com.hua.mapper.TypeMapper;
import com.hua.service.admin.TypeService;
import com.hua.vo.TypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Type queryTypeById(Long id) {
        return typeMapper.queryTypeById(id);
    }

    @Override
    public Type queryTypeByName(String name) {
        return typeMapper.queryTypeByName(name);
    }

    @Override
    public List<Type> queryAllTypes() {
        List<Type> list = typeMapper.queryAllTypes();
        return list;
    }

    @Override
    public List<Type> queryAllTypes(TypeQuery type) {
        return typeMapper.queryAllTypesByQuery(type);
    }

    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public int deleteType(Long id) {
        return typeMapper.deleteType(id);
    }
}
