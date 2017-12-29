package cn.jrry.sample.service;

import cn.jrry.common.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    int insert(User record);

    int deleteByPrimaryKey(Long id);
    int removeByPrimaryKey(User record);

    int updateByPrimaryKey(User record);

    User selectByPrimaryKey(Long id);

    int count(Map<String,Object> record);

    List<User> select(Map<String,Object> record);

}