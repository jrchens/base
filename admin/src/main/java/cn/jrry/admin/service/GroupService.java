package cn.jrry.admin.service;

import cn.jrry.admin.domain.Group;

import java.util.List;
import java.util.Map;

public interface GroupService {
    int deleteByPrimaryKey(Long id);

    int insert(Group record);

    Group selectByPrimaryKey(Long id);

    List<Group> selectAll();

    int updateByPrimaryKey(Group record);

    int removeByPrimaryKey(Group record);

    int count(Map<String, Object> record);

    List<Group> select(Map<String, Object> record);
}
