package cn.jrry.sys.service.impl;

import cn.jrry.sys.domain.Group;
import cn.jrry.sys.mapper.GroupMapper;
import cn.jrry.sys.service.GroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public int insert(Group record) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        record.setDeleted(Boolean.FALSE);
        record.setCrtime(now);
        return groupMapper.insert(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return groupMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Group record) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        record.setMdtime(now);
        return groupMapper.updateByPrimaryKey(record);
    }

    @Override
    public Group selectByPrimaryKey(Integer id) {
        return groupMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Group> query(Group record, int pageNum, int pageSize) {
        PageInfo<Group> pi = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Group> list = groupMapper.query(record);
        pi = new PageInfo<Group>(list);
        return pi;
    }
}

