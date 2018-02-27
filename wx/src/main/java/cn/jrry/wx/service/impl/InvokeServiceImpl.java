package cn.jrry.wx.service.impl;

import cn.jrry.wx.service.InvokeService;
import cn.jrry.wx.util.Constants;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class InvokeServiceImpl implements InvokeService {

    public String getJSAPITicket(){
        return null;
    }

    @Override
    public Map<String, Object> verifyWebUrl(String url) {
        Map<String,Object> result = Maps.newHashMap();

        Long timestamp = System.currentTimeMillis() / 1000;
        String nonceStr = RandomStringUtils.randomAlphanumeric(16);

        String[] params = {
                "jsapi_ticket=".concat(getJSAPITicket()),
                "noncestr=".concat(nonceStr),
                "timestamp=".concat(String.valueOf(timestamp)),
                "url=".concat(url)
        };

        Arrays.sort(params);

        String str = Joiner.on("&").join(params);
        String signature = Hashing.sha1().hashString(str, Charsets.UTF_8).toString();

        result.put("appid", Constants.APP_ID);
        result.put("timestamp",timestamp);
        result.put("nonceStr",nonceStr);
        result.put("signature",signature);

        return result;
    }
}
