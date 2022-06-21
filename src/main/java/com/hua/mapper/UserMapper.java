package com.hua.mapper;

import com.hua.entity.Type;
import com.hua.entity.User;
import com.hua.vo.RegisterUser;
import com.hua.vo.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryAllUsers();

    List<User> queryAllUsersByQuery(UserQuery user);

    List<User> queryUsersByUserName(@Param("username") String username);

    User queryUserById(Long id);

    User queryUserByUserName(@Param("username") String username);

    UserQuery userExist(UserQuery userQuery);

    User userVerify(@PathVariable String username, @PathVariable String password);

    int userRegister(UserQuery userQuery);

    int registerUser(User user);

    int modifyUser(User user);

    int saveUser(User user);

    @Transactional
    int updateUser(User user);

    @Transactional
    int deleteUser(Long id);
}
