package cn.jrry.wx.service.impl;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.common.exception.ServiceException;
import cn.jrry.common.exception.WxInvokeException;
import cn.jrry.wx.domain.WxMenu;
import cn.jrry.wx.domain.WxResponse;
import cn.jrry.wx.domain.WxUserInfo;
import cn.jrry.wx.domain.WxWebAccessToken;
import cn.jrry.wx.mapper.WxAccessTokenMapper;
import cn.jrry.wx.mapper.WxWebAccessTokenMapper;
import cn.jrry.wx.service.WxInvokeService;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WxInvokeServiceImpl implements WxInvokeService {
    private static final Logger logger = LoggerFactory.getLogger(WxInvokeService.class);
    private static final String APP_ID_KEY = "76900565-ac11-4a9f-a990-1475db91db2f";
    private static final String APP_SECRET_KEY = "8128125a-d95b-40cb-840e-477abfc594a7";
    private static final String WX_DEFAULT_USER_TAG_KEY = "356bf3a4-418c-4bf3-9e33-c1c3446aa751";
    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;
    @Autowired
    private WxWebAccessTokenMapper wxWebAccessTokenMapper;
    @Autowired
    private ConfigService configService;

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
            String accessToken = getAccessToken();

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
            Map<String, Object> result = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());

//            { "access_token":"ACCESS_TOKEN",
//                    "expires_in":7200,
//                    "refresh_token":"REFRESH_TOKEN",
//                    "openid":"OPENID",
//                    "scope":"SCOPE" }

            if(result.get("errcode") == null){
                WxWebAccessToken wxWebAccessToken = gson.fromJson(json, WxWebAccessToken.class);
                Date ex = DateTime.now().plusMinutes(90).toDate();
                wxWebAccessToken.setExpires_time(ex);

                return wxWebAccessToken;
            }else{
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

    public int insertWebAccessToken(WxWebAccessToken wxWebAccessToken){
        try {
            return wxWebAccessTokenMapper.insert(wxWebAccessToken);
        } catch (Exception ex) {
            logger.error("insertWebAccessToken error {}", ex);
            throw new ServiceException("insertWebAccessToken error",ex);
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

}
