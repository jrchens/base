package cn.jrry.wx.controller;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.wx.domain.WxMessage;
import cn.jrry.wx.domain.WxUserInfo;
import cn.jrry.wx.domain.WxUserInfoTagRelation;
import cn.jrry.wx.service.WxInvokeService;
import cn.jrry.wx.service.WxUserInfoService;
import cn.jrry.wx.service.WxUserInfoTagRelationService;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;

@Controller(value = "wxMessageController")
@RequestMapping(value = "")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private static final String UPLOAD_PATH_KEY = "34c10bf0-fe3b-4ff0-846d-66acf338e7fb";
    private static final String TOKEN_KEY = "7f2dbe5c-d201-4225-a1d0-777f43c8f3bc";
    private static final String WX_DEFAULT_USER_TAG_KEY = "356bf3a4-418c-4bf3-9e33-c1c3446aa751";
    @Autowired
    private ConfigService configService;
    @Autowired
    private WxInvokeService wxInvokeService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private WxUserInfoTagRelationService wxUserInfoTagRelationService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = {"text/plain; charset=UTF-8"})
    public String verifyUrl(@RequestParam(required = false, defaultValue = "") String signature, @RequestParam(required = false, defaultValue = "") String timestamp, @RequestParam(required = false, defaultValue = "") String nonce, @RequestParam(required = false, defaultValue = "") String echostr) {

        //    signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
//    timestamp	时间戳
//    nonce	随机数
//    echostr	随机字符串
        // https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319

        String token = configService.getString(TOKEN_KEY);
        logger.info("signature {},  timestamp {}, nonce {}, echostr {} , token {} ", signature, timestamp, nonce, echostr, token);

        String[] sort = {token, timestamp, nonce};
        Arrays.sort(sort);

        String str = Joiner.on("").join(sort);
        String hashString = Hashing.sha1().hashString(str, Charsets.UTF_8).toString();
        if (hashString.equals(signature)) {
            return echostr;
        }
        return "error";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
    public String message(HttpServletRequest request) {
        logger.info("message");
        String res = "";
        InputStream is = null;

//        CloseableHttpClient closeableHttpClient = null;
//        CloseableHttpResponse closeableHttpResponse = null;


        try {
            XStream xStream = new XStream();
            xStream.ignoreUnknownElements();
            xStream.processAnnotations(WxMessage.class);

            is = request.getInputStream();
            byte[] ba = ByteStreams.toByteArray(is);
            String xml = new String(ba);
            logger.info("origin xml : {}", xml);

            WxMessage message = (WxMessage) xStream.fromXML(xml);

            String msgType = message.getMsgType();

            logger.info("message type : {}", msgType);

            if ("event".equals(msgType)) {
                String event = message.getEvent();
                // 关注/取消关注事件
                //    参数	描述
                //    ToUserName	开发者微信号
                //    FromUserName	发送方帐号（一个OpenID）
                //    CreateTime	消息创建时间 （整型）
                //    MsgType	消息类型，event
                //    Event	事件类型，subscribe(订阅)、unsubscribe(取消订阅)
                String openid = message.getFromUserName();
                if ("subscribe".equals(event)) {


                    WxUserInfo wxUserInfo = wxInvokeService.getUserInfo(openid);

                    // https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN




//                    String headimgurl = wxUserInfo.getHeadimgurl();
//                    closeableHttpClient = HttpClients.createDefault();
//                    HttpGet httpGet = new HttpGet(headimgurl);
//                    closeableHttpResponse = closeableHttpClient.execute(httpGet);
//                    int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
//                    if (statusCode == HttpStatus.SC_OK) {
//                        HttpEntity entity = closeableHttpResponse.getEntity();
//                        String uploadPath = configService.getString(UPLOAD_PATH_KEY);
//                        String fileName = openid.concat(".jpg");
//                        File dest = new File(uploadPath, fileName);
//                        Files.createParentDirs(dest);
//                        FileCopyUtils.copy(entity.getContent(), new FileOutputStream(dest));
//                        wxUserInfo.setHeadimgurl(fileName);
//                    }

                    wxUserInfoService.insert(wxUserInfo);

                    Long tagId = configService.getLong(WX_DEFAULT_USER_TAG_KEY);
                    WxUserInfoTagRelation wxUserInfoTagRelation = new WxUserInfoTagRelation();
                    wxUserInfoTagRelation.setOpenid(openid);
                    wxUserInfoTagRelation.setTag_id(tagId);
                    wxUserInfoTagRelationService.insert(wxUserInfoTagRelation);

                } else if ("unsubscribe".equals(event)) {
                    WxUserInfo wxUserInfo = wxUserInfoService.selectByOpenid(openid);
                    wxUserInfoTagRelationService.deleteByOpenid(wxUserInfo.getOpenid());
                    wxUserInfoService.deleteByPrimaryKey(wxUserInfo.getId());
                }
                logger.info("event type : {}", event);
            } else if ("text".equals(msgType)) {
//                参数	描述
//                ToUserName	开发者微信号
//                FromUserName	发送方帐号（一个OpenID）
//                CreateTime	消息创建时间 （整型）
//                MsgType	text
//                Content	文本消息内容
//                MsgId	消息id，64位整型
            }

        } catch (Exception e) {
            logger.error("wx message process error {}", e);
        } finally {
            try {
                is.close();
//                closeableHttpResponse.close();
//                closeableHttpClient.close();
            } catch (Exception ex) {

            }

        }
        return res;
    }


}
