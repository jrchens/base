package apache.httpclient;

import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class HttpClientTest {
    private final static Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

    @Test
    public void testGetJSON() throws Exception {

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("http://192.168.201.112/simple/test");
            httpget.setHeader("content-type", "application/json;charset=UTF-8");
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement ele = parser.parse(result);
            JsonArray jsonArray = ele.getAsJsonArray();
            Iterator<JsonElement> it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement e = it.next();
                Map<String, Object> map = gson.fromJson(e, new TypeToken<Map<String, Object>>() {
                }.getType());
                logger.info("row: {}", map);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (Exception e2) {
            }
        }
    }

    @Test
    public void testDateString() throws Exception {
        Date now = new Date(System.currentTimeMillis());
        // 11 Dec 2017 06:55:10 GMT
        // Tue, 05 May 2015 06:11:34 GMT
        // Mon Dec 11 14:53:48 CST 2017

        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);


        String source = "Sat, 16 Dec 2017 02:38:44 GMT";
        Date date = format.parse(source);
        System.out.println(date);

        format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(format.format(now));


//
        System.out.println(format.format(now));


        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        System.out.println(format.format(now));


        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.SIMPLIFIED_CHINESE);

        System.out.println(format.format(now));


    }


    @Test
    public void testSign() throws Exception {
        String string_to_sign = "POST /openapi/index.php?method=newOrder\n" +
                "Mon, 11 Dec 2017 15:27:10 GMT";
        String client_secret = "65bd39b97db32f409adb50d0e2dccd49";

        String hmacSha1 = Hashing.hmacSha1(client_secret.getBytes()).hashString(string_to_sign, Charsets.UTF_8).toString();

        String signature = BaseEncoding.base64().encode(hmacSha1.getBytes(Charsets.UTF_8));

        logger.info("string_to_sign:{}", string_to_sign);
        logger.info("client_secret:{}", client_secret);
        logger.info("hmacSha1:{}", hmacSha1);
        logger.info("signature:{}", signature); // LH test:WymO6w180DUmp91DaYiHY6Tt0rA=
        logger.info("signature as byte:{}", BaseEncoding.base64().encode(Hashing.hmacSha1(client_secret.getBytes()).hashString(string_to_sign, Charsets.UTF_8).asBytes())); // LH test:WymO6w180DUmp91DaYiHY6Tt0rA=
    }

    @Test
    public void testPOSTJSON() throws Exception {

        // 测试系统网址:http://test.lianhepiaowu.com
        // 测试账号信息：
        // parnterId：508
        // apiname：test
        // clientSecre：65bd39b97db32f409adb50d0e2dccd49


        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

        String date = format.format(now);

        String string_to_sign = "POST " + "/openapi/index.php?method=newOrder" + "\n" + date;
        String client_secret = "65bd39b97db32f409adb50d0e2dccd49";
        byte[] rawOutputSha1 = Hashing.hmacSha1(client_secret.getBytes()).hashString(string_to_sign, Charsets.UTF_8).asBytes();
        String signature = BaseEncoding.base64().encode(rawOutputSha1);
        String appname = "test";
        String authorization = "LH " + appname + ":" + signature;
        String partnerId = "508";
        String version = "2.1";


        logger.info("string_to_sign:{}", string_to_sign);
        logger.info("client_secret:{}", client_secret);
        logger.info("signature:{}", signature);
        logger.info("authorization:{}", authorization);
        logger.info("appname:{}", appname);
        logger.info("version:{}", version);
        logger.info("partnerId:{}", partnerId);


        // $string_to_sign = $method . ' ' . $uri . "\n" . $date;
        // $client_secret = self::$clientSecret;
        // $signature = base64_encode(hash_hmac('sha1', $string_to_sign,
        // $client_secret, true));
        // $authorization = 'LH ' . self::$apiName . ':' . $signature;

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpost = new HttpPost("http://test.lianhepiaowu.com/openapi/index.php?method=newOrder");
            httpost.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpost.setHeader("Date", date);
            httpost.setHeader("Version", version);
            httpost.setHeader("PartnerId", partnerId);
            httpost.setHeader("Authorization", authorization);


            Gson g = new Gson();

            Header[] headers = httpost.getAllHeaders();
            for (Header header : headers) {
                logger.info("{}:{}", header.getName(), header.getValue());
            }


            Map<String, Object> data = Maps.newLinkedHashMap();
            data.put("partnerId", partnerId);

            Map<String, Object> body = Maps.newLinkedHashMap();

            String orderId = "123"; //string",
            String payId = "456"; //string",
            String LHDealId = "1590"; //string",
            int sellPrice = 4; // 5,
            int quantity = 2; // 3,
            String travelDate = "2017-12-10";

            body.put("orderId", orderId);
            body.put("payId", payId);
            body.put("LHDealId", LHDealId);
            body.put("sellPrice", sellPrice);
            body.put("quantity", quantity);
            body.put("travelDate", travelDate);

            Map<String, Object> visitor = Maps.newLinkedHashMap();
            String name = "王二"; // 王二
            String mobile = "18888888888"; // 18888888888
            String address = "中国北京市胡说区侃大山街不靠谱小区6号楼001号"; // 中国北京市胡说区侃大山街不靠谱小区6号楼001号
            String zipcode = "100000"; // 100000
            String credentials = "";
            String email = "100000@163.com"; // 100000@163.com

            visitor.put("name", name);
            visitor.put("mobile", mobile);
            visitor.put("address", address);
            visitor.put("zipcode", zipcode);
            visitor.put("credentials", credentials);
            visitor.put("email", email);

            body.put("visitor", visitor);

            data.put("body", body);


//			{
//				  "partnerId": 1985,
//				  "body": {
//				    "orderId": "string",
//				    "payId": "string",
//				    "LHDealId": "string",
//				    "sellPrice": 5,
//				    "quantity": 3,
//				    "travelDate":"2016-4-13",
//				    "visitor": [
//				    	{
//				        "name": "王二",
//				        "mobile": "18888888888",
//				        "address": "中国北京市胡说区侃大山街不靠谱小区6号楼001号",
//				        "zipcode": "100000",
//				        "credentials":"string",
//				        "email": "100000@163.com"
//				      }
//				    ]
//				  }
//				}

            String reqEntity = g.toJson(data);
            logger.info("{}", reqEntity);

            StringEntity s = new StringEntity(reqEntity);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpost.setEntity(s);

            // $header = array(
            // "Content-Type: application/json; charset=utf-8",
            // "Date: " . $date,
            // "Version: " . self::$version,
            // "PartnerId: " . self::$partnerId,
            // "Authorization: " . $authorization,
            // );

            response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            logger.info("{}", result);

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement ele = parser.parse(result);
            JsonArray jsonArray = ele.getAsJsonArray();
            Iterator<JsonElement> it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement e = it.next();
                Map<String, Object> map = gson.fromJson(e, new TypeToken<Map<String, Object>>() {
                }.getType());
                logger.info("row: {}", map);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (Exception e2) {
            }
        }
    }


    @Test
    public void testForm() throws Exception {

        logger.info("test form post");
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {

            Map<String, Object> params = Maps.newLinkedHashMap();
//                params.put("openid_list", openid_list);
//                params.put("tagid", wxConfigService.getString(ConfigConstants.WX_DEFAULT_TAG_KEY));


            String scheme = "http";
            String host = "api.chanyoo.cn";
            String path = "/utf8/interface/send_sms.aspx";

            String receiver = "13376170091";
            String content = String.format("您的手机号：%s，注册验证码：%s，一天内提交有效！【句容热线网】",receiver,RandomStringUtils.randomNumeric(6));

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("username", "jrhotbbs"));
            nvps.add(new BasicNameValuePair("password", "Jrhot87285699"));
            nvps.add(new BasicNameValuePair("receiver", receiver));
            nvps.add(new BasicNameValuePair("content", content));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charsets.UTF_8.name()));

            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            logger.info("test form post response {}", result);

            Map<String,String> rs = WXPayUtil.xmlToMap(result);

            if(Integer.parseInt(rs.get("result")) >= 0){

            }



        } catch (Exception ex) {
            logger.error(" form post error {}", ex);
            throw new RuntimeException(" form post error", ex);
        } finally {
            try {
                if (closeableHttpResponse != null)
                    closeableHttpResponse.close();
                if (closeableHttpClient != null)
                    closeableHttpClient.close();
            } catch (Exception e) {
                logger.error("close error {}", e);
            }
        }
    }

}
