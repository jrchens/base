package cn.jrry.admin.service;

import cn.jrry.admin.domain.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    int removeByPrimaryKey(Permission record);

    int count(Map<String, Object> record);

    List<Permission> select(Map<String, Object> record);
}
