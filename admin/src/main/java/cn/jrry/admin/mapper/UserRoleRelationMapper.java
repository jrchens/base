package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.UserGroupRelation;
import cn.jrry.admin.domain.UserRoleRelation;
import java.util.List;

public interface UserRoleRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRelation record);

    UserRoleRelation selectByPrimaryKey(Long id);

    List<UserRoleRelation> selectAll();

    int updateByPrimaryKey(UserRoleRelation record);


    List<UserRoleRelation> selectRoleByUsername(String username);
    List<UserRoleRelation> selectUserByRolename(String roleName);
    int updateDefByUsername(String username);
    int updateDefByPrimaryKey(Long id);
}