package cn.jrry.wx.mapper;

import cn.jrry.wx.domain.WxMenuMatchRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxMenuMatchRuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxMenuMatchRule record);

    WxMenuMatchRule selectByPrimaryKey(Long id);

    List<WxMenuMatchRule> selectAll();

    int updateByPrimaryKey(WxMenuMatchRule record);

    WxMenuMatchRule selectByMenuid(@Param(value = "menu_id") Long menu_id);
    int deleteByMenuid(@Param(value = "menu_id") Long menu_id);
}