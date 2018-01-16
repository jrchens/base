package cn.jrry.admin.service.impl;

import cn.jrry.admin.domain.Config;
import cn.jrry.admin.domain.User;
import cn.jrry.admin.domain.UserGroupRelation;
import cn.jrry.admin.domain.UserRoleRelation;
import cn.jrry.admin.mapper.UserMapper;
import cn.jrry.admin.service.ConfigService;
import cn.jrry.admin.service.UserGroupRelationService;
import cn.jrry.admin.service.UserRoleRelationService;
import cn.jrry.admin.service.UserService;
import cn.jrry.common.exception.ServiceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConfigService configService;
    @Autowired
    private UserGroupRelationService userGroupRelationService;
    @Autowired
    private UserRoleRelationService userRoleRelationService;

    private static final String DEFAULT_USER_GROUP_CODE = "6e1352df-2c9d-4811-8359-ac0d68e2291e";
    private static final String DEFAULT_USER_ROLE_CODE = "292d8ffc-e394-49ce-8aba-71499f35fa55";

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return userMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(User record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);

            String passwordSalt = RandomStringUtils.randomAlphanumeric(8);
            record.setPasswordSalt(passwordSalt);

            Sha512Hash sha256Hash = new Sha512Hash(record.getPassword(), passwordSalt);
            record.setPassword(sha256Hash.toHex());
            int aff =  userMapper.insert(record);

            Config groupValueConfig = configService.selectByPrimaryKey(DEFAULT_USER_GROUP_CODE);
            logger.info("default user group_name :{}",groupValueConfig.getCfgValue());
            UserGroupRelation ugr = new UserGroupRelation();
            ugr.setUsername(record.getUsername());
            ugr.setGroupName(groupValueConfig.getCfgValue());
            ugr.setDef(Boolean.TRUE);
            userGroupRelationService.insert(ugr);

            Config roleValueConfig = configService.selectByPrimaryKey(DEFAULT_USER_ROLE_CODE);
            logger.info("default user role_name :{}",roleValueConfig.getCfgValue());
            UserRoleRelation urr = new UserRoleRelation();
            urr.setUsername(record.getUsername());
            urr.setRoleName(roleValueConfig.getCfgValue());
            urr.setDef(Boolean.TRUE);
            userRoleRelationService.insert(urr);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        try {
            return userMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public User selectByUsername(String username) {
        try {
            return userMapper.selectByUsername(username);
        } catch (Exception ex) {
            logger.error("selectByUsername error {}{}{}", username, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<User> selectAll() {
        try {
            return userMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(User record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return userMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int removeByPrimaryKey(User record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return userMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return userMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<User> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return userMapper.select(record);
        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
