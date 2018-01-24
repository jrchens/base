package cn.jrry.wx.service.impl;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.common.exception.ServiceException;
import cn.jrry.common.exception.WxInvokeException;
import cn.jrry.wx.domain.WxResponse;
import cn.jrry.wx.domain.WxUserInfo;
import cn.jrry.wx.mapper.WxAccessTokenMapper;
import cn.jrry.wx.service.WxInvokeService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

@Service
public class WxInvokeServiceImpl implements WxInvokeService {
    private static final Logger logger = LoggerFactory.getLogger(WxInvokeService.class);
    private static final Gson gson = new Gson();
    private static final String APP_ID_KEY = "76900565-ac11-4a9f-a990-1475db91db2f";
    private static final String WX_DEFAULT_USER_TAG_KEY = "356bf3a4-418c-4bf3-9e33-c1c3446aa751";
    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;
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
    public WxUserInfo getUserInfo(String openid) {
        WxUserInfo wxUserInfo = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
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

            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();


            // errcode,errmsg
            // {
//                    "subscribe": 1,
//                    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
//                    "nickname": "Band",
//                    "sex": 1,
//                    "language": "zh_CN",
//                    "city": "广州",
//                    "province": "广东",
//                    "country": "中国",
//                    "headimgurl":"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
//                    "subscribe_time": 1382694957,
//                    "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
//                    "remark": "",
//                    "groupid": 0,
//                    "tagid_list":[128,2]
//
//        }

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get user/info response : {}", json);

            wxUserInfo = gson.fromJson(json, WxUserInfo.class);

//            Integer errcode = wxUserInfo.getErrcode();
//            if (errcode == null || errcode.intValue() == 0) {
//                logger.debug("get user/info success {}", json);
//            } else {
//                logger.error("get user/info error {}", json);
//            }
        } catch (Exception ex) {
            logger.error("generate error {}", ex);
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (Exception e) {
                logger.error("close error {}", e);
            }
        }

        return wxUserInfo;

    }

    @Override
    public Long insertTag(Map<String, Object> params) {
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

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
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
            throw new ServiceException(ex.getCause());
        }
    }


    @Override
    public WxResponse updateTag(Map<String, Object> params) {
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

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            String reqData = gson.toJson(params);

            httpPost.setEntity(new StringEntity(reqData, ContentType.APPLICATION_JSON));

            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/update response {}", json);
            // {   "errcode":0,   "errmsg":"ok" }

            return gson.fromJson(json, WxResponse.class);
        } catch (Exception ex) {
            logger.error("updateTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }


    @Override
    public WxResponse deleteTag(Map<String, Object> params) {
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

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/delete response {}", json);

            return gson.fromJson(json, WxResponse.class);
        } catch (Exception ex) {
            logger.error("deleteTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxResponse insertUserInfoTag(Map<String, Object> params) {
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

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/members/batchtagging response {}", json);

            return gson.fromJson(json, WxResponse.class);

        } catch (Exception ex) {
            logger.error("insertUserInfoTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }



    @Override
    public WxResponse deleteUserInfoTag(Map<String, Object> params) {
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

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx tags/members/batchuntagging response {}", json);

            return gson.fromJson(json, WxResponse.class);

        } catch (Exception ex) {
            logger.error("deleteUserInfoTag error {}{}{}", params, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

}
