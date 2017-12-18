package cn.jrry.sys.mapper;

import cn.jrry.sys.domain.Group;

import java.util.List;

public interface GroupMapper {
    int insert(Group record);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(Group record);

    Group selectByPrimaryKey(Integer id);

    List<Group> query(Group record);
}
