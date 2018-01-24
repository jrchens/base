package cn.jrry.wx.mapper;

import cn.jrry.wx.domain.WxUserInfoTagRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WxUserInfoTagRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxUserInfoTagRelation record);

    WxUserInfoTagRelation selectByPrimaryKey(Long id);

    List<WxUserInfoTagRelation> selectAll();

    int updateByPrimaryKey(WxUserInfoTagRelation record);
    int count(Map<String,Object> record);
    List<WxUserInfoTagRelation> select(Map<String,Object> record);

    int deleteByOpenid(@Param(value = "openid") String openid);
    int deleteByTagid(@Param(value = "tag_id") Long tag_id);
    int deleteByRelationIds(@Param(value = "openid") String openid,@Param(value = "tag_id") Long tag_id);
    List<WxUserInfoTagRelation> selectByOpenid(@Param(value = "openid") String openid);
}