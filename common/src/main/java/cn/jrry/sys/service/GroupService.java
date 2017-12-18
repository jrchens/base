package cn.jrry.sys.service;

import cn.jrry.sys.domain.Group;
import com.github.pagehelper.PageInfo;

public interface GroupService {
    int insert(Group record);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(Group record);

    Group selectByPrimaryKey(Integer id);

    PageInfo<Group> query(Group record, int pageNum, int pageSize);
}
