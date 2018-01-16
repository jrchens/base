package cn.jrry.admin.service;

import cn.jrry.admin.domain.UserGroupRelation;

import java.util.List;

public interface UserGroupRelationService {
    int deleteByPrimaryKey(Long id);

    int insert(UserGroupRelation record);
    int insert(List<UserGroupRelation> records);

    UserGroupRelation selectByPrimaryKey(Long id);

    List<UserGroupRelation> selectAll();

    int updateByPrimaryKey(UserGroupRelation record);

    List<UserGroupRelation> selectUserByGroupname(String groupName);
    List<UserGroupRelation> selectGroupByUsername(String username);
    int updateDefByPrimaryKey(Long id);
}
