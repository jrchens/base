package cn.jrry.admin.service.impl;

import cn.jrry.admin.domain.Config;
import cn.jrry.admin.domain.UserDO;
import cn.jrry.admin.domain.UserVO;
import cn.jrry.admin.mapper.UserMapper;
import cn.jrry.admin.service.ConfigService;
import cn.jrry.admin.service.UserService;
import cn.jrry.common.exception.ServiceException;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;
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

    private static final String DEFAULT_USER_GROUP_CODE = "6e1352df-2c9d-4811-8359-ac0d68e2291e";

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
    public int insert(UserDO record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);
            record.setDeleted(Boolean.FALSE);

            String passwordSalt = RandomStringUtils.randomAlphanumeric(8);
            record.setPasswordSalt(passwordSalt);

            Sha512Hash sha256Hash = new Sha512Hash(record.getPassword(), passwordSalt);
            record.setPassword(sha256Hash.toHex());
            int aff =  userMapper.insert(record);

            Config config = configService.selectByPrimaryKey(DEFAULT_USER_GROUP_CODE);
            logger.info("default user group_name :{}",config.getCfgValue());

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public UserVO selectByPrimaryKey(Long id) {
        try {
            UserDO userDO = userMapper.selectByPrimaryKey(id);
            UserVO userVO = new UserVO();
            PropertyUtils.copyProperties(userVO, userDO);
            return userVO;
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserVO> selectAll() {
        try {
            List<UserDO> userDOList = userMapper.selectAll();
            List<UserVO> userVOList = Lists.newArrayList();
            for (UserDO userDO : userDOList
                    ) {
                UserVO userVO = new UserVO();
                PropertyUtils.copyProperties(userVO, userDO);
                userVOList.add(userVO);
            }
            return userVOList;

        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(UserDO record) {
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
    public int removeByPrimaryKey(UserDO record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            record.setDeleted(Boolean.TRUE);
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
    public List<UserVO> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            List<UserDO> userDOList = userMapper.select(record);
            List<UserVO> userVOList = Lists.newArrayList();
            for (UserDO userDO : userDOList
                    ) {
                UserVO userVO = new UserVO();
                PropertyUtils.copyProperties(userVO, userDO);
                userVOList.add(userVO);
            }
            return userVOList;

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
