package cn.jrry.wx.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "xml")
public class WxMessage implements java.io.Serializable {
    private static final long serialVersionUID = 1169263631111968485L;

    @XStreamAlias(value = "ToUserName")
    private String toUserName;
    @XStreamAlias(value = "FromUserName")
    private String fromUserName;
    @XStreamAlias(value = "CreateTime")
    private Integer createTime;
    @XStreamAlias(value = "MsgType")
    private String msgType;
    @XStreamAlias(value = "Event")
    private String event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WxMessage wxMessage = (WxMessage) o;

        if (toUserName != null ? !toUserName.equals(wxMessage.toUserName) : wxMessage.toUserName != null) return false;
        if (fromUserName != null ? !fromUserName.equals(wxMessage.fromUserName) : wxMessage.fromUserName != null)
            return false;
        if (createTime != null ? !createTime.equals(wxMessage.createTime) : wxMessage.createTime != null) return false;
        if (msgType != null ? !msgType.equals(wxMessage.msgType) : wxMessage.msgType != null) return false;
        return event != null ? event.equals(wxMessage.event) : wxMessage.event == null;
    }

    @Override
    public int hashCode() {
        int result = toUserName != null ? toUserName.hashCode() : 0;
        result = 31 * result + (fromUserName != null ? fromUserName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (msgType != null ? msgType.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WxMessage{");
        sb.append("toUserName='").append(toUserName).append('\'');
        sb.append(", fromUserName='").append(fromUserName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", msgType='").append(msgType).append('\'');
        sb.append(", event='").append(event).append('\'');
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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
