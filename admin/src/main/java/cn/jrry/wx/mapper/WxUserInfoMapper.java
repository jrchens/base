package cn.jrry.wx.mapper;

import cn.jrry.wx.domain.WxUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WxUserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxUserInfo record);

    WxUserInfo selectByPrimaryKey(Long id);

    List<WxUserInfo> selectAll();

    int updateByPrimaryKey(WxUserInfo record);

    int count(Map<String, Object> record);

    List<WxUserInfo> select(Map<String, Object> record);

    WxUserInfo selectByOpenid(@Param(value = "openid") String openid);

}