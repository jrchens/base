package cn.jrry.admin.service;

import cn.jrry.admin.domain.UserRoleRelation;

import java.util.List;

public interface UserRoleRelationService {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRelation record);
    int insert(List<UserRoleRelation> records);

    UserRoleRelation selectByPrimaryKey(Long id);

    List<UserRoleRelation> selectAll();

    int updateByPrimaryKey(UserRoleRelation record);

    List<UserRoleRelation> selectRoleByUsername(String username);
    List<UserRoleRelation> selectUserByRolename(String roleName);
    int updateDefByPrimaryKey(Long id);
}
