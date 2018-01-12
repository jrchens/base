package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.UserDO;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    List<UserDO> selectAll();

    int updateByPrimaryKey(UserDO record);

    int removeByPrimaryKey(UserDO record);

    int count(Map<String, Object> record);

    List<UserDO> select(Map<String, Object> record);
}