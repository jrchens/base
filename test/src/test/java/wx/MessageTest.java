package wx;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
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

import java.util.List;

public class MessageTest {
    private static final Logger logger = LoggerFactory.getLogger(MessageTest.class);

    @Test
    public void toXml() throws Exception {
        try {
            Message message = new Message();
            message.setToUserName("appid");
            message.setFromUserName("openid");
            message.setCreateTime(1516711592L);
            message.setMsgType("text");
            message.setContent("text content");
            message.setMsgId(15921516711592L);

            XStream xs = new XStream();
            xs.alias("xml",Message.class);
            String xml = xs.toXML(message);
            logger.info("xml: {}",xml);
        } finally {
        }
    }




    @Test
    public void fromXml() throws Exception {
        try {
//            String xml = "<xml>\n" +
//                    "  <toUserName>appid</toUserName>\n" +
//                    "  <fromUserName>openid</fromUserName>\n" +
//                    "  <createTime>1516711592</createTime>\n" +
//                    "  <msgType>text</msgType>\n" +
//                    "  <content>text content</content>\n" +
//                    "  <msgId>15921516711592</msgId>\n" +
//                    "</xml>";
            String xml = "<xml><URL><![CDATA[http://smejr.gov.cn/hdtyi-wx/]]></URL><ToUserName><![CDATA[jshdtyi]]></ToUserName><FromUserName><![CDATA[oFk9j1Z8B1t71eqvlvyoBVPI0uMY]]></FromUserName><CreateTime>1516711592</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[oFk9j1Z8B1t71eqvlvyoBVPI0uMY]]></Content><MsgId>1516711592</MsgId></xml>\n";
            XStream xs = new XStream();
            xs.ignoreUnknownElements();
            xs.processAnnotations(Message.class);
//            xs.alias("xml",Message.class);
            logger.info("message obj : {}",xs.fromXML(xml));
        } finally {
        }
    }
}

@XStreamAlias(value = "xml")
class Message implements java.io.Serializable {

    private static final long serialVersionUID = -4092269686283813897L;

    @XStreamAlias(value = "ToUserName")
    private String toUserName;
    @XStreamAlias(value = "FromUserName")
    private String fromUserName;
    @XStreamAlias(value = "CreateTime")
    private Long createTime;
    @XStreamAlias(value = "MsgType")
    private String msgType;
    @XStreamAlias(value = "Content")
    private String content;
    @XStreamAlias(value = "MsgId")
//    @XStreamOmitField // 忽略
//    @XStreamImplicit // for List
    private Long msgId ;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("toUserName='").append(toUserName).append('\'');
        sb.append(", fromUserName='").append(fromUserName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", msgType='").append(msgType).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", msgId=").append(msgId);
        sb.append('}');
        return sb.toString();
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }
}
