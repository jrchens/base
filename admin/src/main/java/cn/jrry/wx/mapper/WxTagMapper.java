package cn.jrry.wx.mapper;

import cn.jrry.wx.domain.WxTag;
import cn.jrry.wx.domain.WxUserInfo;

import java.util.List;
import java.util.Map;

public interface WxTagMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxTag record);

    WxTag selectByPrimaryKey(Long id);

    List<WxTag> selectAll();

    int updateByPrimaryKey(WxTag record);

    int removeByPrimaryKey(WxTag record);

    int count(Map<String, Object> record);

    List<WxTag> select(Map<String, Object> record);
}