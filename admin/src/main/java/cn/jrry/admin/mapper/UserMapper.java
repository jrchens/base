package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.UserDTO;
import cn.jrry.admin.domain.UserVO;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDTO record);

    UserVO selectByPrimaryKey(Long id);

    List<UserVO> selectAll();

    int updateByPrimaryKey(UserDTO record);

    int removeByPrimaryKey(UserDTO record);

    int count(Map<String, Object> record);

    List<UserVO> select(Map<String, Object> record);
}