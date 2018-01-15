package cn.jrry.admin.mapper;

import cn.jrry.admin.domain.GroupDO;

import java.util.List;
import java.util.Map;

public interface GroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupDO record);

    GroupDO selectByPrimaryKey(Long id);

    List<GroupDO> selectAll();

    int updateByPrimaryKey(GroupDO record);

    int removeByPrimaryKey(GroupDO record);

    int count(Map<String, Object> record);

    List<GroupDO> select(Map<String, Object> record);
}