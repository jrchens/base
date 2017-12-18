package apache.httpclient;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;

public class HttpClientTest2 {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

    private static final String JRHOT_TICKET_API_URI = "http://www.juyingzhiye.com/service/open";
    private static final String JRHOT_TICKET_API_KEY = "e0170a469c51401792118f7be402386a";// 密钥
    private static final String JRHOT_TICKET_SYSTEM_ID = "6022";// 系统ID
    private static final String JRHOT_TICKET_DISTRIBUTION_ACCOUNT = "6136";// 分销帐号

    public void testAlive() {
        try {
            String testAlive = "/testAlive";
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(JRHOT_TICKET_API_URI + testAlive);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "UTF-8");
            String dir = System.getProperty("java.io.tmpdir");
            logger.info("dir: {}", dir);
            FileCopyUtils.copy(html.getBytes(), new File(dir, "testAlive.text"));
            logger.info("text/html: {}", html);

            response.close();
            httpclient.close();

        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void testGet() {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://www.baidu.com");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "UTF-8");
            String dir = System.getProperty("java.io.tmpdir");
            logger.info("dir: {}", dir);
            FileCopyUtils.copy(html.getBytes(), new File(dir, "baidu.html"));
            logger.info("text/html: {}", html);

            response.close();
            httpclient.close();

        } catch (Exception e) {
            logger.error("", e);
        }
    }

    //

    @Test
    public void testPost() {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://221.230.26.161:8443/estateplat-public/rest/v2/bdcbjjd/20160626220055033");
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "UTF-8");

            Gson gson = new Gson();
//            Entity obj = gson.fromJson(html,Entity.class);
//
//            logger.info("html:{}",obj.getApplyactivity());
//            String dir = System.getProperty("java.io.tmpdir");
//            logger.info("dir: {}", dir);
//            FileCopyUtils.copy(html.getBytes(), new File(dir, "baidu.html"));
//            logger.info("text/html: {}", html);

            response.close();
            httpclient.close();

        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
