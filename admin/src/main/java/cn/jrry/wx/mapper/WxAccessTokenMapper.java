package cn.jrry.wx.mapper;

import cn.jrry.wx.domain.WxAccessToken;

import java.util.List;

public interface WxAccessTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxAccessToken record);

    WxAccessToken selectByPrimaryKey(Integer id);

    List<WxAccessToken> selectAll();

    int updateByPrimaryKey(WxAccessToken record);

    int delete();

    String getAccessToken();
}