package cn.jrry.wx.service.impl;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.common.exception.ServiceException;
import cn.jrry.wx.domain.WxAccessToken;
import cn.jrry.wx.domain.WxUserInfo;
import cn.jrry.wx.mapper.WxUserInfoMapper;
import cn.jrry.wx.service.WxAccessTokenService;
import cn.jrry.wx.service.WxUserInfoService;
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
import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WxUserInfoServiceImpl implements WxUserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(WxUserInfoService.class);
    private static final Gson gson = new Gson();
    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;
    @Autowired
    private WxAccessTokenService wxAccessTokenService;
    @Autowired
    private ConfigService configService;
    private static final String APP_ID_KEY = "76900565-ac11-4a9f-a990-1475db91db2f";

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return wxUserInfoMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(WxUserInfo record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCruser(record.getOpenid());
            record.setCrtime(now);

            int aff = wxUserInfoMapper.insert(record);

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxUserInfo selectByPrimaryKey(Long id) {
        try {
            return wxUserInfoMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxUserInfo> selectAll() {
        try {
            return wxUserInfoMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(WxUserInfo record) {
        try {
//            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(record.getOpenid());
            record.setMdtime(now);
            return wxUserInfoMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int deleteByOpenid(String openid) {
        try {
            return wxUserInfoMapper.deleteByOpenid(openid);
        } catch (Exception ex) {
            logger.error("deleteByOpenid error {}{}{}", openid, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return wxUserInfoMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxUserInfo> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return wxUserInfoMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxUserInfo getUserInfo(String openid) {
        WxUserInfo wxUserInfo = null;
                CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {

            String appid = configService.getString(APP_ID_KEY);
            String accessToken = wxAccessTokenService.getAccessToken();

            // https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/user/info";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", accessToken));
            nvps.add(new BasicNameValuePair("openid", openid));
            nvps.add(new BasicNameValuePair("lang", "zh_CN"));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);


            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build().toString());

            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();


            // errcode,errmsg
            // {
//                    "subscribe": 1,
//                    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
//                    "nickname": "Band",
//                    "sex": 1,
//                    "language": "zh_CN",
//                    "city": "广州",
//                    "province": "广东",
//                    "country": "中国",
//                    "headimgurl":"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
//                    "subscribe_time": 1382694957,
//                    "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
//                    "remark": "",
//                    "groupid": 0,
//                    "tagid_list":[128,2]
//
//        }

//              `openid` varchar(50) NOT NULL DEFAULT '',
//              `nickname` varchar(50) DEFAULT NULL,
//              `sex` varchar(50) DEFAULT NULL,
//              `province` varchar(50) DEFAULT NULL,
//              `city` varchar(50) DEFAULT NULL,
//              `country` varchar(50) DEFAULT NULL,
//              `headimgurl` varchar(500) DEFAULT NULL,
//              `privilege` text,
//              `unionid` varchar(50) DEFAULT NULL,


            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("get user info response json : {}", json);


            wxUserInfo = gson.fromJson(json, WxUserInfo.class);
            Integer errcode = wxUserInfo.getErrcode();
            if (errcode == null || errcode.intValue() == 0) {
                logger.debug("get user info success {}", json);
            } else {
                logger.error("get user info error {}", json);
            }
        } catch (Exception ex) {
            logger.error("generate error {}", ex);
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (Exception e) {
                logger.error("close error {}", e);
            }
        }

        return wxUserInfo;

    }
}
