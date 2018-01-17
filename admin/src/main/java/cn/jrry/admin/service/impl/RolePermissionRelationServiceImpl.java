package cn.jrry.admin.service.impl;

import cn.jrry.admin.domain.RolePermissionRelation;
import cn.jrry.admin.mapper.RolePermissionRelationMapper;
import cn.jrry.admin.service.GroupService;
import cn.jrry.admin.service.RolePermissionRelationService;
import cn.jrry.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class RolePermissionRelationServiceImpl implements RolePermissionRelationService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return rolePermissionRelationMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(RolePermissionRelation record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setCruser(user);
//            record.setCrtime(now);

            int aff = rolePermissionRelationMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public RolePermissionRelation selectByPrimaryKey(Long id) {
        try {
            return rolePermissionRelationMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<RolePermissionRelation> selectAll() {
        try {
            return rolePermissionRelationMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(RolePermissionRelation record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setMduser(user);
//            record.setMdtime(now);
            return rolePermissionRelationMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int countPermission(Map<String,Object> record) {
        try {
            return rolePermissionRelationMapper.countPermission(record);
        } catch (Exception ex) {
            logger.error("countPermission error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<RolePermissionRelation> selectPermission(Map<String,Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return rolePermissionRelationMapper.selectPermission(record);
        } catch (Exception ex) {
            logger.error("selectPermission error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }


    @Override
    public int insert(List<RolePermissionRelation> records) {

        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            record.setCruser(user);
//            record.setCrtime(now);

            int aff = 0;
            for (RolePermissionRelation record :records
                 ) {
                aff = rolePermissionRelationMapper.insert(record);
            }

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", records, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }


    @Override
    public List<RolePermissionRelation> selectByRoleName(String roleName) {
        try {
            return rolePermissionRelationMapper.selectByRoleName(roleName);
        } catch (Exception ex) {
            logger.error("selectByRoleName error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

}
