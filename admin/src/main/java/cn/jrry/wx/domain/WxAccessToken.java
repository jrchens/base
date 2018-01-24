package cn.jrry.wx.domain;

import java.util.Date;

// https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
//        返回码	说明
//        -1	系统繁忙，此时请开发者稍候再试
//        0	请求成功
//        40001	AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性
//        40002	请确保grant_type字段值为client_credential
//        40164	调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置


public class WxAccessToken extends WxResponse {
    private static final long serialVersionUID = -3464799667320734039L;
    private Integer id;

    private String access_token;

    private Integer expires_in;

    private Date expires_time;
    private Date crtime;

    public Date getCrtime() {
        return crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
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
        this.access_token = access_token == null ? null : access_token.trim();
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WxAccessToken other = (WxAccessToken) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getAccess_token() == null ? other.getAccess_token() == null : this.getAccess_token().equals(other.getAccess_token()))
                && (this.getExpires_in() == null ? other.getExpires_in() == null : this.getExpires_in().equals(other.getExpires_in()))
                && (this.getExpires_time() == null ? other.getExpires_time() == null : this.getExpires_time().equals(other.getExpires_time()))
                && (this.getCrtime() == null ? other.getCrtime() == null : this.getCrtime().equals(other.getCrtime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccess_token() == null) ? 0 : getAccess_token().hashCode());
        result = prime * result + ((getExpires_in() == null) ? 0 : getExpires_in().hashCode());
        result = prime * result + ((getExpires_time() == null) ? 0 : getExpires_time().hashCode());
        result = prime * result + ((getCrtime() == null) ? 0 : getCrtime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", access_token=").append(access_token);
        sb.append(", expires_in=").append(expires_in);
        sb.append(", expires_time=").append(expires_time);
        sb.append(", crtime=").append(crtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}