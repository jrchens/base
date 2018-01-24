package cn.jrry.task.impl;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.task.WxAccessTokenTask;
import cn.jrry.wx.domain.WxAccessToken;
import cn.jrry.wx.mapper.WxAccessTokenMapper;
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

    @Override
    public void generate() {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
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


            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();


            // access_token,expires_in
            // errcode,errmsg
            // AccessToken

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get access_token response json : {}", json);


            WxAccessToken wxAccessToken = gson.fromJson(json, WxAccessToken.class);
            Integer errcode = wxAccessToken.getErrcode();
            if (errcode == null || errcode.intValue() == 0) {
                // wxAccessTokenMapper.delete();
//                Integer expiresIn = wxAccessToken.getExpires_in();
//                double plusSecond = expiresIn.intValue() * 0.8d;
//                Date expiresTime = DateTime.now().plusSeconds(Double.valueOf(plusSecond).intValue()).toDate();
                Date expiresTime = DateTime.now().plusMinutes(90).toDate();
                wxAccessToken.setExpires_time(expiresTime);
                wxAccessToken.setCrtime(DateTime.now().toDate());
                wxAccessTokenMapper.insert(wxAccessToken);
            } else {
                // TODO send notification to admin
            }
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
    }
}
