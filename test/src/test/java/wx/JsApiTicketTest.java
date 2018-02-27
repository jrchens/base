package wx;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class JsApiTicketTest {
    private static final Logger logger = LoggerFactory.getLogger(JsApiTicketTest.class);

    /**
     * <pre>
     * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void get() throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            String access_token = "gg3Cc_TqAJIIWnyPBUdt9VD5SUCb0I_BtDvX2rxS6rz0JfxY8In1kwLSI60PMDEOWZaTJeDy30ab17kBM8-0l26vAkTvPmo_gauxFToJWipGvmhe0m6TRX75fpS1w1gXLKQeAIADCI";

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/ticket/getticket";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", access_token));
            nvps.add(new BasicNameValuePair("type", "jsapi"));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get jsapi_ticket response json : {}", json);

            // {"access_token":"C7D0CQ_jZoG8ZxpUwo5fKhcTh5DgQgeXPzTPu_m5tORYx76T4TzyKSYMGQpgZMHR-EAFw0X00UTxmtFNyiUgrxnXmAyr6MIyVrVmE2fz1hnqA37-o_GOlPhMDGzle2o5ESPaAGAWKH","expires_in":7200}
        } finally {
            response.close();
            httpclient.close();
        }
    }

    // HoagFKDcsGMVCIY2vOjf9to9DRFzSjGPLZSBfs4oKXw867XEzv6cCCMbLLb8r78OYFkTutiNpcI16Rou3oPE6g


    @Test
    public void signature() throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {

            String[] params = {
            "noncestr=Wm3WZYTPz0wzccnW",
            "jsapi_ticket=HoagFKDcsGMVCIY2vOjf9to9DRFzSjGPLZSBfs4oKXzUSXpSdPfF2u3VsUopbzrNzl8cJsCDjeN9UYAjBaCz6w",
            "timestamp=1414587457",
            "url=http://smejr.gov.cn/hdtyi-wx/mobile/engineering-progress"
};

            // jsapi_ticket=HoagFKDcsGMVCIY2vOjf9to9DRFzSjGPLZSBfs4oKXzUSXpSdPfF2u3VsUopbzrNzl8cJsCDjeN9UYAjBaCz6wnoncestr=Wm3WZYTPz0wzccnWtimestamp=1414587457url=http://smejr.gov.cn/hdtyi-wx/mobile/engineering-progress
            // jsapi_ticket=HoagFKDcsGMVCIY2vOjf9to9DRFzSjGPLZSBfs4oKXzUSXpSdPfF2u3VsUopbzrNzl8cJsCDjeN9UYAjBaCz6w&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://smejr.gov.cn/hdtyi-wx/mobile/engineering-progress

            // c4e7af4e8afc168759dd61bb6f40e754437ed7dd
            // ca4f3ce877560f1ba9e02fc436d581204ebfd2af

            Arrays.sort(params);

            String str = Joiner.on("&").join(params);

            logger.info("string1 {}",str);
            logger.info("signature {}",Hashing.sha1().hashString(str, Charsets.UTF_8).toString());

//            String access_token = "gg3Cc_TqAJIIWnyPBUdt9VD5SUCb0I_BtDvX2rxS6rz0JfxY8In1kwLSI60PMDEOWZaTJeDy30ab17kBM8-0l26vAkTvPmo_gauxFToJWipGvmhe0m6TRX75fpS1w1gXLKQeAIADCI";
//
//            String scheme = "https";
//            String host = "api.weixin.qq.com";
//            String path = "/cgi-bin/ticket/getticket";
//
//            List<NameValuePair> nvps = Lists.newArrayList();
//            nvps.add(new BasicNameValuePair("access_token", access_token));
//            nvps.add(new BasicNameValuePair("type", "jsapi"));
//
//            URIBuilder builder = new URIBuilder();
//            builder.setScheme(scheme).setHost(host).setPath(path);
//            builder.addParameters(nvps);
//
//            httpclient = HttpClients.createDefault();
//            HttpGet httpGet = new HttpGet(builder.build().toString());
//
//            response = httpclient.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//
//            String json = EntityUtils.toString(entity, "UTF-8");
//            logger.info("get jsapi_ticket response json : {}", json);

            // {"access_token":"C7D0CQ_jZoG8ZxpUwo5fKhcTh5DgQgeXPzTPu_m5tORYx76T4TzyKSYMGQpgZMHR-EAFw0X00UTxmtFNyiUgrxnXmAyr6MIyVrVmE2fz1hnqA37-o_GOlPhMDGzle2o5ESPaAGAWKH","expires_in":7200}
        } finally {
//            response.close();
//            httpclient.close();
        }
    }
}
