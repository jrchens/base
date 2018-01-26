package cn.jrry.task.impl;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.common.exception.WxInvokeException;
import cn.jrry.task.WxAccessTokenTask;
import cn.jrry.wx.domain.AccessTokenResponse;
import cn.jrry.wx.domain.RefreshTokenResponse;
import cn.jrry.wx.domain.WxAccessToken;
import cn.jrry.wx.domain.WxWebAccessToken;
import cn.jrry.wx.mapper.WxAccessTokenMapper;
import cn.jrry.wx.mapper.WxWebAccessTokenMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class WxAccessTokenTaskImpl implements WxAccessTokenTask {

    private static final Logger logger = LoggerFactory.getLogger(WxAccessTokenTask.class);
    private static final Gson gson = new Gson();
    private static final String APP_ID_KEY = "76900565-ac11-4a9f-a990-1475db91db2f";
    private static final String APP_SECRET_KEY = "8128125a-d95b-40cb-840e-477abfc594a7";
    @Autowired
    private ConfigService configService;
    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;
    @Autowired
    private WxWebAccessTokenMapper wxWebAccessTokenMapper;

    @Override
    public void generate() {
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
            logger.info("get access_token response json : {}", json);

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
            logger.error("generate error {}", ex);
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
    public void refresh() {
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
                    wxWebAccessTokenMapper.updateByOpenid(wxWebAccessToken);
                }
            }
        } catch (Exception ex) {
            logger.error("refresh web access token error {}", ex);
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


}
