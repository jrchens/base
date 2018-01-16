package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.UserGroupRelation;

import java.util.List;
import java.util.Map;

public interface UserGroupRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserGroupRelation record);

    UserGroupRelation selectByPrimaryKey(Long id);

    List<UserGroupRelation> selectAll();

    int updateByPrimaryKey(UserGroupRelation record);

    int countUser(Map<String,Object> record);
    List<UserGroupRelation> selectUser(Map<String,Object> record);
    int countGroup(Map<String,Object> record);
    List<UserGroupRelation> selectGroup(Map<String,Object> record);

    int updateDefByUsername(String username);
    int updateDefByPrimaryKey(Long id);
}