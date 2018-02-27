package wx;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

public class MenuTest {
    private static final Logger logger = LoggerFactory.getLogger(MenuTest.class);

    @Test
    public void save() throws Exception {

        String url = URLEncoder.encode("http://nsh-hb.jrhot.com/pay", Charsets.UTF_8.name());

        String res = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0fd9ea5b91f95791&redirect_uri=%s&response_type=code&scope=snsapi_base&state=8136#wechat_redirect",url);
        logger.info("url:{}",res);

        // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0fd9ea5b91f95791&redirect_uri=http%3A%2F%2Fnsh-hb.jrhot.com%2Fpay&response_type=code&scope=snsapi_base&state=8136#wechat_redirect

//        "type":"click",
//                "name":"今日歌曲",
//                "key":"V1001_TODAY_MUSIC"
//        url
        // https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN


    }
}
