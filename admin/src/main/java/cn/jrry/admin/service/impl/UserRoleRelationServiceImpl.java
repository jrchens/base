package cn.jrry.admin.service.impl;

import cn.jrry.admin.domain.UserRoleRelation;
import cn.jrry.admin.mapper.UserRoleRelationMapper;
import cn.jrry.admin.service.GroupService;
import cn.jrry.admin.service.UserRoleRelationService;
import cn.jrry.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleRelationServiceImpl implements UserRoleRelationService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return userRoleRelationMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(UserRoleRelation record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setCruser(user);
//            record.setCrtime(now);

            int aff = userRoleRelationMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(List<UserRoleRelation> records) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setCruser(user);
//            record.setCrtime(now);
            int aff = 0;
            for (UserRoleRelation userRoleRelation : records
                    ) {
                aff += userRoleRelationMapper.insert(userRoleRelation);
            }


            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", records, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public UserRoleRelation selectByPrimaryKey(Long id) {
        try {
            return userRoleRelationMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserRoleRelation> selectAll() {
        try {
            return userRoleRelationMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(UserRoleRelation record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setMduser(user);
//            record.setMdtime(now);
            return userRoleRelationMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserRoleRelation> selectUserByRolename(String roleName) {
        try {
            return userRoleRelationMapper.selectUserByRolename(roleName);
        } catch (Exception ex) {
            logger.error("selectUserByRolename error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserRoleRelation> selectRoleByUsername(String username) {
        try {
            return userRoleRelationMapper.selectRoleByUsername(username);
        } catch (Exception ex) {
            logger.error("selectRoleByUsername error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateDefByPrimaryKey(Long id) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setMduser(user);
//            record.setMdtime(now);

            UserRoleRelation userRoleRelation = selectByPrimaryKey(id);
            int aff = userRoleRelationMapper.updateDefByUsername(userRoleRelation.getUsername());
            aff = userRoleRelationMapper.updateDefByPrimaryKey(id);

            return aff;
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }



}
