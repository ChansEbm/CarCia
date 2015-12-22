package com.app.CarCia.model;

import android.text.TextUtils;
import android.view.View;

import com.app.CarCia.tools.AppTools;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by ChanZeeBm on 2015/12/14.
 */
public class ShareModel implements PlatformActionListener {
    private Wechat.ShareParams wechatFirendsParams = new Wechat.ShareParams();
    private WechatMoments.ShareParams wechatMomentParams = new WechatMoments.ShareParams();
    private QZone.ShareParams qZoneShareParams = new QZone.ShareParams();
    private QQ.ShareParams qqParams = new QQ.ShareParams();
    private TencentWeibo.ShareParams weiboParams = new TencentWeibo.ShareParams();
    private ShortMessage.ShareParams shortMessageParams = new ShortMessage.ShareParams();
    private Platform platform;
    private View snackView;
    private ShareParams shareParams;

    public void shareToWechatFriends() {
        wechatFirendsParams.setTitle(shareParams.getTitle());
        wechatFirendsParams.setImageUrl(TextUtils.isEmpty(shareParams.getImageUrl()) ?
                shareParams.getImageStaticUrl() : shareParams.getImageUrl());
        wechatFirendsParams.setText(shareParams.getText());
        wechatFirendsParams.setUrl(shareParams.getTitleUrl());
        wechatFirendsParams.setShareType(Platform.SHARE_WEBPAGE);
        platform = ShareSDK.getPlatform(Wechat.NAME);
        platform.setPlatformActionListener(this);
        platform.share(wechatFirendsParams);
    }

    public void shareToWechatMoment() {
        wechatMomentParams.setTitle(shareParams.getTitle());
        wechatMomentParams.setImageUrl(TextUtils.isEmpty(shareParams.getImageUrl()) ?
                shareParams.getImageStaticUrl() : shareParams.getImageUrl());
        wechatMomentParams.setText(shareParams.getText());
        wechatMomentParams.setUrl(shareParams.getTitleUrl());
        wechatMomentParams.setShareType(Platform.SHARE_WEBPAGE);
        platform = ShareSDK.getPlatform(WechatMoments.NAME);
        platform.setPlatformActionListener(this);
        platform.share(wechatMomentParams);
    }

    public void shareToQZone() {
        qZoneShareParams.setTitle(shareParams.getTitle());
        qZoneShareParams.setTitleUrl(shareParams.getTitleUrl());
        qZoneShareParams.setSiteUrl(ShareParams.siteUrl);
        qZoneShareParams.setSite(ShareParams.site);
        qZoneShareParams.setImageUrl(TextUtils.isEmpty(shareParams.getImageUrl()) ?
                shareParams.getImageStaticUrl() : shareParams.getImageUrl());
        qZoneShareParams.setText(shareParams.getText());
        platform = ShareSDK.getPlatform(QZone.NAME);
        platform.setPlatformActionListener(this);
        platform.share(qZoneShareParams);
    }

    public void shareToQQ() {
        qqParams.setTitle(shareParams.getTitle());
        qqParams.setTitleUrl(shareParams.getTitleUrl());
        qqParams.setSiteUrl(ShareParams.siteUrl);
        qqParams.setSite(ShareParams.site);
        qqParams.setImageUrl(TextUtils.isEmpty(shareParams.getImageUrl()) ?
                shareParams.getImageStaticUrl() : shareParams.getImageUrl());
        qqParams.setText(shareParams.getText());
        platform = ShareSDK.getPlatform(QQ.NAME);
        platform.setPlatformActionListener(this);
        platform.share(qqParams);
    }

    public void shareToShortMessage() {
        shortMessageParams.setTitle(shareParams.getTitle());
        shortMessageParams.setTitleUrl(shareParams.getTitleUrl());
        shortMessageParams.setSiteUrl(ShareParams.siteUrl);
        shortMessageParams.setSite(ShareParams.site);
        shortMessageParams.setImageUrl(TextUtils.isEmpty(shareParams.getImageUrl()) ?
                shareParams.getImageStaticUrl() : shareParams.getImageUrl());
        shortMessageParams.setText(shareParams.getText());
        platform = ShareSDK.getPlatform(ShortMessage.NAME);
        platform.setPlatformActionListener(this);
        platform.share(shortMessageParams);
    }

    public void setShareParams(ShareParams shareParams) {
        this.shareParams = shareParams;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (snackView != null) {
            AppTools.showSnackBar(snackView, "分享成功");
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        if (snackView != null) {
            AppTools.showSnackBar(snackView, "取消分享");
        }
    }

    public void setSnackView(View snackView) {
        this.snackView = snackView;
    }

    public static class ShareParams {
        public String title = "加西亚瓷砖";
        public String text = "加西亚瓷砖始终为顾客提供最具创新价值的产品";
        public String imageUrl = "";
        public String titleUrl = "";
        public String imageStaticUrl = "http://diy.appbaba.com/garcia/android/logo.jpg";

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitleUrl() {
            return titleUrl;
        }

        public void setTitleUrl(String titleUrl) {
            this.titleUrl = titleUrl;
        }

        public String getImageStaticUrl() {
            return imageStaticUrl;
        }

        public void setImageStaticUrl(String imageStaticUrl) {
            this.imageStaticUrl = imageStaticUrl;
        }

        private static String site = "加西亚";
        private static String siteUrl = "http://m.garcia.net.cn/";
    }
}
