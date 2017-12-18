package apache.httpclient;

public class Entity implements java.io.Serializable {
    // {"applyactivity":"收费","djlx":"转移登记","slbh":"20160626220055033","jjrq":1466949655000,"success":"true",
    // "qlr":"张世龙","zl":"华阳镇东昌南路88号御东国际30幢307室"}
    private String applyactivity;
    private String djlx;
    private String slbh;
    private String jjrq;
    private String success;
    private String qlr;
    private String zl;

    public String getApplyactivity() {
        return applyactivity;
    }

    public void setApplyactivity(String applyactivity) {
        this.applyactivity = applyactivity;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getJjrq() {
        return jjrq;
    }

    public void setJjrq(String jjrq) {
        this.jjrq = jjrq;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }
}
