package cn.jrry.sys.service;

import cn.jrry.sys.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    int removeUser(User user);

    User updateUser(User user);

    User getUser(User user);

    User getUserByUsername(String username);

    List<User> queryUser(User user);
}
