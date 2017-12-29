package cn.jrry.sample.service.impl;

import cn.jrry.common.domain.User;
import cn.jrry.sample.mapper.UserMapper;
import cn.jrry.sample.service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User record) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Long id = userMapper.selectId();

        record.setId(id);
        record.setCrtime(now);
        record.setDisabled(Boolean.FALSE);
        record.setLocked(Boolean.FALSE);
        record.setDeleted(Boolean.FALSE);
        return userMapper.insert(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int removeByPrimaryKey(User record) {
        User original = selectByPrimaryKey(record.getId());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        original.setMduser(record.getMduser());
        original.setMdtime(now);
        original.setDeleted(Boolean.TRUE);
        return userMapper.updateByPrimaryKey(original);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        record.setMdtime(now);
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int count(Map<String,Object> record) {
        return userMapper.count(record);
    }

    @Override
    public List<User> select(Map<String,Object>  record) {
        return userMapper.select(record);
    }
}
