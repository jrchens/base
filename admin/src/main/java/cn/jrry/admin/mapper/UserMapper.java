package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int removeByPrimaryKey(User record);

    int count(Map<String, Object> record);

    List<User> select(Map<String, Object> record);

    User selectByUsername(String username);
}