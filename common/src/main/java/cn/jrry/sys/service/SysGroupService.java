package cn.jrry.sys.service;

import cn.jrry.sys.domain.SysGroup;

import java.util.List;

public interface SysGroupService {
    int deleteByPrimaryKey(Long id);

    int insert(SysGroup record);

    SysGroup selectByPrimaryKey(Long id);

    List<SysGroup> selectAll();

    int updateByPrimaryKey(SysGroup record);
}
