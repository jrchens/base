package wx;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class MediaTest {
    private static final Logger logger = LoggerFactory.getLogger(MediaTest.class);
    private static final Gson gson = new Gson();

    @Test
    public void list() throws Exception {
        // https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738734
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            String type = "news"; /// 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
            int offset = 0;
            int count = 20;
            String access_token = "BWXnIIAnNmvKfeLdlWr4u6UJSOuUrPNqn9GWMiZ5bvzNYl1ESsj0k82sJe5E83ZXdHi797rIH8lhxAXzyURccMph2cdbOYPVGpG5C0ruokRqvru2kRKQmEKD-thRqXmfXABbADASVW";

            // https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/material/batchget_material";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", access_token));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            logger.info("url: {}", builder.build().toString());


            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Map<String, Object> params = Maps.newLinkedHashMap();
            params.put("type", type);
            params.put("offset", offset);
            params.put("count", count);

            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            // errcode,errmsg
//            参数	描述
//            total_count	该类型的素材的总数
//            item_count	本次调用获取的素材的数量
//            title	图文消息的标题
//            thumb_media_id	图文消息的封面图片素材id（必须是永久mediaID）
//            show_cover_pic	是否显示封面，0为false，即不显示，1为true，即显示
//            author	作者
//            digest	图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
//            content	图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
//            url	图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL
//            content_source_url	图文消息的原文地址，即点击“阅读原文”后的URL
//            update_time		这篇图文消息素材的最后更新时间
//            name	文件名称

            String json = EntityUtils.toString(entity, "UTF-8");

            logger.info("json: {}", json);
        } finally {
            response.close();
            httpclient.close();
        }
    }
}
