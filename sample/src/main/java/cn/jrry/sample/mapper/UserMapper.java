package cn.jrry.sample.mapper;

import cn.jrry.common.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int insert(User record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);

    User selectByPrimaryKey(Long id);
    Long selectId();

    int count(Map<String,Object> record);

    List<User> select(Map<String,Object> record);

}