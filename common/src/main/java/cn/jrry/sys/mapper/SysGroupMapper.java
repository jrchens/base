package cn.jrry.sys.mapper;

import cn.jrry.sys.domain.SysGroup;

import java.util.List;

public interface SysGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysGroup record);

    SysGroup selectByPrimaryKey(Long id);

    List<SysGroup> selectAll();

    int updateByPrimaryKey(SysGroup record);
}