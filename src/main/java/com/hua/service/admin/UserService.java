package com.hua.service.admin;


import com.hua.entity.User;
import com.hua.vo.RegisterUser;
import com.hua.vo.UserQuery;

import java.util.List;


public interface UserService {

    List<User> queryAllUsers();

    List<User> queryAllUsers(UserQuery user);

    List<User> queryUsersByUserName(String username);

    User queryUserById(Long id);

    User queryUserByUserName(String username);

    UserQuery userExist(UserQuery userQuery);

    User userVerify(String name, String password);

    int userRegister(UserQuery userQuery);

    int registerUser(User user);

    int modifyPassword(User user);

    int saveUser(User user);

    int updateUser(User user);

    int deleteUser(Long id);

}
