package cn.jrry.wx.service;

import cn.jrry.wx.domain.WxUserInfo;

import java.util.List;
import java.util.Map;

public interface WxUserInfoService {
    int deleteByPrimaryKey(Long id);

    int insert(WxUserInfo record);

    WxUserInfo selectByPrimaryKey(Long id);

    List<WxUserInfo> selectAll();

    int updateByPrimaryKey(WxUserInfo record);


    int count(Map<String, Object> record);

    List<WxUserInfo> select(Map<String, Object> record);

    WxUserInfo selectByOpenid(String openid);

}
