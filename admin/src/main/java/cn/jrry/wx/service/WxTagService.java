package cn.jrry.wx.service;


import cn.jrry.wx.domain.WxTag;

import java.util.List;
import java.util.Map;

public interface WxTagService {
    int deleteByPrimaryKey(Long id);

    int insert(WxTag record);

    WxTag selectByPrimaryKey(Long id);

    List<WxTag> selectAll();

    int updateByPrimaryKey(WxTag record);

    int count(Map<String, Object> record);

    List<WxTag> select(Map<String, Object> record);

}
