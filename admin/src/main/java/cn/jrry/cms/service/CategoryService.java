package cn.jrry.cms.service;

import cn.jrry.cms.domain.Category;
import cn.jrry.cms.domain.TreeNode;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    Category selectByPrimaryKey(Long id);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);

    int removeByPrimaryKey(Category record);
    int count(Map<String,Object> record);
    List<Category> select(Map<String,Object> record);
    List<Category> selectByParentId(Long parentId);
}
