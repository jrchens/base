package cn.jrry.wx.service;


import cn.jrry.wx.domain.WxUserInfoTagRelation;

import java.util.List;
import java.util.Map;

public interface WxUserInfoTagRelationService {
    int deleteByPrimaryKey(Long id);

    int insert(WxUserInfoTagRelation record);

    WxUserInfoTagRelation selectByPrimaryKey(Long id);

    List<WxUserInfoTagRelation> selectAll();

    int updateByPrimaryKey(WxUserInfoTagRelation record);

    int count(Map<String, Object> record);

    List<WxUserInfoTagRelation> select(Map<String, Object> record);

    int deleteByOpenid(String openid);
    int deleteByTagid(Long tag_id);
    int deleteByRelationIds(String openid,Long tag_id);
    List<WxUserInfoTagRelation> selectByOpenid(String openid);

}
