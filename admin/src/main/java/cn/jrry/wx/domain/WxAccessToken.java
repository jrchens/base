package cn.jrry.wx.domain;

import java.util.Date;

// https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
//        返回码	说明
//        -1	系统繁忙，此时请开发者稍候再试
//        0	请求成功
//        40001	AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性
//        40002	请确保grant_type字段值为client_credential
//        40164	调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置


public class WxAccessToken implements java.io.Serializable {
    private static final long serialVersionUID = 3881045300643991128L;
    private Integer id;

    private String access_token;

    private Integer expires_in;

    private Date expires_time;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WxAccessToken{");
        sb.append("id=").append(id);
        sb.append(", access_token='").append(access_token).append('\'');
        sb.append(", expires_in=").append(expires_in);
        sb.append(", expires_time=").append(expires_time);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WxAccessToken that = (WxAccessToken) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (access_token != null ? !access_token.equals(that.access_token) : that.access_token != null) return false;
        if (expires_in != null ? !expires_in.equals(that.expires_in) : that.expires_in != null) return false;
        return expires_time != null ? expires_time.equals(that.expires_time) : that.expires_time == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (access_token != null ? access_token.hashCode() : 0);
        result = 31 * result + (expires_in != null ? expires_in.hashCode() : 0);
        result = 31 * result + (expires_time != null ? expires_time.hashCode() : 0);
        return result;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Date getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(Date expires_time) {
        this.expires_time = expires_time;
    }
}