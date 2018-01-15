package cn.jrry.admin.service.impl;

import cn.jrry.admin.domain.GroupDO;
import cn.jrry.admin.domain.GroupVO;
import cn.jrry.admin.mapper.GroupMapper;
import cn.jrry.admin.service.GroupService;
import cn.jrry.common.exception.ServiceException;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return groupMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(GroupDO record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);

            int aff = groupMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public GroupVO selectByPrimaryKey(Long id) {
        try {
            GroupDO groupDO = groupMapper.selectByPrimaryKey(id);
            GroupVO groupVO = new GroupVO();
            PropertyUtils.copyProperties(groupVO, groupDO);
            return groupVO;
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<GroupVO> selectAll() {
        try {
            List<GroupDO> groupDOList = groupMapper.selectAll();
            List<GroupVO> groupVOList = Lists.newArrayList();
            for (GroupDO groupDO : groupDOList
                    ) {
                GroupVO groupVO = new GroupVO();
                PropertyUtils.copyProperties(groupVO, groupDO);
                groupVOList.add(groupVO);
            }
            return groupVOList;

        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(GroupDO record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return groupMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int removeByPrimaryKey(GroupDO record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return groupMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return groupMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<GroupVO> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            List<GroupDO> groupDOList = groupMapper.select(record);
            List<GroupVO> groupVOList = Lists.newArrayList();
            for (GroupDO groupDO : groupDOList
                    ) {
                GroupVO groupVO = new GroupVO();
                PropertyUtils.copyProperties(groupVO, groupDO);
                groupVOList.add(groupVO);
            }
            return groupVOList;

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
