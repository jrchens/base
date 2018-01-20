package cn.jrry.cms.mapper;

import cn.jrry.cms.domain.Category;
import java.util.List;
import java.util.Map;

public interface CategoryMapper {
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