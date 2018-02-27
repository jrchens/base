package cn.jrry.wx.domain;

public class WxConfig implements  java.io.Serializable {
    private static final long serialVersionUID = -5056037472166280693L;
    //    appId: '', // 必填，公众号的唯一标识
//    timestamp: , // 必填，生成签名的时间戳
//    nonceStr: '', // 必填，生成签名的随机串
//    signature: '',// 必填，签名，见附录1
    private String appId;
    private Long timestamp;
    private String nonceStr;
    private String signature;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WxConfig{");
        sb.append("appId='").append(appId).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", nonceStr='").append(nonceStr).append('\'');
        sb.append(", signature='").append(signature).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WxConfig wxConfig = (WxConfig) o;

        if (appId != null ? !appId.equals(wxConfig.appId) : wxConfig.appId != null) return false;
        if (timestamp != null ? !timestamp.equals(wxConfig.timestamp) : wxConfig.timestamp != null) return false;
        if (nonceStr != null ? !nonceStr.equals(wxConfig.nonceStr) : wxConfig.nonceStr != null) return false;
        return signature != null ? signature.equals(wxConfig.signature) : wxConfig.signature == null;
    }

    @Override
    public int hashCode() {
        int result = appId != null ? appId.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (nonceStr != null ? nonceStr.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }

    public String getAppId() {

        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
