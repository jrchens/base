package cn.jrry.wx.domain;


import java.util.Date;

public class AccessTokenResponse implements java.io.Serializable {
    private static final long serialVersionUID = 7023182644459086729L;
//    https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842

//    {
//        "errcode": 40013,
//            "errmsg": "invalid appid"
//    }

//    {
//        "access_token": "ACCESS_TOKEN",
//            "expires_in": 7200,
//    }

    private Integer errcode;
    private String errmsg;
    private String access_token;
    private Integer expires_in;
    private Date expires_time;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccessTokenResponse{");
        sb.append("errcode=").append(errcode);
        sb.append(", errmsg='").append(errmsg).append('\'');
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

        AccessTokenResponse that = (AccessTokenResponse) o;

        if (errcode != null ? !errcode.equals(that.errcode) : that.errcode != null) return false;
        if (errmsg != null ? !errmsg.equals(that.errmsg) : that.errmsg != null) return false;
        if (access_token != null ? !access_token.equals(that.access_token) : that.access_token != null) return false;
        if (expires_in != null ? !expires_in.equals(that.expires_in) : that.expires_in != null) return false;
        return expires_time != null ? expires_time.equals(that.expires_time) : that.expires_time == null;
    }

    @Override
    public int hashCode() {
        int result = errcode != null ? errcode.hashCode() : 0;
        result = 31 * result + (errmsg != null ? errmsg.hashCode() : 0);
        result = 31 * result + (access_token != null ? access_token.hashCode() : 0);
        result = 31 * result + (expires_in != null ? expires_in.hashCode() : 0);
        result = 31 * result + (expires_time != null ? expires_time.hashCode() : 0);
        return result;
    }

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

    public Date getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(Date expires_time) {
        this.expires_time = expires_time;
    }
}
