package cn.jrry.wx.service.impl;

import cn.jrry.common.exception.ServiceException;
import cn.jrry.common.exception.WxInvokeException;
import cn.jrry.wx.domain.WxResponse;
import cn.jrry.wx.domain.WxTag;
import cn.jrry.wx.mapper.WxTagMapper;
import cn.jrry.wx.service.WxAccessTokenService;
import cn.jrry.wx.service.WxTagService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

@Service
public class WxTagServiceImpl implements WxTagService {
    private static final Logger logger = LoggerFactory.getLogger(WxTagService.class);
    private static final Gson gson = new Gson();

    @Autowired
    private WxTagMapper wxTagMapper;
    @Autowired
    private WxAccessTokenService wxAccessTokenService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            // https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/delete";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", wxAccessTokenService.getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Map<String, Object> params = Maps.newLinkedHashMap();
            Map<String, Object> tag = Maps.newHashMap();
            tag.put("id", id);
            params.put("tag", tag);

            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx create tag response {}", json);
            // {   "tag":{ "id":134,//标签id "name":"广东"   } }

            WxResponse result = gson.fromJson(json, WxResponse.class);

            if(result.getErrcode() == 0){
                return wxTagMapper.deleteByPrimaryKey(id);
            }else {
                throw new WxInvokeException(String.valueOf(result.getErrcode()));
            }



        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(WxTag record) {
        try {
            // https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/create";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", wxAccessTokenService.getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Map<String, Object> params = Maps.newLinkedHashMap();
            Map<String, Object> tag = Maps.newHashMap();
            tag.put("name", record.getName());
            params.put("tag", tag);

            httpPost.setEntity(new StringEntity(gson.toJson(params), ContentType.APPLICATION_JSON));

            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx create tag response {}", json);
            // {   "tag":{ "id":134,//标签id "name":"广东"   } }

            Map<String, Map<String, Object>> result = gson.fromJson(json, new TypeToken<Map<String, Map<String, Object>>>() {
            }.getType());

            Object errcode = result.get("errcode");

            if (null == errcode) {
                NumberFormat numberFormat = new DecimalFormat("0");
                record.setId(numberFormat.parse(result.get("tag").get("id").toString()).longValue());

                String user = SecurityUtils.getSubject().getPrincipal().toString();
                Timestamp now = new Timestamp(System.currentTimeMillis());
                record.setCruser(user);
                record.setCrtime(now);

                int aff = wxTagMapper.insert(record);

                return aff;
            } else {
                throw new WxInvokeException(errcode.toString());
            }
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public WxTag selectByPrimaryKey(Long id) {
        try {
            return wxTagMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxTag> selectAll() {
        try {
            return wxTagMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(WxTag record) {
        try {

            // https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN
            String scheme = "https";
            String host = "api.weixin.qq.com";
            String path = "/cgi-bin/tags/update";

            List<NameValuePair> nvps = Lists.newArrayList();
            nvps.add(new BasicNameValuePair("access_token", wxAccessTokenService.getAccessToken()));

            URIBuilder builder = new URIBuilder();
            builder.setScheme(scheme).setHost(host).setPath(path);
            builder.addParameters(nvps);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.build().toString());

            Map<String, Object> params = Maps.newLinkedHashMap();
            Map<String, Object> tag = Maps.newHashMap();
            tag.put("id", record.getId());
            tag.put("name", record.getName());
            params.put("tag", tag);
            String reqData = gson.toJson(params);

            httpPost.setEntity(new StringEntity(reqData, ContentType.APPLICATION_JSON));

            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            logger.info("wx update tag response {}", json);
            // {   "errcode":0,   "errmsg":"ok" }

            WxResponse result = gson.fromJson(json, WxResponse.class);

            if (result.getErrcode() == 0) {
                String user = SecurityUtils.getSubject().getPrincipal().toString();
                Timestamp now = new Timestamp(System.currentTimeMillis());
                record.setMduser(user);
                record.setMdtime(now);
                return wxTagMapper.updateByPrimaryKey(record);
            } else {
                throw new WxInvokeException(String.valueOf(result.getErrcode()));
            }

        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int removeByPrimaryKey(WxTag record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return wxTagMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return wxTagMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<WxTag> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return wxTagMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}
