package cn.jrry.common.domain;

import java.util.Objects;

public class User implements java.io.Serializable {

    private static final long serialVersionUID = 3224988800583542492L;
    private Long id;
    private String username;
    private String viewname;
    private String password;
    private String passwordSalt;
    private String email;
    private Boolean disabled;
    private Boolean locked;
    private Boolean deleted;
    private String cruser;
    private java.sql.Timestamp crtime;
    private String mduser;
    private java.sql.Timestamp mdtime;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", viewname='").append(viewname).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", passwordSalt='").append(passwordSalt).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", disabled=").append(disabled);
        sb.append(", locked=").append(locked);
        sb.append(", deleted=").append(deleted);
        sb.append(", cruser='").append(cruser).append('\'');
        sb.append(", crtime=").append(crtime);
        sb.append(", mduser='").append(mduser).append('\'');
        sb.append(", mdtime=").append(mdtime);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(viewname, user.viewname) &&
                Objects.equals(password, user.password) &&
                Objects.equals(passwordSalt, user.passwordSalt) &&
                Objects.equals(email, user.email) &&
                Objects.equals(disabled, user.disabled) &&
                Objects.equals(locked, user.locked) &&
                Objects.equals(deleted, user.deleted) &&
                Objects.equals(cruser, user.cruser) &&
                Objects.equals(crtime, user.crtime) &&
                Objects.equals(mduser, user.mduser) &&
                Objects.equals(mdtime, user.mdtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, viewname, password, passwordSalt, email, disabled, locked, deleted, cruser, crtime, mduser, mdtime);
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getViewname () {
        return viewname;
    }

    public void setViewname (String viewname) {
        this.viewname = viewname;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPasswordSalt () {
        return passwordSalt;
    }

    public void setPasswordSalt (String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public Boolean getDisabled () {
        return disabled;
    }

    public void setDisabled (Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getLocked () {
        return locked;
    }

    public void setLocked (Boolean locked) {
        this.locked = locked;
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