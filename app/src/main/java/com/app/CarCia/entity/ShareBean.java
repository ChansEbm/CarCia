package com.app.CarCia.entity;

/**
 * Created by ChanZeeBm on 2015/12/22.
 */
public class ShareBean {

    /**
     * title : 超白通体砖
     * content : 超白通体砖  白·致优雅
     * thumbUrl : http://diy.appbaba.com/garcia/uploadfile/2015/1207/20151207050550638.jpg
     * shareUrl : http://diy.appbaba.com:80/garcia/index.php?m=content&c=app&a=subject_detail&id=5
     */

    private String title;
    private String content;
    private String thumbUrl;
    private String shareUrl;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }
}
