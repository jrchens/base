package cn.jrry.web.domain;

import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable {
    private static final long serialVersionUID = 3793305295004794328L;
    private Long id;

    private String originalFileName;

    private String savePath;

    private String fileName;

    private Long fileSize;

    private String fileType;

    private String fileSha1;

    private String owner;

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

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName == null ? null : originalFileName.trim();
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath == null ? null : savePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFileSha1() {
        return fileSha1;
    }

    public void setFileSha1(String fileSha1) {
        this.fileSha1 = fileSha1 == null ? null : fileSha1.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
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
        Attachment other = (Attachment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOriginalFileName() == null ? other.getOriginalFileName() == null : this.getOriginalFileName().equals(other.getOriginalFileName()))
            && (this.getSavePath() == null ? other.getSavePath() == null : this.getSavePath().equals(other.getSavePath()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
            && (this.getFileSha1() == null ? other.getFileSha1() == null : this.getFileSha1().equals(other.getFileSha1()))
            && (this.getOwner() == null ? other.getOwner() == null : this.getOwner().equals(other.getOwner()))
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
        result = prime * result + ((getOriginalFileName() == null) ? 0 : getOriginalFileName().hashCode());
        result = prime * result + ((getSavePath() == null) ? 0 : getSavePath().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getFileSha1() == null) ? 0 : getFileSha1().hashCode());
        result = prime * result + ((getOwner() == null) ? 0 : getOwner().hashCode());
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
        sb.append(", originalFileName=").append(originalFileName);
        sb.append(", savePath=").append(savePath);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileType=").append(fileType);
        sb.append(", fileSha1=").append(fileSha1);
        sb.append(", owner=").append(owner);
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