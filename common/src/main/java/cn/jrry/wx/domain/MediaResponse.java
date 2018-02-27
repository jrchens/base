package cn.jrry.wx.domain;


public class MediaResponse implements java.io.Serializable {
//    https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID

//    {
//        "errcode": 40029,
//            "errmsg": "invalid code"
//    }

//    {
//        "video_url":DOWN_URL
//    }


    private Integer errcode;
    private String errmsg;
    private String video_url;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaResponse{");
        sb.append("errcode=").append(errcode);
        sb.append(", errmsg='").append(errmsg).append('\'');
        sb.append(", video_url='").append(video_url).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaResponse that = (MediaResponse) o;

        if (errcode != null ? !errcode.equals(that.errcode) : that.errcode != null) return false;
        if (errmsg != null ? !errmsg.equals(that.errmsg) : that.errmsg != null) return false;
        return video_url != null ? video_url.equals(that.video_url) : that.video_url == null;
    }

    @Override
    public int hashCode() {
        int result = errcode != null ? errcode.hashCode() : 0;
        result = 31 * result + (errmsg != null ? errmsg.hashCode() : 0);
        result = 31 * result + (video_url != null ? video_url.hashCode() : 0);
        return result;
    }

    public Integer getErrcode() {

        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
