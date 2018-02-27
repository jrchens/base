package cn.jrry.wx.domain;

import java.io.Serializable;
import java.util.Date;

public class WxJsapiTicket implements Serializable {
    private Integer id;

    private String ticket;

    private Integer expires_in;

    private Date expires_time;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
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
        WxJsapiTicket other = (WxJsapiTicket) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTicket() == null ? other.getTicket() == null : this.getTicket().equals(other.getTicket()))
            && (this.getExpires_in() == null ? other.getExpires_in() == null : this.getExpires_in().equals(other.getExpires_in()))
            && (this.getExpires_time() == null ? other.getExpires_time() == null : this.getExpires_time().equals(other.getExpires_time()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTicket() == null) ? 0 : getTicket().hashCode());
        result = prime * result + ((getExpires_in() == null) ? 0 : getExpires_in().hashCode());
        result = prime * result + ((getExpires_time() == null) ? 0 : getExpires_time().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ticket=").append(ticket);
        sb.append(", expires_in=").append(expires_in);
        sb.append(", expires_time=").append(expires_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}