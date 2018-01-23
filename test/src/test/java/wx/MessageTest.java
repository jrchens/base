package wx;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
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
            String xml = "<xml>\n" +
                    "  <toUserName>appid</toUserName>\n" +
                    "  <fromUserName>openid</fromUserName>\n" +
                    "  <createTime>1516711592</createTime>\n" +
                    "  <msgType>text</msgType>\n" +
                    "  <content>text content</content>\n" +
                    "  <msgId>15921516711592</msgId>\n" +
                    "</xml>";
            XStream xs = new XStream();
            xs.alias("xml",Message.class);
            logger.info("message obj : {}",xs.fromXML(xml));
        } finally {
        }
    }
}

class Message implements java.io.Serializable {

    private static final long serialVersionUID = -4092269686283813897L;
    private String toUserName;
    private String fromUserName;
    private Long createTime;
    private String msgType;
    private String content;
    private Long msgId;

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
