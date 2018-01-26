package cn.jrry.wx.domain;

import java.io.Serializable;
import java.util.Date;

public class WxWebAccessToken implements Serializable {
    private Integer id;

    private String access_token;

    private Integer expires_in;

    private Date expires_time;

    private String openid;

    private String refresh_token;

    private String scope;

    private static final long serialVersionUID = 1L;

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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token == null ? null : refresh_token.trim();
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
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
        WxWebAccessToken other = (WxWebAccessToken) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccess_token() == null ? other.getAccess_token() == null : this.getAccess_token().equals(other.getAccess_token()))
            && (this.getExpires_in() == null ? other.getExpires_in() == null : this.getExpires_in().equals(other.getExpires_in()))
            && (this.getExpires_time() == null ? other.getExpires_time() == null : this.getExpires_time().equals(other.getExpires_time()))
            && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
            && (this.getRefresh_token() == null ? other.getRefresh_token() == null : this.getRefresh_token().equals(other.getRefresh_token()))
            && (this.getScope() == null ? other.getScope() == null : this.getScope().equals(other.getScope()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccess_token() == null) ? 0 : getAccess_token().hashCode());
        result = prime * result + ((getExpires_in() == null) ? 0 : getExpires_in().hashCode());
        result = prime * result + ((getExpires_time() == null) ? 0 : getExpires_time().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getRefresh_token() == null) ? 0 : getRefresh_token().hashCode());
        result = prime * result + ((getScope() == null) ? 0 : getScope().hashCode());
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
        sb.append(", openid=").append(openid);
        sb.append(", refresh_token=").append(refresh_token);
        sb.append(", scope=").append(scope);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}