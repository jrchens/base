package cn.jrry.admin.domain;

import cn.jrry.validation.group.Remove;
import cn.jrry.validation.group.Save;
import cn.jrry.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Config implements Serializable {
    private static final long serialVersionUID = 9118063304074817477L;
    private Long id;

    @NotBlank
    @Length(min = 36,max = 36,groups = {Save.class, Remove.class, Update.class})
    private String cfgCode;
    @NotBlank
    @Length(min = 2,max = 50,groups = {Save.class, Update.class})
    private String cfgName;
    @NotNull
    @Min(value = 1,groups = {Save.class})
    @Max(value = 32,groups = {Save.class})
    private Byte cfgType;
    @NotBlank
    @Length(min = 1,max = 200,groups = {Save.class, Update.class})
    private String cfgValue;

    private Boolean deleted;
    private String cruser;
    private Date crtime;
    private String mduser;
    private Date mdtime;

    public String getCfgName() {
        return cfgName;
    }

    public void setCfgName(String cfgName) {
        this.cfgName = cfgName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCfgCode() {
        return cfgCode;
    }

    public void setCfgCode(String cfgCode) {
        this.cfgCode = cfgCode == null ? null : cfgCode.trim();
    }

    public Byte getCfgType() {
        return cfgType;
    }

    public void setCfgType(Byte cfgType) {
        this.cfgType = cfgType;
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue == null ? null : cfgValue.trim();
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
        Config other = (Config) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCfgCode() == null ? other.getCfgCode() == null : this.getCfgCode().equals(other.getCfgCode()))
                && (this.getCfgType() == null ? other.getCfgType() == null : this.getCfgType().equals(other.getCfgType()))
                && (this.getCfgName() == null ? other.getCfgName() == null : this.getCfgName().equals(other.getCfgName()))
                && (this.getCfgValue() == null ? other.getCfgValue() == null : this.getCfgValue().equals(other.getCfgValue()))
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
        result = prime * result + ((getCfgCode() == null) ? 0 : getCfgCode().hashCode());
        result = prime * result + ((getCfgType() == null) ? 0 : getCfgType().hashCode());
        result = prime * result + ((getCfgName() == null) ? 0 : getCfgName().hashCode());
        result = prime * result + ((getCfgValue() == null) ? 0 : getCfgValue().hashCode());
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
        sb.append(", cfgCode=").append(cfgCode);
        sb.append(", cfgType=").append(cfgType);
        sb.append(", cfgValue=").append(cfgValue);
        sb.append(", cfgName=").append(cfgName);
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