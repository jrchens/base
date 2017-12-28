package cn.jrry.sample.service;

import cn.jrry.common.domain.User;

import java.util.List;

public interface UserService {
    int insert(User record);

    int deleteByPrimaryKey(Long id);
    int removeByPrimaryKey(User record);

    int updateByPrimaryKey(User record);

    User selectByPrimaryKey(Long id);

    int count(User record);

    List<User> select(User record, int offset, int limit);

}