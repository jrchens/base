package cn.jrry.admin.service;

import cn.jrry.admin.domain.UserDO;
import cn.jrry.admin.domain.UserVO;

import java.util.List;
import java.util.Map;

public interface UserService {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    UserVO selectByPrimaryKey(Long id);

    List<UserVO> selectAll();

    int updateByPrimaryKey(UserDO record);

    int removeByPrimaryKey(UserDO record);

    int count(Map<String, Object> record);

    List<UserVO> select(Map<String, Object> record);
}
