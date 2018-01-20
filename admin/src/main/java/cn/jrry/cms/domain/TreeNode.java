package cn.jrry.cms.domain;

import com.google.common.collect.Lists;

import java.util.List;

public class TreeNode implements java.io.Serializable {
    private static final long serialVersionUID = -5673451005085305079L;
    private Long id;
    private String text;
    private String state;
    private List<TreeNode> children = Lists.newArrayList();

    public TreeNode(Long id, String text, String state) {
        this.id = id;
        this.text = text;
        this.state = state;
    }

    public TreeNode(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode treeNode = (TreeNode) o;

        if (!id.equals(treeNode.id)) return false;
        return text.equals(treeNode.text);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TreeNode{");
        sb.append("id=").append(id);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
