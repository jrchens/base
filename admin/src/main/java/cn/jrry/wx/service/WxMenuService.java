package cn.jrry.wx.service;


import cn.jrry.cms.domain.TreeNode;
import cn.jrry.wx.domain.WxMenu;

import java.util.List;
import java.util.Map;

public interface WxMenuService {
    int deleteByPrimaryKey(Long id);

    int insert(WxMenu record);

    WxMenu selectByPrimaryKey(Long id);

    List<WxMenu> selectAll();

    int updateByPrimaryKey(WxMenu record);

    int count(Map<String, Object> record);

    List<WxMenu> select(Map<String, Object> record);

    List<WxMenu> selectByParentId(Long parentId);

    List<TreeNode> selectAsTree(Long parentId);

    int publish(Long id);

}
