package cn.jrry.admin.service.impl;

import cn.jrry.admin.domain.UserGroupRelation;
import cn.jrry.admin.mapper.UserGroupRelationMapper;
import cn.jrry.admin.service.GroupService;
import cn.jrry.admin.service.UserGroupRelationService;
import cn.jrry.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class UserGroupRelationServiceImpl implements UserGroupRelationService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private UserGroupRelationMapper userGroupRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return userGroupRelationMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(UserGroupRelation record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setCruser(user);
//            record.setCrtime(now);

            int aff = userGroupRelationMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(List<UserGroupRelation> records) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setCruser(user);
//            record.setCrtime(now);
            int aff = 0;
            for (UserGroupRelation userGroupRelation : records
                    ) {
                aff += userGroupRelationMapper.insert(userGroupRelation);
            }


            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", records, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public UserGroupRelation selectByPrimaryKey(Long id) {
        try {
            return userGroupRelationMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserGroupRelation> selectAll() {
        try {
            return userGroupRelationMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(UserGroupRelation record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setMduser(user);
//            record.setMdtime(now);
            return userGroupRelationMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int countUser(Map<String,Object> record) {
        try {
            return userGroupRelationMapper.countUser(record);
        } catch (Exception ex) {
            logger.error("countUserByGroupname error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserGroupRelation> selectUser(Map<String,Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return userGroupRelationMapper.selectUser(record);
        } catch (Exception ex) {
            logger.error("selectUserByGroupname error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }


    @Override
    public int countGroup(Map<String,Object> record) {
        try {
            return userGroupRelationMapper.countGroup(record);
        } catch (Exception ex) {
            logger.error("countGroupByUsername error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<UserGroupRelation> selectGroup(Map<String,Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return userGroupRelationMapper.selectGroup(record);
        } catch (Exception ex) {
            logger.error("selectGroupByUsername error {}", ex);
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

            UserGroupRelation userGroupRelation = selectByPrimaryKey(id);
            int aff = userGroupRelationMapper.updateDefByUsername(userGroupRelation.getUsername());
            aff = userGroupRelationMapper.updateDefByPrimaryKey(id);

            return aff;
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }



}
