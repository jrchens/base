package cn.jrry.sys.service.impl;

import cn.jrry.sys.domain.User;
import cn.jrry.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public int removeUser(User user) {
        return 0;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User getUser(User user) {
        List<User> userList = jdbcTemplate.query(
                "select * from sys_user where deleted = ? and id = ?",
                new Object[]{Boolean.FALSE, user.getId()},
                new BeanPropertyRowMapper<User>(User.class));

        return DataAccessUtils.requiredSingleResult(userList);
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> userList = jdbcTemplate.query(
                "select * from sys_user where deleted = ? and username = ?",
                new Object[]{Boolean.FALSE, username},
                new BeanPropertyRowMapper<User>(User.class));

        return DataAccessUtils.requiredSingleResult(userList);
    }

    @Override
    public List<User> queryUser(User user) {
        return null;
    }
}
