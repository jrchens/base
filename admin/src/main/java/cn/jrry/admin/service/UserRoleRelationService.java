package cn.jrry.admin.service;

import cn.jrry.admin.domain.UserRoleRelation;

import java.util.List;
import java.util.Map;

public interface UserRoleRelationService {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRelation record);
    int insert(List<UserRoleRelation> records);

    UserRoleRelation selectByPrimaryKey(Long id);

    List<UserRoleRelation> selectAll();

    int updateByPrimaryKey(UserRoleRelation record);

    int countRole(Map<String,Object> record);
    List<UserRoleRelation> selectRole(Map<String,Object> record);

    List<UserRoleRelation> selectUserByRolename(String roleName);
    int updateDefByPrimaryKey(Long id);
}
