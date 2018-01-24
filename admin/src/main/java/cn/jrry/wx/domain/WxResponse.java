package cn.jrry.wx.domain;

public class WxResponse implements java.io.Serializable {
    private static final long serialVersionUID = 2713810979716019459L;
    // "errcode":40013,"errmsg":"invalid appid"
    private Integer errcode;
    private String errmsg;

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
}
