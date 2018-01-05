package cn.jrry.sample.pojo;

import cn.jrry.validation.group.Edit;
import cn.jrry.validation.group.Remove;
import cn.jrry.validation.group.Update;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Sample implements Serializable {
    private static final long serialVersionUID = -6686296713191305077L;
    @NotNull(groups = {Edit.class, Remove.class, Update.class})
    private Long id;

    private String bcode;

    private String btitle;

    private Integer bint;

    private BigDecimal bnum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bdate;

    private Boolean deleted;
    private Boolean btinyint;
    private String cruser;
    private Date crtime;
    @NotNull(groups = {Edit.class})
    private String mduser;
    private Date mdtime;

    public Boolean getBtinyint() {
        return btinyint;
    }

    public void setBtinyint(Boolean btinyint) {
        this.btinyint = btinyint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode == null ? null : bcode.trim();
    }

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle == null ? null : btitle.trim();
    }

    public Integer getBint() {
        return bint;
    }

    public void setBint(Integer bint) {
        this.bint = bint;
    }

    public BigDecimal getBnum() {
        return bnum;
    }

    public void setBnum(BigDecimal bnum) {
        this.bnum = bnum;
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCruser() {
        return cruser;
    }

    public void setCruser(String cruser) {
        this.cruser = cruser == null ? null : cruser.trim();
    }

    public Date getCrtime() {
        return crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    public String getMduser() {
        return mduser;
    }

    public void setMduser(String mduser) {
        this.mduser = mduser == null ? null : mduser.trim();
    }

    public Date getMdtime() {
        return mdtime;
    }

    public void setMdtime(Date mdtime) {
        this.mdtime = mdtime;
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
        Sample other = (Sample) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getBcode() == null ? other.getBcode() == null : this.getBcode().equals(other.getBcode()))
                && (this.getBtitle() == null ? other.getBtitle() == null : this.getBtitle().equals(other.getBtitle()))
                && (this.getBint() == null ? other.getBint() == null : this.getBint().equals(other.getBint()))
                && (this.getBnum() == null ? other.getBnum() == null : this.getBnum().equals(other.getBnum()))
                && (this.getBdate() == null ? other.getBdate() == null : this.getBdate().equals(other.getBdate()))
                && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
                && (this.getCruser() == null ? other.getCruser() == null : this.getCruser().equals(other.getCruser()))
                && (this.getCrtime() == null ? other.getCrtime() == null : this.getCrtime().equals(other.getCrtime()))
                && (this.getMduser() == null ? other.getMduser() == null : this.getMduser().equals(other.getMduser()))
                && (this.getMdtime() == null ? other.getMdtime() == null : this.getMdtime().equals(other.getMdtime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBcode() == null) ? 0 : getBcode().hashCode());
        result = prime * result + ((getBtitle() == null) ? 0 : getBtitle().hashCode());
        result = prime * result + ((getBint() == null) ? 0 : getBint().hashCode());
        result = prime * result + ((getBnum() == null) ? 0 : getBnum().hashCode());
        result = prime * result + ((getBdate() == null) ? 0 : getBdate().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getCruser() == null) ? 0 : getCruser().hashCode());
        result = prime * result + ((getCrtime() == null) ? 0 : getCrtime().hashCode());
        result = prime * result + ((getMduser() == null) ? 0 : getMduser().hashCode());
        result = prime * result + ((getMdtime() == null) ? 0 : getMdtime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bcode=").append(bcode);
        sb.append(", btitle=").append(btitle);
        sb.append(", bint=").append(bint);
        sb.append(", bnum=").append(bnum);
        sb.append(", bdate=").append(bdate);
        sb.append(", deleted=").append(deleted);
        sb.append(", cruser=").append(cruser);
        sb.append(", crtime=").append(crtime);
        sb.append(", mduser=").append(mduser);
        sb.append(", mdtime=").append(mdtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}