package cn.jrry.sample.mapper;

import cn.jrry.common.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);

    User selectByPrimaryKey(Long id);
    Long selectId();

    int count(@Param("record") User record);

    List<User> select(@Param("record") User record, @Param("offset") int offset, @Param("limit") int limit);

}