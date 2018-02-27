import cn.jrry.wx.util.Constants;
import com.google.common.base.Charsets;
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
import org.junit.Test;

import java.net.URLEncoder;
import java.util.List;

public class WxTest {

    @Test
    public void getAccessToken() throws Exception{
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
    public void getWebAccessToken() throws Exception {
        try {
            // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri=https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect
            String scheme = "https";
            String host = "open.weixin.qq.com";
            String path = "/connect/oauth2/authorize";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("appid", Constants.APP_ID));
            nvps.add(new BasicNameValuePair("redirect_uri", URLEncoder.encode("https://wz.jrhot.com/api.php?id=4",Charsets.UTF_8.name())));
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
    public void refreshWebAccessToken() throws Exception{
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
    public void verifyWebUrl() throws Exception{
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
