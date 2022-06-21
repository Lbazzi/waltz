package com.hua.mapper;

import com.hua.entity.Type;
import com.hua.vo.TypeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface TypeMapper {

    @Transactional
    Type queryTypeById(Long id);

    @Transactional
    Type queryTypeByName(@Param("name") String name);

    @Transactional
    List<Type> queryAllTypes();

    List<Type> queryAllTypesByQuery(TypeQuery type);

    @Transactional
    int saveType(Type type);

    @Transactional
    int updateType(Type type);

    @Transactional
    int deleteType(Long id);
}
