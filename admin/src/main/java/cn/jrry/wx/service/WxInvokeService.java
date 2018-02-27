package cn.jrry.wx.service;

import cn.jrry.wx.domain.*;

import java.io.File;
import java.util.Map;

public interface WxInvokeService {


    void refreshAccessTokenTask(); // access token
    void refreshWebAccessTokenTask(); // web access token
    void refreshJsapiTicketTask();

    String getAccessToken();

    WxWebAccessToken getWebAccessToken(String code);
    int deleteWebAccessTokenByOpenid(String openid);
    int insertWebAccessToken(WxWebAccessToken wxWebAccessToken);

    WxUserInfo getUserInfo(String openid);

    String getUserInfo();

    @Deprecated
    String getTag();
    @Deprecated
    Long insertTag(Map<String, Object> params);
    @Deprecated
    WxResponse updateTag(Map<String, Object> params);
    @Deprecated
    WxResponse deleteTag(Map<String, Object> params);


    WxResponse insertUserInfoTag(Map<String, Object> params);
    WxResponse deleteUserInfoTag(Map<String, Object> params);

    String getMenu();
    WxResponse deleteMenu();
    WxResponse createMenu(Map<String,Object> params);
    String getMenuUrl(WxMenu wxMenu);

    Long createConditionalMenu(Map<String,Object> params);
    WxResponse deleteConditionalMenu(Map<String,Object> params);


    String getJsapiTicket();
    WxConfig getWxConfig(String url,String code, String state);

    File getMedia(String serverId);

}
