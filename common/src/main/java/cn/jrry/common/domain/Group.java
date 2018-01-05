package cn.jrry.common.domain;

public class Group implements java.io.Serializable {

    private Long id;
    private String groupname;
    private String viewname;
    private Boolean deleted;
    private String cruser;
    private java.sql.Timestamp crtime;
    private String mduser;
    private java.sql.Timestamp mdtime;


    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getGroupname () {
        return groupname;
    }

    public void setGroupname (String groupname) {
        this.groupname = groupname;
    }

    public String getViewname () {
        return viewname;
    }

    public void setViewname (String viewname) {
        this.viewname = viewname;
    }

    public Boolean getDeleted () {
        return deleted;
    }

    public void setDeleted (Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCruser () {
        return cruser;
    }

    public void setCruser (String cruser) {
        this.cruser = cruser;
    }

    public java.sql.Timestamp getCrtime () {
        return crtime;
    }

    public void setCrtime (java.sql.Timestamp crtime) {
        this.crtime = crtime;
    }

    public String getMduser () {
        return mduser;
    }

    public void setMduser (String mduser) {
        this.mduser = mduser;
    }

    public java.sql.Timestamp getMdtime () {
        return mdtime;
    }

    public void setMdtime (java.sql.Timestamp mdtime) {
        this.mdtime = mdtime;
    }

}