package cn.jrry.wx.service;

import cn.jrry.wx.domain.WxMenu;
import cn.jrry.wx.domain.WxResponse;
import cn.jrry.wx.domain.WxUserInfo;

import java.util.List;
import java.util.Map;

public interface WxInvokeService {

    String getAccessToken();
    WxUserInfo getUserInfo(String openid);
    Long insertTag(Map<String, Object> params);
    WxResponse updateTag(Map<String, Object> params);
    WxResponse deleteTag(Map<String, Object> params);


    WxResponse insertUserInfoTag(Map<String, Object> params);
    WxResponse deleteUserInfoTag(Map<String, Object> params);

    Object getMenu();
    WxResponse deleteMenu();
    WxResponse createMenu(Map<String,Object> params);
    String getMenuUrl(WxMenu wxMenu);

    Long createConditionalMenu(Map<String,Object> params);
    WxResponse deleteConditionalMenu(Map<String,Object> params);

}
