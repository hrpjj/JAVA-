package com.library.service;

import com.library.dao.UserDao;
import com.library.entity.User;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    // 登录业务：校验账号密码 + 校验账号状态是否正常
    public User login(String username, String password) {
        User user = userDao.login(username, password);
        if (user != null && "使用".equals(user.getUserState())) {
            return user;
        }
        return null;
    }

    // 新增用户
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    // 删除用户
    public boolean deleteUser(int userId) {
        return userDao.deleteUserById(userId);
    }

    // 修改用户
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    // 根据ID查询用户
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    // 查询所有用户
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}