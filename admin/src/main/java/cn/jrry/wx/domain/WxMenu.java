package cn.jrry.wx.domain;

import java.io.Serializable;
import java.util.Date;

public class WxMenu implements Serializable {
    private static final long serialVersionUID = 4783609055496435859L;
    private Long id;

    private Long parent_id;

    private String node_type;

    private String node_name;

    private String node_key;

    private String node_url;

    private String node_media_id;

    private String node_appid;

    private String node_pagepath;

    private String view_scope;

    private Boolean custom;

    private Long menuid;

    private String rich_content;
    private String url_state;

    private WxMenuMatchRule matchRule;
    private Integer sort;
    private Boolean deleted;
    private String cruser;
    private Date crtime;
    private String mduser;
    private Date mdtime;

    public WxMenuMatchRule getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(WxMenuMatchRule matchRule) {
        this.matchRule = matchRule;
    }

    public String getUrl_state() {
        return url_state;
    }

    public void setUrl_state(String url_state) {
        this.url_state = url_state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getNode_type() {
        return node_type;
    }

    public void setNode_type(String node_type) {
        this.node_type = node_type == null ? null : node_type.trim();
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name == null ? null : node_name.trim();
    }

    public String getNode_key() {
        return node_key;
    }

    public void setNode_key(String node_key) {
        this.node_key = node_key == null ? null : node_key.trim();
    }

    public String getNode_url() {
        return node_url;
    }

    public void setNode_url(String node_url) {
        this.node_url = node_url == null ? null : node_url.trim();
    }

    public String getNode_media_id() {
        return node_media_id;
    }

    public void setNode_media_id(String node_media_id) {
        this.node_media_id = node_media_id == null ? null : node_media_id.trim();
    }

    public String getNode_appid() {
        return node_appid;
    }

    public void setNode_appid(String node_appid) {
        this.node_appid = node_appid == null ? null : node_appid.trim();
    }

    public String getNode_pagepath() {
        return node_pagepath;
    }

    public void setNode_pagepath(String node_pagepath) {
        this.node_pagepath = node_pagepath == null ? null : node_pagepath.trim();
    }

    public String getView_scope() {
        return view_scope;
    }

    public void setView_scope(String view_scope) {
        this.view_scope = view_scope == null ? null : view_scope.trim();
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public String getRich_content() {
        return rich_content;
    }

    public void setRich_content(String rich_content) {
        this.rich_content = rich_content == null ? null : rich_content.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        WxMenu other = (WxMenu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getParent_id() == null ? other.getParent_id() == null : this.getParent_id().equals(other.getParent_id()))
                && (this.getNode_type() == null ? other.getNode_type() == null : this.getNode_type().equals(other.getNode_type()))
                && (this.getNode_name() == null ? other.getNode_name() == null : this.getNode_name().equals(other.getNode_name()))
                && (this.getNode_key() == null ? other.getNode_key() == null : this.getNode_key().equals(other.getNode_key()))
                && (this.getNode_url() == null ? other.getNode_url() == null : this.getNode_url().equals(other.getNode_url()))
                && (this.getNode_media_id() == null ? other.getNode_media_id() == null : this.getNode_media_id().equals(other.getNode_media_id()))
                && (this.getNode_appid() == null ? other.getNode_appid() == null : this.getNode_appid().equals(other.getNode_appid()))
                && (this.getNode_pagepath() == null ? other.getNode_pagepath() == null : this.getNode_pagepath().equals(other.getNode_pagepath()))
                && (this.getView_scope() == null ? other.getView_scope() == null : this.getView_scope().equals(other.getView_scope()))
                && (this.getCustom() == null ? other.getCustom() == null : this.getCustom().equals(other.getCustom()))
                && (this.getMenuid() == null ? other.getMenuid() == null : this.getMenuid().equals(other.getMenuid()))
                && (this.getRich_content() == null ? other.getRich_content() == null : this.getRich_content().equals(other.getRich_content()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getUrl_state() == null ? other.getUrl_state() == null : this.getUrl_state().equals(other.getUrl_state()))
                && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
                && (this.getCruser() == null ? other.getCruser() == null : this.getCruser().equals(other.getCruser()))
                && (this.getCrtime() == null ? other.getCrtime() == null : this.getCrtime().equals(other.getCrtime()))
                && (this.getMduser() == null ? other.getMduser() == null : this.getMduser().equals(other.getMduser()))
                && (this.getMdtime() == null ? other.getMdtime() == null : this.getMdtime().equals(other.getMdtime()))
                && (this.getMatchRule() == null ? other.getMatchRule() == null : this.getMatchRule().equals(other.getMatchRule()))
                ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParent_id() == null) ? 0 : getParent_id().hashCode());
        result = prime * result + ((getNode_type() == null) ? 0 : getNode_type().hashCode());
        result = prime * result + ((getNode_name() == null) ? 0 : getNode_name().hashCode());
        result = prime * result + ((getNode_key() == null) ? 0 : getNode_key().hashCode());
        result = prime * result + ((getNode_url() == null) ? 0 : getNode_url().hashCode());
        result = prime * result + ((getNode_media_id() == null) ? 0 : getNode_media_id().hashCode());
        result = prime * result + ((getNode_appid() == null) ? 0 : getNode_appid().hashCode());
        result = prime * result + ((getNode_pagepath() == null) ? 0 : getNode_pagepath().hashCode());
        result = prime * result + ((getView_scope() == null) ? 0 : getView_scope().hashCode());
        result = prime * result + ((getCustom() == null) ? 0 : getCustom().hashCode());
        result = prime * result + ((getMenuid() == null) ? 0 : getMenuid().hashCode());
        result = prime * result + ((getRich_content() == null) ? 0 : getRich_content().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getUrl_state() == null) ? 0 : getUrl_state().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getCruser() == null) ? 0 : getCruser().hashCode());
        result = prime * result + ((getCrtime() == null) ? 0 : getCrtime().hashCode());
        result = prime * result + ((getMduser() == null) ? 0 : getMduser().hashCode());
        result = prime * result + ((getMdtime() == null) ? 0 : getMdtime().hashCode());
        result = prime * result + ((getMatchRule() == null) ? 0 : getMatchRule().hashCode())
        ;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parent_id=").append(parent_id);
        sb.append(", node_type=").append(node_type);
        sb.append(", node_name=").append(node_name);
        sb.append(", node_key=").append(node_key);
        sb.append(", node_url=").append(node_url);
        sb.append(", node_media_id=").append(node_media_id);
        sb.append(", node_appid=").append(node_appid);
        sb.append(", node_pagepath=").append(node_pagepath);
        sb.append(", view_scope=").append(view_scope);
        sb.append(", custom=").append(custom);
        sb.append(", menuid=").append(menuid);
        sb.append(", rich_content=").append(rich_content);
        sb.append(", sort=").append(sort);
        sb.append(", url_state=").append(url_state);
        sb.append(", deleted=").append(deleted);
        sb.append(", cruser=").append(cruser);
        sb.append(", crtime=").append(crtime);
        sb.append(", mduser=").append(mduser);
        sb.append(", mdtime=").append(mdtime);
        sb.append(", matchRule=").append(matchRule);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}