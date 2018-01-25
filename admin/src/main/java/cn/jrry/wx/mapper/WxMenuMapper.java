package cn.jrry.wx.mapper;

import cn.jrry.cms.domain.TreeNode;
import cn.jrry.wx.domain.WxMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WxMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxMenu record);

    WxMenu selectByPrimaryKey(Long id);

    List<WxMenu> selectAll();

    int updateByPrimaryKey(WxMenu record);

    int count(Map<String,Object> record);
    List<WxMenu> select(Map<String,Object> record);

    List<WxMenu> selectByParentId(@Param(value = "parent_id") Long parent_id);
    List<TreeNode> selectAsTreeNode(@Param(value = "parent_id") Long parentId);
    int updateMenuid(WxMenu record);

}