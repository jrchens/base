package cn.jrry.wx.mapper;

import cn.jrry.wx.domain.WxWebAccessToken;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface WxWebAccessTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxWebAccessToken record);

    WxWebAccessToken selectByPrimaryKey(Integer id);

    List<WxWebAccessToken> selectAll();

    int updateByPrimaryKey(WxWebAccessToken record);

    List<String> selectNeedRefresh(@Param(value = "expires_time") Date expires_time);
    int updateByOpenid(WxWebAccessToken record);
    int deleteByOpenid(@Param(value = "openid") String openid);
}