package cn.jrry.sys.service.impl;

import cn.jrry.sys.domain.SysGroup;
import cn.jrry.sys.mapper.SysGroupMapper;
import cn.jrry.sys.service.SysGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysGroupServiceImpl implements SysGroupService {

    @Autowired
    private SysGroupMapper sysGroupMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysGroupMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysGroup record) {
        record.setDeleted(Boolean.FALSE);
        return sysGroupMapper.insert(record);
    }

    @Override
    public SysGroup selectByPrimaryKey(Long id) {
        return sysGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysGroup> selectAll() {
        return sysGroupMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(SysGroup record) {
        return sysGroupMapper.updateByPrimaryKey(record);
    }
}
