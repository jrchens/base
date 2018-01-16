package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.UserGroupRelation;

import java.util.List;

public interface UserGroupRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserGroupRelation record);

    UserGroupRelation selectByPrimaryKey(Long id);

    List<UserGroupRelation> selectAll();

    int updateByPrimaryKey(UserGroupRelation record);


    List<UserGroupRelation> selectUserByGroupname(String groupName);
    List<UserGroupRelation> selectGroupByUsername(String username);

    int updateDefByUsername(String username);
    int updateDefByPrimaryKey(Long id);
}