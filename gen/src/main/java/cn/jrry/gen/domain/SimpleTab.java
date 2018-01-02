package cn.jrry.gen.domain;

import java.io.Serializable;
import java.util.Date;

public class SimpleTab implements Serializable {
    private Long id;

    private Boolean tinyintBoolean;

    private Integer intInteger;

    private Double doubleDouble;

    private Date dateDate;

    private Date timeTime;

    private Date datetimeTimestamp;

    private String varcharString;

    private String textString;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getTinyintBoolean() {
        return tinyintBoolean;
    }

    public void setTinyintBoolean(Boolean tinyintBoolean) {
        this.tinyintBoolean = tinyintBoolean;
    }

    public Integer getIntInteger() {
        return intInteger;
    }

    public void setIntInteger(Integer intInteger) {
        this.intInteger = intInteger;
    }

    public Double getDoubleDouble() {
        return doubleDouble;
    }

    public void setDoubleDouble(Double doubleDouble) {
        this.doubleDouble = doubleDouble;
    }

    public Date getDateDate() {
        return dateDate;
    }

    public void setDateDate(Date dateDate) {
        this.dateDate = dateDate;
    }

    public Date getTimeTime() {
        return timeTime;
    }

    public void setTimeTime(Date timeTime) {
        this.timeTime = timeTime;
    }

    public Date getDatetimeTimestamp() {
        return datetimeTimestamp;
    }

    public void setDatetimeTimestamp(Date datetimeTimestamp) {
        this.datetimeTimestamp = datetimeTimestamp;
    }

    public String getVarcharString() {
        return varcharString;
    }

    public void setVarcharString(String varcharString) {
        this.varcharString = varcharString == null ? null : varcharString.trim();
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString == null ? null : textString.trim();
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
        SimpleTab other = (SimpleTab) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTinyintBoolean() == null ? other.getTinyintBoolean() == null : this.getTinyintBoolean().equals(other.getTinyintBoolean()))
            && (this.getIntInteger() == null ? other.getIntInteger() == null : this.getIntInteger().equals(other.getIntInteger()))
            && (this.getDoubleDouble() == null ? other.getDoubleDouble() == null : this.getDoubleDouble().equals(other.getDoubleDouble()))
            && (this.getDateDate() == null ? other.getDateDate() == null : this.getDateDate().equals(other.getDateDate()))
            && (this.getTimeTime() == null ? other.getTimeTime() == null : this.getTimeTime().equals(other.getTimeTime()))
            && (this.getDatetimeTimestamp() == null ? other.getDatetimeTimestamp() == null : this.getDatetimeTimestamp().equals(other.getDatetimeTimestamp()))
            && (this.getVarcharString() == null ? other.getVarcharString() == null : this.getVarcharString().equals(other.getVarcharString()))
            && (this.getTextString() == null ? other.getTextString() == null : this.getTextString().equals(other.getTextString()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTinyintBoolean() == null) ? 0 : getTinyintBoolean().hashCode());
        result = prime * result + ((getIntInteger() == null) ? 0 : getIntInteger().hashCode());
        result = prime * result + ((getDoubleDouble() == null) ? 0 : getDoubleDouble().hashCode());
        result = prime * result + ((getDateDate() == null) ? 0 : getDateDate().hashCode());
        result = prime * result + ((getTimeTime() == null) ? 0 : getTimeTime().hashCode());
        result = prime * result + ((getDatetimeTimestamp() == null) ? 0 : getDatetimeTimestamp().hashCode());
        result = prime * result + ((getVarcharString() == null) ? 0 : getVarcharString().hashCode());
        result = prime * result + ((getTextString() == null) ? 0 : getTextString().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tinyintBoolean=").append(tinyintBoolean);
        sb.append(", intInteger=").append(intInteger);
        sb.append(", doubleDouble=").append(doubleDouble);
        sb.append(", dateDate=").append(dateDate);
        sb.append(", timeTime=").append(timeTime);
        sb.append(", datetimeTimestamp=").append(datetimeTimestamp);
        sb.append(", varcharString=").append(varcharString);
        sb.append(", textString=").append(textString);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}