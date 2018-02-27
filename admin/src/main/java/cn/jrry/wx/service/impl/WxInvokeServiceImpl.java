package cn.jrry.wx.service.impl;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.common.exception.ServiceException;
import cn.jrry.common.exception.WxInvokeException;
import cn.jrry.wx.domain.*;
import cn.jrry.wx.mapper.WxAccessTokenMapper;
import cn.jrry.wx.mapper.WxJsapiTicketMapper;
import cn.jrry.wx.mapper.WxWebAccessTokenMapper;
import cn.jrry.wx.service.WxInvokeService;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(value = "wxInvokeServiceImpl")
public class WxInvokeServiceImpl implements WxInvokeService {
    private static final Logger logger = LoggerFactory.getLogger(WxInvokeService.class);
    private static final String APP_ID_KEY = "76900565-ac11-4a9f-a990-1475db91db2f";
    private static final String APP_SECRET_KEY = "8128125a-d95b-40cb-840e-477abfc594a7";
    private static final String UPLOAD_BASE_PATH = "34c10bf0-fe3b-4ff0-846d-66acf338e7fb";
    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;
    @Autowired
    private WxWebAccessTokenMapper wxWebAccessTokenMapper;
    @Autowired
    private WxJsapiTicketMapper wxJsapiTicketMapper;

    @Autowired
    private ConfigService configService;


    @Override
    public void refreshAccessTokenTask() {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String appid = configService.getString(APP_ID_KEY);
            String secret = configService.getString(APP_SECRET_KEY);

            // https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/token";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("grant_type", "client_credential"));
            nvps.add(new BasicNameValuePair("appid", appid));
            nvps.add(new BasicNameValuePair("secret", secret));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);


            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("task --> refresh access_token response json : {}", json);

            Gson gson = new Gson();

            AccessTokenResponse accessTokenResponse = gson.fromJson(json, AccessTokenResponse.class);
            if (accessTokenResponse.getErrcode() == null) {
                WxAccessToken wxAccessToken = new WxAccessToken();
                wxAccessToken.setAccess_token(accessTokenResponse.getAccess_token());
                wxAccessToken.setExpires_in(accessTokenResponse.getExpires_in());
                wxAccessToken.setExpires_time(DateTime.now().plusMinutes(90).toDate());
                wxAccessTokenMapper.insert(wxAccessToken);
            } else {
                // TODO send notification to admin
            }
        } catch (Exception ex) {
            logger.error("refresh access_token error {}", ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
                logger.error("close error {}", e);
            }
        }
    }


    @Override
    public void refreshWebAccessTokenTask() {
        try {
            Date expiresTime = DateTime.now().plusMinutes(10).toDate();
            List<String> refreshTokenList = wxWebAccessTokenMapper.selectNeedRefresh(expiresTime);
            for (String refreshToken : refreshTokenList
                    ) {
                RefreshTokenResponse refreshTokenResponse = refresh(refreshToken);
                if (refreshTokenResponse.getErrcode() == null) {
                    WxWebAccessToken wxWebAccessToken = new WxWebAccessToken();
                    wxWebAccessToken.setAccess_token(refreshTokenResponse.getAccess_token());
                    wxWebAccessToken.setRefresh_token(refreshTokenResponse.getRefresh_token());
                    wxWebAccessToken.setScope(refreshTokenResponse.getScope());
                    wxWebAccessToken.setExpires_time(DateTime.now().plusMinutes(90).toDate());
                    wxWebAccessToken.setOpenid(refreshTokenResponse.getOpenid());
                    wxWebAccessTokenMapper.updateByOpenid(wxWebAccessToken);
                }
            }
        } catch (Exception ex) {
            logger.error("task --> refresh web_access_token error {}", ex);
        }
    }

    @Override
    public void refreshJsapiTicketTask() {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {


            Date expiresTime = DateTime.now().plusMinutes(10).toDate();
            List<WxJsapiTicket> refreshTokenList = wxJsapiTicketMapper.selectNeedRefresh(expiresTime);
            if (refreshTokenList.size() == 0) {
                return;
            }

            String access_token = getAccessToken();

            // https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/ticket/getticket";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", access_token));
            nvps.add(new BasicNameValuePair("type", "jsapi"));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);


            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get jsapi_ticket response json : {}", json);

            Gson gson = new Gson();

            JsapiTicketResponse jsapiTicketResponse = gson.fromJson(json, JsapiTicketResponse.class);

            if (jsapiTicketResponse.getErrcode() == 0) {
                WxJsapiTicket wxJsapiTicket = new WxJsapiTicket();
                wxJsapiTicket.setTicket(jsapiTicketResponse.getTicket());
                wxJsapiTicket.setExpires_in(jsapiTicketResponse.getExpires_in());
                wxJsapiTicket.setExpires_time(DateTime.now().plusMinutes(90).toDate());
                wxJsapiTicketMapper.insert(wxJsapiTicket);
            } else {
                // TODO send notification to admin
            }
        } catch (Exception ex) {
            logger.error("get jsapi_ticket error {}", ex);
            throw new WxInvokeException("get jsapi_ticket error", ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
                logger.error("close error {}", e);
            }
        }
    }


    private RefreshTokenResponse refresh(String refresh_token) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String appid = configService.getString(APP_ID_KEY);

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/sns/oauth2/refresh_token";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("appid", appid));
            nvps.add(new BasicNameValuePair("grant_type", "refresh_token"));
            nvps.add(new BasicNameValuePair("refresh_token", refresh_token));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("refresh web_access_token response json : {}", json);

            Gson gson = new Gson();

            return gson.fromJson(json, RefreshTokenResponse.class);
        } catch (Exception ex) {
            logger.error("refresh web_access_token error {}", ex);
            throw new WxInvokeException("refresh web_access_token", ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
                logger.error("close error {}", e);
            }
        }
    }

    public String getAccessToken() {
        try {
            return wxAccessTokenMapper.getAccessToken();
        } catch (Exception ex) {
            logger.error("getAccessToken error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxWebAccessToken getWebAccessToken(String code) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String appid = configService.getString(APP_ID_KEY);
            String secret = configService.getString(APP_SECRET_KEY);
            String grant_type = "authorization_code";

            // https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/sns/oauth2/access_token";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("appid", appid));
            nvps.add(new BasicNameValuePair("secret", secret));
            nvps.add(new BasicNameValuePair("code", code));
            nvps.add(new BasicNameValuePair("grant_type", grant_type));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get sns/oauth2/access_token response : {}", json);

            Gson gson = new Gson();
            Map<String, Object> result = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());

//            { "access_token":"ACCESS_TOKEN",
//                    "expires_in":7200,
//                    "refresh_token":"REFRESH_TOKEN",
//                    "openid":"OPENID",
//                    "scope":"SCOPE" }

            if (result.get("errcode") == null) {
                WxWebAccessToken wxWebAccessToken = gson.fromJson(json, WxWebAccessToken.class);
                Date ex = DateTime.now().plusMinutes(90).toDate();
                wxWebAccessToken.setExpires_time(ex);

                return wxWebAccessToken;
            } else {
                throw new WxInvokeException(result.get("errcode").toString());
            }

        } catch (Exception ex) {
            logger.error("sns/oauth2/access_token error {}", ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }

    public int deleteWebAccessTokenByOpenid(String openid) {
        try {
            return wxWebAccessTokenMapper.deleteByOpenid(openid);
        } catch (Exception ex) {
            logger.error("deleteWebAccessTokenByOpenid error {}", ex);
            throw new ServiceException("deleteWebAccessTokenByOpenid error", ex);
        }
    }

    public int insertWebAccessToken(WxWebAccessToken wxWebAccessToken) {
        try {
            return wxWebAccessTokenMapper.insert(wxWebAccessToken);
        } catch (Exception ex) {
            logger.error("insertWebAccessToken error {}", ex);
            throw new ServiceException("insertWebAccessToken error", ex);
        }
    }

    @Override
    public WxUserInfo getUserInfo(String openid) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String appid = configService.getString(APP_ID_KEY);
            String accessToken = getAccessToken();

            // https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/user/info";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", accessToken));
            nvps.add(new BasicNameValuePair("openid", openid));
            nvps.add(new BasicNameValuePair("lang", "zh_CN"));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get user/info response : {}", json);

            Gson gson = new Gson();
            return gson.fromJson(json, WxUserInfo.class);

//            Integer errcode = wxUserInfo.getErrcode();
//            if (errcode == null || errcode.intValue() == 0) {
//                logger.debug("get user/info success {}", json);
//            } else {
//                logger.error("get user/info error {}", json);
//            }
        } catch (Exception ex) {
            logger.error("user/info error {}", ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }

    @Override
    public String getUserInfo() {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String accessToken = getAccessToken();

            // https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/user/get";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", accessToken));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get user/get response : {}", json);

            if (json.indexOf("errcode") != -1) {
                Gson gson = new Gson();
                WxResponse wxResponse = gson.fromJson(json, WxResponse.class);
                throw new WxInvokeException(String.valueOf(wxResponse.getErrcode()));
            }
            return json;

        } catch (Exception ex) {
            logger.error("user/get error {}", ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }


    @Override
    @Deprecated
    public String getTag() {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String accessToken = getAccessToken();

            // https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/get";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", accessToken));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get user/info response : {}", json);

            if (json.indexOf("errcode") != -1) {
                Gson gson = new Gson();
                WxResponse wxResponse = gson.fromJson(json, WxResponse.class);
                throw new WxInvokeException(String.valueOf(wxResponse.getErrcode()));
            }
            return json;

        } catch (Exception ex) {
            logger.error("user/info error {}", ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }

    @Override
    @Deprecated
    public Long insertTag(Map<String, Object> params) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/create";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = new Gson();
            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/create response {}", json);
            // {   "tag":{ "id":134,//标签id "name":"广东"   } }

            Map<String, Map<String, Object>> result = gson.fromJson(json, new TypeToken<Map<String, Map<String, Object>>>() {
            }.getType());

            Object errcode = result.get("errcode");
            if (null == errcode) {
                NumberFormat numberFormat = new DecimalFormat("0");
                return numberFormat.parse(result.get("tag").get("id").toString()).longValue();
            } else {
                throw new WxInvokeException(errcode.toString());
            }
        } catch (Exception ex) {
            logger.error("insertTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }


    @Override
    @Deprecated
    public WxResponse updateTag(Map<String, Object> params) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            // https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/update";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = new Gson();
            String reqData = gson.toJson(params);

            httpPost.setEntity(new StringEntity(reqData, ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/update response {}", json);
            // {   "errcode":0,   "errmsg":"ok" }

            return gson.fromJson(json, WxResponse.class);
        } catch (Exception ex) {
            logger.error("updateTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }


    @Override
    @Deprecated
    public WxResponse deleteTag(Map<String, Object> params) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/delete";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = new Gson();
            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/delete response {}", json);

            return gson.fromJson(json, WxResponse.class);
        } catch (Exception ex) {
            logger.error("deleteTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }

    @Override
    public WxResponse insertUserInfoTag(Map<String, Object> params) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN
//            {   "openid_list" : [//粉丝列表
//                "ocYxcuAEy30bX0NXmGn4ypqx3tI0",
//                        "ocYxcuBt0mRugKZ7tGAHPnUaOW7Y"   ],
//                "tagid" : 134 }
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/members/batchtagging";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = new Gson();
            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/members/batchtagging response {}", json);

            return gson.fromJson(json, WxResponse.class);

        } catch (Exception ex) {
            logger.error("insertUserInfoTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }


    @Override
    public WxResponse deleteUserInfoTag(Map<String, Object> params) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN
//            {   "openid_list" : [//粉丝列表
//                "ocYxcuAEy30bX0NXmGn4ypqx3tI0",
//                        "ocYxcuBt0mRugKZ7tGAHPnUaOW7Y"   ],
//                "tagid" : 134 }

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/members/batchuntagging";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = new Gson();
            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/members/batchuntagging response {}", json);

            return gson.fromJson(json, WxResponse.class);

        } catch (Exception ex) {
            logger.error("deleteUserInfoTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);

            }
        }
    }

//

    @Override
    public String getMenu() {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String accessToken = getAccessToken();

            // https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/menu/get";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", accessToken));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String result = EntityUtils.toString(entity, "UTF-8");
            logger.info("get menu/get response : {}", result);
            return result;

//            Gson gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().disableHtmlEscaping().create();
//            return gson.fromJson(result, Object.class);
        } catch (Exception ex) {
            logger.error("menu/get error {}", ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }

    @Override
    public WxResponse deleteMenu() {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String accessToken = getAccessToken();

            // https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/menu/delete";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", accessToken));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get menu/delete response : {}", json);

            Gson gson = new Gson();
            return gson.fromJson(json, WxResponse.class);

        } catch (Exception ex) {
            logger.error("deleteMenu error {}", ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }

    @Override
    public WxResponse createMenu(Map<String, Object> params) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/menu/create";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().disableHtmlEscaping().create();
            String json = gson.toJson(params);
            logger.info("wx menu/create request {}", json);
            httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx menu/create response {}", json);

            return gson.fromJson(json, WxResponse.class);

        } catch (Exception ex) {
            logger.error("createMenu error {}{}{}", params, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }


    @Override
    public String getMenuUrl(WxMenu wxMenu) {

        try {

//            参数	是否必须	说明
//            appid	是	公众号的唯一标识
//            redirect_uri	是	授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
//            response_type	是	返回类型，请填写code
//            scope	是	应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
//            state	否	重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
//            #wechat_redirect	是	无论直接打开还是做页面302重定向时候，必须带此参数

            String url = wxMenu.getNode_url();

            if (!StringUtils.hasText(url)) {
                return url;
            }

            String appid = configService.getString(APP_ID_KEY);

            String scope = wxMenu.getView_scope();
            String state = wxMenu.getUrl_state();

            String templete = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

            if (StringUtils.hasText(scope)) {
                url = URLEncoder.encode(url, "UTF-8");
                url = String.format(templete, appid, url, scope, state);
                logger.info("url: {}", url);
                return url;
            } else {
                return url;
            }

        } catch (Exception ex) {
            logger.error("getMenuUrl error {}{}{}", wxMenu, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        }
    }


    @Override
    public Long createConditionalMenu(Map<String, Object> params) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/menu/addconditional";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().disableHtmlEscaping().create();
            String json = gson.toJson(params);
            logger.info("wx menu/addconditional request {}", json);
            httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx menu/addconditional response {}", json);

            Map<String, Object> result = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());

            Object errcode = result.get("errcode");
            if (null == errcode) {
                NumberFormat numberFormat = new DecimalFormat("0");
                return numberFormat.parse(result.get("menuid").toString()).longValue();
            } else {
                throw new WxInvokeException(errcode.toString());
            }

        } catch (Exception ex) {
            logger.error("createConditionalMenu error {}{}{}", params, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }


    @Override
    public WxResponse deleteConditionalMenu(Map<String, Object> params) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/menu/delconditional";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = new Gson();
            String json = gson.toJson(params);
            logger.info("wx menu/delconditional request {}", json);
            httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx menu/delconditional response {}", json);

            return gson.fromJson(json, WxResponse.class);

        } catch (Exception ex) {
            logger.error("deleteConditionalMenu error {}{}{}", params, System.lineSeparator(), ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }

    @Override
    public String getJsapiTicket() {
        try {
            return wxJsapiTicketMapper.getJsapiTicket();
        } catch (Exception ex) {
            logger.error("getJsapiTicket error {}", ex);
            throw new WxInvokeException(ex);
        }
    }

    @Override
    public WxConfig getWxConfig(String url, String code, String state) {
        String appid = configService.getString(APP_ID_KEY);
        Long timestamp = System.currentTimeMillis() / 1000;
        String nonceStr = RandomStringUtils.randomAlphanumeric(12);

        String[] params = {
                "jsapi_ticket=".concat(getJsapiTicket()),
                "noncestr=".concat(nonceStr),
                "timestamp=".concat(String.valueOf(timestamp)),
                "url=".concat(url)
        };

        Arrays.sort(params);

        String str = Joiner.on("&").join(params);
        String signature = Hashing.sha1().hashString(str, Charsets.UTF_8).toString();

        WxConfig config = new WxConfig();
        config.setAppId(appid);
        config.setTimestamp(timestamp);
        config.setNonceStr(nonceStr);
        config.setSignature(signature);

        logger.info("wx config : {}", config);

        return config;
    }


    @Override
    public File getMedia(String serverId) {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            String accessToken = getAccessToken();

            // https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/media/get";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", accessToken));
            nvps.add(new BasicNameValuePair("media_id", serverId));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);

            int code = closeableHttpResponse.getStatusLine().getStatusCode();
            if (code == 200) {

                Header[] headers = closeableHttpResponse.getHeaders("Content-disposition");
                String filename = serverId.concat(".jpg");
                for (int i = 0; i < headers.length; i++) {
                    String value = headers[i].getValue();
                    if (value.indexOf(serverId) != -1) {
                        int idx = value.indexOf("filename=");
                        filename = value.substring(idx + 9);
                        filename = filename.replaceAll("\"","");
                    }
                }
                HttpEntity entity = closeableHttpResponse.getEntity();

                String basePath = configService.getString(UPLOAD_BASE_PATH);
                SimpleDateFormat df = new SimpleDateFormat("/yyyy/MM/dd");
                String prefixPath = df.format(DateTime.now().toDate());
                File dir = new File(basePath.concat(prefixPath));
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File dest = new File(dir, filename);
                FileCopyUtils.copy(EntityUtils.toByteArray(entity), new FileOutputStream(dest));
                return dest;
            } else {
//                String json = EntityUtils.toString(entity, "UTF-8");
//                logger.info("get media/get response : {}", json);
//
//                Gson gson = new Gson();
//                MediaResponse mediaResponse = gson.fromJson(json, MediaResponse.class);
                throw new RuntimeException(String.format("download media id ({}) object to local failed",serverId));
            }



        } catch (Exception ex) {
            logger.error("getMedia error {}", ex);
            throw new WxInvokeException(ex);
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }

//    appId: '', // 必填，公众号的唯一标识
//    timestamp: , // 必填，生成签名的时间戳
//    nonceStr: '', // 必填，生成签名的随机串
//    signature: '',// 必填，签名，见附录1


}
