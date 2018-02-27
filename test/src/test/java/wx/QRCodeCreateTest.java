package wx;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QRCodeCreateTest {
    private static final Logger logger = LoggerFactory.getLogger(QRCodeCreateTest.class);

    @Test
    public void testQRCodeCreate() throws Exception {

        Map<String, Object> scene = Maps.newHashMap();
        scene.put("scene_str", "subscribe");
        Map<String, Object> action_info = Maps.newHashMap();
        action_info.put("scene", scene);

        Map<String, Object> params = Maps.newHashMap();
        params.put("action_name", "QR_LIMIT_STR_SCENE");
        params.put("action_info", action_info);


        // {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
        // {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}

        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token

            String access_token = "6_zXk2QSLv4-VjysNZ8r8V5NllA6lD8-kun8NUlhHSUhsekHd_QWZ8DG_A_aW4j_2UrDEw0tbY2UEojB_npS48QLGFD4GlBl_5EfmV1IvEZsSVjypsWIPRy41zyu5mjIo6YCeNhBRTCvLlOT5MQPBdAJAFEQ"; // store in database

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/qrcode/create";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", access_token));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Gson gson = new Gson();

            String reqJson = gson.toJson(params);
            logger.info("qrcode/create request json {}", reqJson);
            httpPost.setEntity(new StringEntity(reqJson, ContentType.APPLICATION_JSON));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("qrcode/create response {}", json);

            logger.info("ticket encode {}", URLEncoder.encode("gQGY7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAycDdQZnBlWm5iNFAxMDAwMHcwN2QAAgQ_bnhaAwQAAAAA",Charsets.UTF_8.name()));

            // {"ticket":"gQGY7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAycDdQZnBlWm5iNFAxMDAwMHcwN2QAAgQ_bnhaAwQAAAAA","url":"http:\/\/weixin.qq.com\/q\/02p7PfpeZnb4P10000w07d"}
        } catch (Exception ex) {
            logger.error("menu/create {}{}{}", params, System.lineSeparator(), ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                if (closeableHttpResponse != null)
                    closeableHttpResponse.close();
                if (closeableHttpClient != null)
                    closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }
    }


    @Test
    public void testShowQRCode() throws Exception {

        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET

            String ticket = "gQGY7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAycDdQZnBlWm5iNFAxMDAwMHcwN2QAAgQ_bnhaAwQAAAAA";

            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/showqrcode";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("ticket", URLEncoder.encode(ticket, Charsets.UTF_8.name())));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);
            String uri = builder.build().toString();
            logger.error("request uri {}", uri);

            closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);

            closeableHttpResponse = closeableHttpClient.execute(httpGet);

            int code = closeableHttpResponse.getStatusLine().getStatusCode();
            if (code == 200) {
                Header[] headers = closeableHttpResponse.getHeaders("Content-disposition");
                String filename = null;
                for (int i = 0; i < headers.length; i++) {
                    filename = headers[i].getValue();
                    int idx = filename.indexOf("filename=");
                    filename = filename.substring(idx + 9);
                    filename = filename.replaceAll("\"", "");
                    filename = UUID.randomUUID().toString().replaceAll("-", "").concat(".").concat(Files.getFileExtension(filename));
                }
                HttpEntity entity = closeableHttpResponse.getEntity();

                FileCopyUtils.copy(EntityUtils.toByteArray(entity), new FileOutputStream(new File("/usr/local/var/tmp/o.jpg")));
            }

            // {"ticket":"gQGY7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAycDdQZnBlWm5iNFAxMDAwMHcwN2QAAgQ_bnhaAwQAAAAA","url":"http:\/\/weixin.qq.com\/q\/02p7PfpeZnb4P10000w07d"}

        } catch (Exception ex) {
            logger.error("showqrcode {}", ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                if (closeableHttpResponse != null)
                    closeableHttpResponse.close();
                if (closeableHttpClient != null)
                    closeableHttpClient.close();
            } catch (Exception ex) {
                logger.error("close error {}", ex);
            }
        }


    }
}
