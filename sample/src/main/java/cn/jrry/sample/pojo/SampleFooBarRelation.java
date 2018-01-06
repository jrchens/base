package cn.jrry.sample.pojo;

import java.io.Serializable;

public class SampleFooBarRelation implements Serializable {
    private static final long serialVersionUID = 495281311222361102L;
    private Long id;

    private Long fooId;

    private Long barId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFooId() {
        return fooId;
    }

    public void setFooId(Long fooId) {
        this.fooId = fooId;
    }

    public Long getBarId() {
        return barId;
    }

    public void setBarId(Long barId) {
        this.barId = barId;
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
        SampleFooBarRelation other = (SampleFooBarRelation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getFooId() == null ? other.getFooId() == null : this.getFooId().equals(other.getFooId()))
                && (this.getBarId() == null ? other.getBarId() == null : this.getBarId().equals(other.getBarId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFooId() == null) ? 0 : getFooId().hashCode());
        result = prime * result + ((getBarId() == null) ? 0 : getBarId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fooId=").append(fooId);
        sb.append(", barId=").append(barId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}