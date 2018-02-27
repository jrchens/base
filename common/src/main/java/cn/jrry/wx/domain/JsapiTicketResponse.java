package cn.jrry.wx.domain;


public class JsapiTicketResponse implements java.io.Serializable {
    private static final long serialVersionUID = 3675843731324455294L;

//    {
//        "errcode":0,
//            "errmsg":"ok",
//            "ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
//            "expires_in":7200
//    }


    private Integer errcode;
    private String errmsg;
    private String ticket;
    private Integer expires_in;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JsapiTicketResponse{");
        sb.append("errcode=").append(errcode);
        sb.append(", errmsg='").append(errmsg).append('\'');
        sb.append(", ticket='").append(ticket).append('\'');
        sb.append(", expires_in=").append(expires_in);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsapiTicketResponse that = (JsapiTicketResponse) o;

        if (errcode != null ? !errcode.equals(that.errcode) : that.errcode != null) return false;
        if (errmsg != null ? !errmsg.equals(that.errmsg) : that.errmsg != null) return false;
        if (ticket != null ? !ticket.equals(that.ticket) : that.ticket != null) return false;
        return expires_in != null ? expires_in.equals(that.expires_in) : that.expires_in == null;
    }

    @Override
    public int hashCode() {
        int result = errcode != null ? errcode.hashCode() : 0;
        result = 31 * result + (errmsg != null ? errmsg.hashCode() : 0);
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        result = 31 * result + (expires_in != null ? expires_in.hashCode() : 0);
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
