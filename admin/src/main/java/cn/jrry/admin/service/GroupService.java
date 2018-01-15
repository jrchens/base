package cn.jrry.admin.service;

import cn.jrry.admin.domain.GroupDO;
import cn.jrry.admin.domain.GroupVO;

import java.util.List;
import java.util.Map;

public interface GroupService {
    int deleteByPrimaryKey(Long id);

    int insert(GroupDO record);

    GroupVO selectByPrimaryKey(Long id);

    List<GroupVO> selectAll();

    int updateByPrimaryKey(GroupDO record);

    int removeByPrimaryKey(GroupDO record);

    int count(Map<String, Object> record);

    List<GroupVO> select(Map<String, Object> record);
}
