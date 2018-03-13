import cn.jrry.wx.util.Constants;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

public class WxTest {

    private static final Logger logger = LoggerFactory.getLogger(WxTest.class);

    @Test
    public void getAccessToken() throws Exception {
        // ovVfEs7WV5jXDA7Q-U9jftZGn4G0
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/token";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("grant_type", "client_credential"));
            nvps.add(new BasicNameValuePair("appid", Constants.APP_ID));
            nvps.add(new BasicNameValuePair("secret", Constants.APP_SECRET));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);


            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");

            System.out.println(json);

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
            }
        }
    }


    @Test
    public void getUserList() throws Exception {
        // ovVfEs7WV5jXDA7Q-U9jftZGn4G0
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/user/get";

//            String access_token = GET
//            https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
//

            String access_token = "7_tWatgFrV9bqDEgpRo7fmuKN95akxvISOHDnAyOoNS5XtOB_jAn8twjKa4k1yTfCWFDyDOjZHWy5GG_CvziuXTjsTCb3w39WJvoJQTuGXdWewHzWgBY-P_pc4wOqZot8zAhQjwidekVOt3SpkQPFeAAAWVL";
            String next_openid = "";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", access_token));
            if (StringUtils.hasText(next_openid)) {
                nvps.add(new BasicNameValuePair("next_openid", next_openid));
            }

            // {"total":14,"count":14,"data":{"openid":["o1cUw0Zc_JOMNDPDRW8tr9w3Y-78","o1cUw0UigW_pz80aufy3Y3sSQcPs","o1cUw0fLzp65vfa-o5MexQx3NjzY","o1cUw0cv40ROnf59IytHyA8XYzcw","o1cUw0XM-OX4xOIuD2uYfjrpKcnw","o1cUw0WgI8rZh0BzyJJENPKtYxeY","o1cUw0fv-UkPGgyQLl93lEaJfRo8","o1cUw0Tq3NAmqHOytWJxqfg78R2A","o1cUw0V0h6TxYCRRlZD8XKj1_rOg","o1cUw0aGZ2r_IEniJA3UY8YwnAno","o1cUw0ashN2UH8uWQpzT8notgY5g","o1cUw0UYCDCvCqf73AI7vlESBiak","o1cUw0Sa4UZZY3Qrory5tSr3fLf4","o1cUw0dEZTsLW22wHE-hQOtT3DzI"]},"next_openid":"o1cUw0dEZTsLW22wHE-hQOtT3DzI"}

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);


            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");

            System.out.println(json);

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
            }
        }
    }

    private String getUserInfo(String openid) throws Exception {
        String json = null;
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            // https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/user/info";


            String access_token = "7_tWatgFrV9bqDEgpRo7fmuKN95akxvISOHDnAyOoNS5XtOB_jAn8twjKa4k1yTfCWFDyDOjZHWy5GG_CvziuXTjsTCb3w39WJvoJQTuGXdWewHzWgBY-P_pc4wOqZot8zAhQjwidekVOt3SpkQPFeAAAWVL";
            String lang = "zh_CN";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", access_token));
            nvps.add(new BasicNameValuePair("openid", openid));
            nvps.add(new BasicNameValuePair("lang", lang));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);


            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();

            json = EntityUtils.toString(entity, "UTF-8");

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
            }
        }

        return json;
    }


    @Test
    public void getUserInfo() throws Exception {
        try {

            String[] openids = {
                    "o1cUw0Zc_JOMNDPDRW8tr9w3Y-78",
                    "o1cUw0UigW_pz80aufy3Y3sSQcPs",
                    "o1cUw0fLzp65vfa-o5MexQx3NjzY",
                    "o1cUw0cv40ROnf59IytHyA8XYzcw",
                    "o1cUw0XM-OX4xOIuD2uYfjrpKcnw",
                    "o1cUw0WgI8rZh0BzyJJENPKtYxeY",
                    "o1cUw0fv-UkPGgyQLl93lEaJfRo8",
                    "o1cUw0Tq3NAmqHOytWJxqfg78R2A",
                    "o1cUw0V0h6TxYCRRlZD8XKj1_rOg",
                    "o1cUw0aGZ2r_IEniJA3UY8YwnAno",
                    "o1cUw0ashN2UH8uWQpzT8notgY5g",
                    "o1cUw0UYCDCvCqf73AI7vlESBiak",
                    "o1cUw0Sa4UZZY3Qrory5tSr3fLf4",
                    "o1cUw0dEZTsLW22wHE-hQOtT3DzI"};


            for (String openid : openids
                    ) {
                String json = getUserInfo(openid);
                logger.info("json:{}",json);
                Files.write(json.getBytes(Charsets.UTF_8), new File("//Users/sheng/Documents/tmp/wx_user_info/".concat(openid).concat(".json")));
            }


        } catch (Exception ex) {
            throw ex;
        }
    }

    //
    @Test
    public void getWebAccessToken() throws Exception {
        try {
            // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri=https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect
            String scheme = "https";
            String host = "open.weixin.qq.com";
            String path = "/connect/oauth2/authorize";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("appid", Constants.APP_ID));
            nvps.add(new BasicNameValuePair("redirect_uri", URLEncoder.encode("https://wz.jrhot.com/api.php?id=4", Charsets.UTF_8.name())));
            nvps.add(new BasicNameValuePair("response_type", "code"));
            nvps.add(new BasicNameValuePair("scope", "snsapi_base"));
            nvps.add(new BasicNameValuePair("state", "test"));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);
            builder.setFragment("wechat_redirect");

            String url = builder.build().toString();
            System.out.println(url);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void refreshWebAccessToken() throws Exception {
        // ovVfEs7WV5jXDA7Q-U9jftZGn4G0
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        String refresh_token = "";

        try {

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/sns/oauth2/refresh_token";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("appid", Constants.APP_ID));
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
            System.out.println(json);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
            }
        }
    }


    @Test
    public void verifyWebUrl() throws Exception {
        // ovVfEs7WV5jXDA7Q-U9jftZGn4G0
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        String refresh_token = "";

        try {

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/sns/oauth2/refresh_token";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("appid", Constants.APP_ID));
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
            System.out.println(json);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
            }
        }
    }
}
