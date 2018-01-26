package cn.jrry.wx.domain;


public class RefreshTokenResponse implements java.io.Serializable {
//    https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842

//    {
//        "errcode": 40029,
//            "errmsg": "invalid code"
//    }

//    {
//        "access_token": "ACCESS_TOKEN",
//            "expires_in": 7200,
//            "refresh_token": "REFRESH_TOKEN",
//            "openid": "OPENID",
//            "scope": "SCOPE"
//    }


    private Integer errcode;
    private String errmsg;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String openid;
    private String scope;


    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
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

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefreshTokenResponse that = (RefreshTokenResponse) o;

        if (errcode != null ? !errcode.equals(that.errcode) : that.errcode != null) return false;
        if (errmsg != null ? !errmsg.equals(that.errmsg) : that.errmsg != null) return false;
        if (access_token != null ? !access_token.equals(that.access_token) : that.access_token != null) return false;
        if (expires_in != null ? !expires_in.equals(that.expires_in) : that.expires_in != null) return false;
        if (refresh_token != null ? !refresh_token.equals(that.refresh_token) : that.refresh_token != null)
            return false;
        if (openid != null ? !openid.equals(that.openid) : that.openid != null) return false;
        return scope != null ? scope.equals(that.scope) : that.scope == null;
    }

    @Override
    public int hashCode() {
        int result = errcode != null ? errcode.hashCode() : 0;
        result = 31 * result + (errmsg != null ? errmsg.hashCode() : 0);
        result = 31 * result + (access_token != null ? access_token.hashCode() : 0);
        result = 31 * result + (expires_in != null ? expires_in.hashCode() : 0);
        result = 31 * result + (refresh_token != null ? refresh_token.hashCode() : 0);
        result = 31 * result + (openid != null ? openid.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RefreshTokenResponse{");
        sb.append("errcode=").append(errcode);
        sb.append(", errmsg='").append(errmsg).append('\'');
        sb.append(", access_token='").append(access_token).append('\'');
        sb.append(", expires_in=").append(expires_in);
        sb.append(", refresh_token='").append(refresh_token).append('\'');
        sb.append(", openid='").append(openid).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
