package com.hua.service.admin;


import com.hua.entity.Type;
import com.hua.vo.TypeQuery;

import java.util.List;


public interface TypeService {

    Type queryTypeById(Long id);
    Type queryTypeByName(String name);

    List<Type> queryAllTypes();

    List<Type> queryAllTypes(TypeQuery type);

    int saveType(Type type);
    int updateType(Type type);
    int deleteType(Long id);

}
