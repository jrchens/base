package cn.jrry.cms.service.impl;

import cn.jrry.cms.domain.Category;
import cn.jrry.cms.domain.TreeNode;
import cn.jrry.cms.mapper.CategoryMapper;
import cn.jrry.cms.service.CategoryService;
import cn.jrry.common.exception.ServiceException;
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
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return categoryMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(Category record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(user);
            record.setCrtime(now);

            int aff = categoryMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public Category selectByPrimaryKey(Long id) {
        try {
            return categoryMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Category> selectAll() {
        try {
            return categoryMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(Category record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return categoryMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int removeByPrimaryKey(Category record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);

            return categoryMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return categoryMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Category> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return categoryMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Category> selectByParentId(Long parentId) {
        try {
            return categoryMapper.selectByParentId(parentId);

        } catch (Exception ex) {
            logger.error("selectByParentId error {}{}{}", parentId, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    private void selectAsTree(final List<TreeNode> treeNodeList) {
        for (TreeNode treeNode : treeNodeList
                ) {
            List<TreeNode> treeNodes = categoryMapper.selectAsTreeNode(treeNode.getId());
            if (treeNodes != null && treeNodes.size() > 0) {
                treeNode.setChildren(treeNodes);
                selectAsTree(treeNodes);
            }
        }
    }

    @Override
    public List<TreeNode> selectAsTree(Long parentId) {
        try {
            List<TreeNode> treeNodeList = categoryMapper.selectAsTreeNode(parentId);
            selectAsTree(treeNodeList);
            return treeNodeList;
        } catch (Exception ex) {
            logger.error("selectAsTree error {}{}{}", parentId, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}