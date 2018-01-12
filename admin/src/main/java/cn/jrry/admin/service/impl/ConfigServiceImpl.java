package cn.jrry.admin.service.impl;

import cn.jrry.admin.domain.Config;
import cn.jrry.admin.domain.UserDO;
import cn.jrry.admin.domain.UserVO;
import cn.jrry.admin.mapper.ConfigMapper;
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
public class ConfigServiceImpl implements ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public int deleteByPrimaryKey(String cfgCode) {
        try {
            return configMapper.deleteByPrimaryKey(cfgCode);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", cfgCode, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(Config record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);
            record.setDeleted(Boolean.FALSE);

            return configMapper.insert(record);
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public Config selectByPrimaryKey(String cfgCode) {
        try {
            return configMapper.selectByPrimaryKey(cfgCode);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", cfgCode, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Config> selectAll() {
        try {
            return configMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(Config record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);

            return configMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }


    @Override
    public int removeByPrimaryKey(Config record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            record.setDeleted(Boolean.TRUE);

            return configMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }


}
