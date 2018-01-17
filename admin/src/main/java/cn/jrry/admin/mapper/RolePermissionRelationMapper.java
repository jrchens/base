package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.RolePermissionRelation;
import java.util.List;
import java.util.Map;

public interface RolePermissionRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionRelation record);

    RolePermissionRelation selectByPrimaryKey(Long id);

    List<RolePermissionRelation> selectAll();

    int updateByPrimaryKey(RolePermissionRelation record);

    int countPermission(Map<String,Object> record);
    List<RolePermissionRelation> selectPermission(Map<String,Object> record);
    List<RolePermissionRelation> selectByRoleName(String roleName);
}