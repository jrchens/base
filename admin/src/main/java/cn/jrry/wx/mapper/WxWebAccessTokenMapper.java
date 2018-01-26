package cn.jrry.wx.mapper;

import cn.jrry.wx.domain.WxWebAccessToken;

import java.util.List;

public interface WxWebAccessTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxWebAccessToken record);

    WxWebAccessToken selectByPrimaryKey(Integer id);

    List<WxWebAccessToken> selectAll();

    int updateByPrimaryKey(WxWebAccessToken record);

    String selectByOpenid(String openid);
}