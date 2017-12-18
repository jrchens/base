package apache.httpclient;

import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

public class URIBuilderTest {

    private static final Logger logger = LoggerFactory.getLogger(URIBuilderTest.class);
    private static final String appId = "wx00979ffb52ef6c4f";

    @Test
    public void testBuilder() {

        try {

            // String scheme = "https";
            // String host = "open.weixin.qq.com";
            // String path = "/connect/oauth2/authorize";
            // List<NameValuePair> nvps = Lists.newArrayList();
            // nvps.add(new BasicNameValuePair("grant_type",
            // "client_credential"));
            // nvps.add(new BasicNameValuePair("appid", "{APPID}"));
            // nvps.add(new BasicNameValuePair("secret", "{SECRET}"));

            // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=
            // code&scope=snsapi_userinfo&state=STATE#wechat_redirect
            String scheme = "https";
            String host = "open.weixin.qq.com";
            String path = "/connect/oauth2/authorize";
            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("appid", appId));
            nvps.add(new BasicNameValuePair("redirect_uri", "http://wx.hdtyi.com/oauth2/wx"));
            nvps.add(new BasicNameValuePair("response_type", "code"));
            nvps.add(new BasicNameValuePair("scope", "snsapi_userinfo"));
            nvps.add(new BasicNameValuePair("state", "1"));
            String fragment = "wechat_redirect";

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);
            builder.setFragment(fragment);

            logger.info("{}", builder.build().toString());

            String openid = "openid";
            String username = "username";
            String password = "";
            builder = new URIBuilder("/index");
            builder.addParameter("openid", openid);
            builder.addParameter("username", username);
            builder.addParameter("password", password);
            String url = builder.build().toString();

            logger.info("{}", url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void urlEncode() throws Exception {
        String uri = "http://smejr.gov.cn/wx/bdc/";
        logger.info("{}", URLEncoder.encode(uri, "UTF-8"));
    }

    @Test
    public void urlFormat() throws Exception {
        String appid = "FF58CEAD0C9E440795D8A851DA3A26B3";
        String url = URLEncoder.encode("http://smejr.gov.cn/wx/bdc/", "UTF-8");
        String state = "1";
        String res = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect", appid, url, state);
        logger.info("{}", res);
    }


    //
}
