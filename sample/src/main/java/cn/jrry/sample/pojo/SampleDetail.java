package cn.jrry.sample.pojo;

import java.io.Serializable;

public class SampleDetail implements Serializable {
    private static final long serialVersionUID = 6568203436834723438L;
    private Long id;

    private Long sampleId;

    private String bcontent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public String getBcontent() {
        return bcontent;
    }

    public void setBcontent(String bcontent) {
        this.bcontent = bcontent == null ? null : bcontent.trim();
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
        SampleDetail other = (SampleDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSampleId() == null ? other.getSampleId() == null : this.getSampleId().equals(other.getSampleId()))
                && (this.getBcontent() == null ? other.getBcontent() == null : this.getBcontent().equals(other.getBcontent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSampleId() == null) ? 0 : getSampleId().hashCode());
        result = prime * result + ((getBcontent() == null) ? 0 : getBcontent().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sampleId=").append(sampleId);
        sb.append(", bcontent=").append(bcontent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}