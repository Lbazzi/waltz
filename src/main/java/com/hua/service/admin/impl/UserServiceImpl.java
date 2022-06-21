package com.hua.service.admin.impl;

import com.hua.entity.User;
import com.hua.mapper.UserMapper;
import com.hua.service.admin.UserService;
import com.hua.util.MD5Utils;
import com.hua.util.StringUtils;
import com.hua.vo.RegisterUser;
import com.hua.vo.UserQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAllUsers() {
        return userMapper.queryAllUsers();
    }

    @Override
    public List<User> queryAllUsers(UserQuery user) {
        return userMapper.queryAllUsersByQuery(user);
    }

    @Override
    public List<User> queryUsersByUserName(String username) {
        return userMapper.queryUsersByUserName(username);
    }

    @Override
    public UserQuery userExist(UserQuery userQuery) {
        return userMapper.userExist(userQuery);
    }

    @Override
    public User queryUserById(Long id) {
        return userMapper.queryUserById(id);
    }

    @Override
    public User queryUserByUserName(String username) {
        return userMapper.queryUserByUserName(username);
    }

    @Override
    public User userVerify(String name, String password) {
//        User user = userMapper.userVerify(name, MD5Utils.code(password));
        User user = userMapper.userVerify(name, password);
        return user;
    }

    @Override
    public int userRegister(UserQuery userQuery) {
//        User user = new User();
//        BeanUtils.copyProperties(registerUser, user);
        userQuery.setAvatar("http://q1.qlogo.cn/g?b=qq&nk=" + StringUtils.getQQ(userQuery.getEmail()) + "&s=100");
        userQuery.setType(false);
        userQuery.setCreateTime(new Date());
        userQuery.setUpdateTime(new Date());
        return userMapper.userRegister(userQuery);
    }

    @Override
    public int registerUser(User user) {
//        User user = new User();
//        BeanUtils.copyProperties(registerUser, user);
        user.setAvatar("http://q1.qlogo.cn/g?b=qq&nk=" + StringUtils.getQQ(user.getEmail()) + "&s=100");
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setType(false);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.registerUser(user);
    }

    @Override
    public int modifyPassword(User user) {
        user.setPassword(MD5Utils.code(user.getPassword()));
        return userMapper.modifyUser(user);
    }

    @Override
    public int saveUser(User user) {
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.saveUser(user);
    }

    @Override
    public int updateUser(User user) {
//        System.out.println(user.getCreateTime());
//        user.setCreateTime(user.getCreateTime());
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setUpdateTime(new Date());
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(Long id) {
        return userMapper.deleteUser(id);
    }
}
