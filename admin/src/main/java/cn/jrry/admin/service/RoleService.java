package cn.jrry.admin.service;

import cn.jrry.admin.domain.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    int removeByPrimaryKey(Role record);

    int count(Map<String, Object> record);

    List<Role> select(Map<String, Object> record);
}
