package cn.jrry.wx.domain;

import cn.jrry.validation.group.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class WxTag implements Serializable {
    private static final long serialVersionUID = 8482260777410947150L;
    @NotNull
    @Min(value = 1L,groups = {Detail.class,Edit.class,Remove.class})
    @Max(value = 1L,groups = {Detail.class,Edit.class,Remove.class})
    private Long id;

    @NotBlank(groups = {Save.class, Update.class})
    @Length(min = 2,max = 50,groups = {Save.class, Update.class})
    private String name;

    private Boolean deleted;

    private String cruser;

    private Date crtime;

    private String mduser;

    private Date mdtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
        WxTag other = (WxTag) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
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
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
        sb.append(", name=").append(name);
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